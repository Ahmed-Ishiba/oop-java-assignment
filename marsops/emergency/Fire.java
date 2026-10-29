package marsops.emergency;
import marsops.agent.Capability;
import marsops.agent.Category;
import marsops.core.AgentRequirement;
import marsops.facility.Location;
public class Fire extends Emergency{
    
    public Fire(Location laboratory){
        super(EmergencyType.FIRE, laboratory);
        addRequirement(new AgentRequirement(Category.HUMAN, Capability.REPAIR,1));
        addRequirement(new AgentRequirement(Category.ROBOT, Capability.REPAIR,1));
    }
    public void applyImmediateEffects() {
        return;
    }
}
