package marsops.core;

import java.util.Locale.Category;

import marsops.agent.Capability;

// used by Missions and emergencies to describe who they need
public class AgentRequirement {
    private final Category category;
    private final Capability capability;
    private final int count;
    
    public AgentRequirement(Category category, Capability capability, int count){
        this.category = category;this.capability = capability;this.count = count;
    }
    public Category getCategory(){return this.category;}
    public Capability getCapability(){return this.capability;}
    public int getCount(){return this.count;}
}
