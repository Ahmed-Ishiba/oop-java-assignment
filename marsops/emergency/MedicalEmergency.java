package marsops.emergency;

import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.agent.Capability;

public class MedicalEmergency extends Emergency {
    public MedicalEmergency() {
        super();
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.MEDICAL, 1));
    }

    public void applyImmediateEffects() {
        return;
    }
}
