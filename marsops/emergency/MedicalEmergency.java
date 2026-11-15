package marsops.emergency;

import marsops.agent.Category;
import marsops.agent.CrewMember;
import marsops.core.AgentRequirement;
import marsops.facility.Facility;
import marsops.resource.ResourceType;
import marsops.agent.Capability;

public class MedicalEmergency extends Emergency {
    private static final int TREATMENT_HEAL = 50;
    private final CrewMember patient;
    private final Facility facility;

    public MedicalEmergency(CrewMember patient, Facility facility) {
        super(EmergencyType.MEDICAL_EMERGENCY, facility.getLocation());
        this.patient = patient;
        this.facility = facility;
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.MEDICAL, 1));
        requireResource(ResourceType.MEDICAL_SUPPLIES, 1);
    }

    public boolean isFacilityOperational() { return facility.isOperational(); }

    public void applyImmediateEffects() { } // TODO

    protected void onResolved() { patient.heal(TREATMENT_HEAL); }
}
