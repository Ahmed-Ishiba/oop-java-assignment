package marsops.agent;

import marsops.facility.Location;

public class Drone extends Robot {
    public Drone(int id, String name, Location location) {
        super(id, name, Category.ROBOT, Capability.RECON, location);
    }
}