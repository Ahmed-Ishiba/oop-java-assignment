package marsops.agent;

import marsops.facility.Location;

public class RepairRobot extends Robot {
    public RepairRobot(int id, String name, Location location) {
        super(id, name, Category.ROBOT, Capability.REPAIR, location);
    }
}