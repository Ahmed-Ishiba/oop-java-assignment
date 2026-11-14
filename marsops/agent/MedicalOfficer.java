package marsops.agent;

import marsops.facility.Location;

public class MedicalOfficer extends CrewMember {
    public MedicalOfficer(int id, String name, Location location) {
        super(id, name, Category.HUMAN, Capability.MEDICAL, location);
    }
}