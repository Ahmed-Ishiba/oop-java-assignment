package marsops.emergency;
import java.util.ArrayList;
import java.util.List;

import marsops.agent.Agent;
import marsops.agent.Capability;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.Equipment;
import marsops.facility.Laboratory;
public class Fire extends Emergency{
    private final Laboratory laboratory;
    private final List<Agent> personnelPresent = new ArrayList<>();

    public Fire(Laboratory laboratory) {
        super(EmergencyType.FIRE, laboratory.getLocation());
        this.laboratory = laboratory;
    }
    public void applyImmediateEffects() {
        laboratory.damage();
        for (Equipment item : laboratory.getEquipment()) {
            item.damage();
        }
        // TODO : oxygen consumption
    }
    
    public void addPersonnelPresent(Agent person) {
        if (person != null && !personnelPresent.contains(person)) {
            personnelPresent.add(person);
            if (personnelPresent.size() == 1) {
                addRequirement(new AgentRequirement(Category.HUMAN, Capability.MEDICAL, 1));
            }
        }
    }

}