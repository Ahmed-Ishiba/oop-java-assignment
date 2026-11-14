package marsops.agent;

import marsops.facility.Location;

public class ExplorationRover extends Robot {
    public ExplorationRover(int id, String name, Location location) {
        super(id, name, Category.ROBOT, Capability.SURFACE_TRAVEL, location);
    }
}