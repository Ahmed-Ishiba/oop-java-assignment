package marsops.mission;

import marsops.agent.Capability;
import marsops.resource.ResourceType;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.Equipment;
import marsops.facility.Facility;

public class RepairMission extends Mission {
    private final Facility facility;
    private final Equipment equipment;

    public RepairMission(int id, Facility facility) {
        this(id, "Repair " + facility.getName(), facility, null);
    }

    public RepairMission(int id, Equipment equipment) {
        this(id, "Repair " + equipment.getName(), null, equipment);
    }

    private RepairMission(int id, String description, Facility facility, Equipment equipment) {
        super(id, MissionType.REPAIR, description, 2);
        this.facility = facility;
        this.equipment = equipment;
        requireAgent(new AgentRequirement(Category.HUMAN, Capability.REPAIR, 1));
        requireAgent(new AgentRequirement(Category.ROBOT, Capability.REPAIR, 1));
        requireResource(ResourceType.SPARE_PARTS, 3);
    }

    @Override
    protected void onComplete() {
        if (facility != null) {
            facility.restore();
            for (Equipment item : facility.getEquipment())
                item.repair();
        }
        if (equipment != null)
            equipment.repair();
    }
}
