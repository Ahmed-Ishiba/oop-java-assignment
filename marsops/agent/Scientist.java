package marsops.agent;

import marsops.facility.Location;

public class Scientist extends CrewMember {
    public Scientist(int id, String name, Location location) {
        super(id, name, Category.HUMAN, Capability.SCIENTIFIC_RESEARCH, location);
    }
}