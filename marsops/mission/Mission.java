package marsops.mission;
import marsops.core.AgentRequirement;
import marsops.resource.ResourceType;
import marsops.facility.EquipmentType;
import marsops.agent.Agent;
import marsops.environment.Condition;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
public abstract class Mission {
    int id;
    String description;
    MissionType type;
    MissionState state;
    List<AgentRequirement> agentRequirements;
    Map<ResourceType,Integer> resourceRequirements;
    Set<EquipmentType> equipmentRequirements; // set because it doesnt need duplicates
    List<Agent> assignedAgents;

    String failureReason;
    protected Mission(int id, MissionType missionType){
        this.id = id;this.type = missionType;
    }
    public int getId() { return id; }
    public MissionType getType() { return type; }
    public String getDescription() { return description; }
    public MissionState getState() { return state; }
    public String getFailureReason() { return failureReason; }
    public List<AgentRequirement> getAgentRequirements(){return Collections.unmodifiableList(agentRequirements);}
    public Map<ResourceType,Integer> getResourceRequirements(){return Collections.unmodifiableMap(resourceRequirements);}
    public Set<EquipmentType> getEquipmentTypes(){return Collections.unmodifiableSet(equipmentRequirements);}
    

    protected void requireAgent(AgentRequirement requirement){agentRequirements.add(requirement);}
    protected void requireResource(ResourceType resource, int amount){resourceRequirements.merge(resource, amount, Integer::sum);}
    protected void requireEquipment(EquipmentType equipment){equipmentRequirements.add(equipment);}
    
    public boolean isFinished(){return state == MissionState.COMPLETED;}
    public void fail(String reason) {
        if (isFinished()) {
            throw new IllegalStateException("Mission already ended: " + state);
        }
        failureReason = reason;
        state = MissionState.FAILED;
        releaseAgents();
    }
    public void assign(List<Agent> agents){
        for(Agent agent:agents){
            agent.assignTo(this);
            assignedAgents.add(agent);
        }
        state = MissionState.ASSIGNED;
    }
    public void start(){
        state = MissionState.IN_PROGRESS;
    }
    public void complete(){
        state = MissionState.COMPLETED;
        releaseAgents();
    }
    private void releaseAgents(){
        for(Agent agent : assignedAgents)agent.release();
    }
    public boolean isBlockedBy(Condition condition){return false;}
}
