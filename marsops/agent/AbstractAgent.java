package marsops.agent;

import java.util.*;
import marsops.core.AgentRequirement;
import marsops.emergency.Emergency;
import marsops.facility.Location;
import marsops.mission.Mission;

public abstract class AbstractAgent implements Agent {
    private final int id;
    private final String name;
    private final Category category;
    private final Capability capability;
    private Location location;
    private int health;
    private AgentState state;
    private Mission currentMission;

    protected AbstractAgent(int id, String name, Category category,
            Capability capability, Location location) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.capability = capability;
        this.location = location;
        this.health = 100;
        this.state = AgentState.AVAILABLE;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Capability getCapability() {
        return capability;
    }

    public Category getCategory() {
        return category;
    }

    public int getHealth() {
        return health;
    }

    public Mission getCurrentMission() {
        return currentMission;
    }

    // methods

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return state == AgentState.AVAILABLE;
    }

    public void assignTo(Mission mission) {
        this.currentMission = mission;
        this.state = AgentState.ON_MISSION;
    }

    public void release() {
        this.currentMission = null;
        this.state = AgentState.AVAILABLE;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.health = 0;
            this.state = AgentState.INACTIVE;
        }
    }

    public void heal(int amount) {
        if (this.state != AgentState.INACTIVE) {
            this.health += amount;
            if (this.health > 100) {
                this.health = 100;
            }
        }
    }

    public boolean physicallyCapable() {
        return this.health > 0;
    }

    public boolean canPerform(Capability capability) {
        if (!physicallyCapable() || !isAvailable()) {
            return false;
        }
        if (this.capability != capability) {
            return false;
        }
        return true;
    }
}
