package marsops.emergency;

import marsops.agent.Capability;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.Habitat;
import marsops.facility.Location;
import marsops.resource.ResourceType;

public class OxygenLeak extends Emergency {

    private final Habitat habitat;
    private final int oxygenLoss;

    public OxygenLeak(Habitat habitat, int oxygenLoss) {
        super(EmergencyType.OXYGEN_LEAK, habitat.getLocation());
        this.habitat = habitat;
        this.oxygenLoss = oxygenLoss;
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.REPAIR, 1));
        addRequirement(new AgentRequirement(Category.ROBOT, Capability.REPAIR, 1));
        requireResource(ResourceType.SPARE_PARTS, 2);
    }

    public void applyImmediateEffects() {
        habitat.getLifeSupportSystem().registerLeak();
        deductOxygen();
    }

    protected void applyUnresolvedPenalty() { deductOxygen(); }
    protected void onResolved() { habitat.getLifeSupportSystem().sealLeak(); }

    private void deductOxygen() {
        int lost = Math.min(oxygenLoss, habitat.getQuantity(ResourceType.OXYGEN));
        habitat.removeResource(ResourceType.OXYGEN, lost);
    }
}