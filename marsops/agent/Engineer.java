package marsops.agent;

import marsops.facility.Location;

public class Engineer extends CrewMember {
    public Engineer(int id, String name, Location location) {
        super(id, name, Category.HUMAN, Capability.REPAIR, location);
    }
}