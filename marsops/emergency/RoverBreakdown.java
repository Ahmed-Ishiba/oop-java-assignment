package marsops.emergency;

import marsops.agent.Capability;
import marsops.agent.Category;
import marsops.agent.Robot;
import marsops.core.AgentRequirement;
import marsops.facility.Location;
import marsops.mission.Mission;
import marsops.resource.ResourceType;

public class RoverBreakdown extends Emergency {
    private final Robot rover;
    private final Mission mission;   // may be null

    public RoverBreakdown(Robot rover, Mission mission) {
        super(EmergencyType.BREAKDOWN, Location.MARS_SURFACE);
        this.rover = rover;
        this.mission = mission;
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.REPAIR, 1));
        addRequirement(new AgentRequirement(Category.ROBOT, Capability.REPAIR, 1));
        requireResource(ResourceType.SPARE_PARTS, 2);
        requireResource(ResourceType.BATTERY_POWER, 5);
    }

    @Override
    public void applyImmediateEffects() {
        rover.setLocation(Location.MARS_SURFACE);
        if (mission != null && !mission.isFinished()) {
            mission.fail("Rover " + rover.getName() + " broke down");
        }
        rover.takeDamage(rover.getHealth());   // after the mission fails, so release() runs first
    }

    @Override
    protected void onResolved() { rover.restoreService(100); }
}