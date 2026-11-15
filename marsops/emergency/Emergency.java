package marsops.emergency;

import marsops.facility.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import marsops.resource.ResourceType;
import marsops.agent.Agent;
import marsops.core.AgentRequirement;
import java.util.List;

public abstract class Emergency {
    private EmergencyType Emergency_type;
    private Location location;
    private List<AgentRequirement> responderRequirement = new ArrayList<>();
    private Map<ResourceType, Integer> responseResources = new EnumMap<>(ResourceType.class);
    private EmergencyState state = EmergencyState.ACTIVE;
    private String reason;

    protected Emergency(EmergencyType Emergency_type, Location location) {
        this.Emergency_type = Emergency_type;
        this.location = location;
    }

    protected Emergency() {
        this.Emergency_type = EmergencyType.MEDICAL_EMERGENCY;

    }

    public EmergencyType getType() {
        return Emergency_type;
    }

    public Location getLocation() {
        return location;
    }

    public EmergencyState getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public List<AgentRequirement> getResponderRequirements() {
        return Collections.unmodifiableList(responderRequirement);
    }

    protected void addRequirement(AgentRequirement requirement) {
        responderRequirement.add(requirement);
    }

    public boolean requires(Agent agent) {
        if (agent == null) {
            return false;
        }
        for (int i = 0; i < responderRequirement.size(); i = i + 1) {
            if (responderRequirement.get(i).matches(agent)) {
                return true;
            }
        }
        return false;
    }
    public Map<ResourceType, Integer> getResponseResources() {
        return Collections.unmodifiableMap(responseResources);
    }

    protected void requireResource(ResourceType resource, int amount) {
        responseResources.merge(resource, amount, Integer::sum);
    }

    public void markResolved() {
        requireActive();
        state = EmergencyState.RESOLVED;
        onResolved();
    }

    public void markUnresolved(String reason) {
        requireActive();
        this.reason = reason;
        state = EmergencyState.UNRESOLVED;
        applyUnresolvedPenalty();
    }

    private void requireActive() {
        if (state != EmergencyState.ACTIVE) {
            throw new IllegalStateException("Emergency already ended: " + state);
        }
    }


    protected void onResolved() { }
    protected void applyUnresolvedPenalty() { }
    public abstract void applyImmediateEffects();
}
