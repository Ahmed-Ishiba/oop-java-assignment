package marsops.mission;
import marsops.agent.Capability;
import marsops.resource.ResourceType;
import marsops.agent.Category;
import marsops.core.AgentRequirement;

import marsops.facility.*;
import marsops.environment.*;
public class RescueMission extends Mission {
    public RescueMission(int id, String strandedEntity) {
        super(id, MissionType.RESCUE, "Rescue " + strandedEntity, 2);
        requireAgent(new AgentRequirement(Category.ROBOT, Capability.SURFACE_TRAVEL, 1));
        requireAgent(new AgentRequirement(Category.HUMAN, Capability.MEDICAL, 1));
        requireEquipment(EquipmentType.COMMUNICATION);
    }

    @Override
    public boolean isBlockedBy(Condition condition) {
        return condition == Condition.DUST_STORM
            || condition == Condition.COMMUNICATION_BLACKOUT;
    }

    protected void onComplete() { }
}
