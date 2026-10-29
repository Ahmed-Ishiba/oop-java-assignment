package marsops.emergency;

import marsops.facility.Location;
import java.util.ArrayList;
import java.util.Collections;

import marsops.core.AgentRequirement;
import java.util.List;
public abstract class Emergency {
    private EmergencyType Emergency_type;
    private Location location;
    private List<AgentRequirement> responderRequirement = new ArrayList<>();
    private EmergencyState state = EmergencyState.ACTIVE;
    private String reason ;
   
    protected Emergency(EmergencyType Emergency_type, Location location){
            this.Emergency_type = Emergency_type;
            this.location = location;
    }
    protected Emergency(){
        this.Emergency_type = EmergencyType.MEDICAL_EMERGENCY;
        
    }
    public EmergencyType getType() {return Emergency_type;}
    public Location getLocation() {return location;}
    public EmergencyState getState() {return state;}
    public String getReason() {return reason;}
    public List<AgentRequirement> getResponderRequirements(){return Collections.unmodifiableList(responderRequirement);}
    
    protected void addRequirement(AgentRequirement requirement) {
        responderRequirement.add(requirement);
    }
    
    public abstract void applyImmediateEffects();
}
