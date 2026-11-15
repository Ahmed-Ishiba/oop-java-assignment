package marsops.mission;
import marsops.core.AgentRequirement;
import marsops.resource.ResourceType;
import marsops.facility.EquipmentType;
import marsops.agent.Agent;
import marsops.environment.Condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public abstract class Mission {
    private int id;
    private String description;
    private MissionType type;
    private MissionState state = MissionState.CREATED;
    private int duration;
    private int elapsed = 0;
    private List<AgentRequirement> agentRequirements = new ArrayList<>();
    private Map<ResourceType,Integer> resourceRequirements = new EnumMap<>(ResourceType.class);
    private Set<EquipmentType> equipmentRequirements = EnumSet.noneOf(EquipmentType.class);
    private List<Agent> assignedAgents = new ArrayList<>();
    private String failureReason;


    protected Mission(int id, MissionType missionType, String description, int duration) {
        if (duration < 1) throw new IllegalArgumentException("Mission duration must be at least 1");
        this.id = id;
        this.type = missionType;
        this.description = description;
        this.duration = duration;
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
    
    public boolean isFinished() { return state == MissionState.COMPLETED || state == MissionState.FAILED; }
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
    public void tick() {
        requireState(MissionState.IN_PROGRESS);
        elapsed++;
        if (elapsed >= duration) complete();
    }

    private void requireState(MissionState expected) {
        if (state != expected) {
            throw new IllegalStateException("Mission is " + state + ", expected " + expected);
        }
    }

    protected void onComplete(){ }
}
