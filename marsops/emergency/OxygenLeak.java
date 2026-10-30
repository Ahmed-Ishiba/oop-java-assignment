package marsops.emergency;

import marsops.agent.Capability;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.Location;

public class OxygenLeak extends Emergency {

    public OxygenLeak(Location habitat) {
        super(EmergencyType.OXYGEN_LEAK, habitat);
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.REPAIR, 1));
        addRequirement(new AgentRequirement(Category.ROBOT, Capability.REPAIR, 1));
    }

    public void applyImmediateEffects() {
        return;
    }
}