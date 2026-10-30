package marsops.mission;

import marsops.agent.Capability;
import marsops.resource.ResourceType;
import marsops.agent.Category;
import marsops.core.AgentRequirement;

public class RepairMission extends Mission {
    public RepairMission(int id, String region){
        super(id, MissionType.REPAIR);
        requireAgent(new AgentRequirement(Category.HUMAN, Capability.REPAIR, 1));
        requireAgent(new AgentRequirement(Category.ROBOT, Capability.REPAIR, 1));
        requireResource(ResourceType.SPARE_PARTS, 3);
    }
}
