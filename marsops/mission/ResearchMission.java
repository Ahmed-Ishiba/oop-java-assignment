package marsops.mission;
import marsops.agent.Capability;
import marsops.resource.ResourceType;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.*;

public class ResearchMission extends Mission {
    private final Laboratory laboratory;

    public ResearchMission(int id, String experiment, Laboratory laboratory) {
        super(id, MissionType.RESEARCH, "Research: " + experiment, 2);
        this.laboratory = laboratory;
        requireAgent(new AgentRequirement(Category.HUMAN, Capability.SCIENTIFIC_RESEARCH, 1));
        requireEquipment(EquipmentType.SCIENTIFIC);
        requireResource(ResourceType.BATTERY_POWER, 5);
    }

    public Laboratory getLaboratory() { return laboratory; }

    @Override
    protected void onComplete() { }
}