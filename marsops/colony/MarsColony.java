package marsops.colony;

import marsops.agent.Agent;
import marsops.core.AgentRequirement;
import marsops.core.EmergencyResult;
import marsops.core.MissionResult;
import marsops.emergency.Emergency;
import marsops.environment.Condition;
import marsops.environment.Environment;
import marsops.facility.Facility;
import marsops.facility.Location;
import marsops.mission.Mission;
import marsops.mission.MissionState;
import marsops.resource.ResourceType;

import java.util.*;

public class MarsColony {
    private final String name;
    private final List<Agent> agents = new ArrayList<>();
    private final List<Facility> facilities = new ArrayList<>();
    private final Environment environment = new Environment();
    private Mission activeMission = null; // NOT final, NOT initialized to null
    private int currentTime = 0;

    public MarsColony(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Colony name cannot be null or blank");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Mission getActiveMission() {
        return activeMission;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void addAgent(Agent agent) {
        if (agent != null && !agents.contains(agent)) {
            agents.add(agent);
        }
    }

    public void addFacility(Facility facility) {
        if (facility != null && !facilities.contains(facility)) {
            facilities.add(facility);
        }
    }

    public int getResourceTotal(ResourceType resourceType) {
        int total = 0;
        for (Facility facility : facilities) {
            if (facility.isOperational()) {
                total += facility.getQuantity(resourceType);
            }
        }
        return total;
    }

    public boolean canSupply(Map<ResourceType, Integer> cost) {
        for (ResourceType type : ResourceType.values()) {
            Integer needed = cost.get(type);
            if (needed != null && needed > 0 && getResourceTotal(type) < needed) {
                return false;
            }
        }
        return true;
    }

    public boolean consumeResources(Map<ResourceType, Integer> cost) {
        if (!canSupply(cost)) {
            return false;
        }
        for (ResourceType type : ResourceType.values()) {
            Integer needed = cost.get(type);
            if (needed == null || needed <= 0) {
                continue;
            }
            int remaining = needed;
            for (Facility facility : facilities) {
                if (!facility.isOperational()) {
                    continue;
                }
                int taken = facility.removeResourceUpTo(type, remaining);
                remaining = remaining - taken;
                if (remaining == 0) {
                    break;
                }
            }
            if (remaining > 0) {
                return false;
            }
        }
        return true;
    }

    public MissionResult submitMission(Mission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("Mission cannot be null");
        }
        if (mission.getState() != MissionState.CREATED) {
            return MissionResult.rejected(mission, "Mission is not in CREATED state");
        }

        if (activeMission != null) {
            return MissionResult.rejected(mission, "Another mission is already active");
        }

        List<Agent> submitted = mission.getAssignedAgents();
        if (submitted == null || submitted.isEmpty()) {
            return MissionResult.rejected(mission, "No agents assigned to mission");
        }

        for (AgentRequirement requirement : mission.getAgentRequirements()) {
            int found = 0;
            for (Agent agent : submitted) {
                if (found >= requirement.getCount())
                    break;
                if (!agent.isAvailable())
                    continue;
                if (!requirement.matches(agent))
                    continue;
                found++;
            }
            if (found < requirement.getCount()) {
                return MissionResult.rejected(mission, "Not enough agents for " + requirement.getCapability());
            }
        }

        if (!canSupply(mission.getResourceRequirements())) {
            return MissionResult.rejected(mission, "Insufficient colony resources");
        }

        Condition condition = environment.getCondition();
        if (mission.isBlockedBy(condition)) {
            return MissionResult.rejected(mission, environment.getRestrictionReason());
        }

        if (!consumeResources(mission.getResourceRequirements())) {
            return MissionResult.rejected(mission, "Insufficient colony resources");
        }

        for (Agent agent : submitted) {
            agent.assignTo(mission);
        }
        mission.assign(submitted);
        activeMission = mission;
        return MissionResult.accepted(mission);
    }

    public void advanceTime(int units) {
        if (units < 0) {
            return;
        }
        for (int i = 0; i < units; i = i + 1) {
            currentTime = currentTime + 1;
            for (Agent agent : agents) {
                agent.tick();
            }
            for (i = 0; i < units; i = i + 1) {
                currentTime = currentTime + 1;
                for (Agent agent : agents) {
                    agent.tick();
                }
                if (activeMission != null && activeMission.getState() == MissionState.ASSIGNED) {
                    activeMission.start(); // ASSIGNED -> IN_PROGRESS
                }
                if (activeMission != null && activeMission.getState() == MissionState.IN_PROGRESS) {
                    activeMission.tick();
                }
            }
            if (activeMission != null && activeMission.getState() == MissionState.IN_PROGRESS) {
                activeMission.tick();
            }
        }
    }

    public EmergencyResult triggerEmergency(Emergency emergency) {
        if (emergency == null) {
            throw new IllegalArgumentException("Emergency cannot be null");
        }

        emergency.applyImmediateEffects();

        boolean respondersFound = true;
        for (AgentRequirement requirement : emergency.getResponderRequirements()) {
            int found = 0;
            for (Agent agent : agents) {
                if (found >= requirement.getCount())
                    break;
                if (!agent.isAvailable())
                    continue;
                if (!emergency.requires(agent))
                    continue;
                found++;
            }
            if (found < requirement.getCount()) {
                respondersFound = false;
                break;
            }
        }

        if (!respondersFound) {
            emergency.markUnresolved("Required responders not available.");
            return EmergencyResult.unresolved(emergency, "Required responders not available.");
        }

        if (!canSupply(emergency.getResponseResources())) {
            emergency.markUnresolved("Insufficient response resources.");
            return EmergencyResult.unresolved(emergency, "Insufficient response resources.");
        }

        if (!consumeResources(emergency.getResponseResources())) {
            emergency.markUnresolved("Insufficient response resources.");
            return EmergencyResult.unresolved(emergency, "Insufficient response resources.");
        }

        emergency.markResolved();
        return EmergencyResult.resolved(emergency, "Emergency resolved.");
    }

    public void setEnvironmentCondition(Condition condition) {
        environment.setCondition(condition);
        if (activeMission != null && activeMission.isBlockedBy(condition)) {
            activeMission.fail(environment.getRestrictionReason());
            activeMission = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MarsColony[").append(name).append("] t=").append(currentTime);
        builder.append(" env=").append(environment);
        builder.append(" activeMission=");
        if (activeMission == null) {
            builder.append("none");
        } else {
            builder.append(activeMission.getId());
        }
        return builder.toString();
    }
}