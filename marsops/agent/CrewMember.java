package marsops.agent;

import marsops.facility.Location;

public class CrewMember extends AbstractAgent {
    private int fatigue;

    public CrewMember(int id, String Name, Category category, Capability capability, Location location) {
        super(id, Name, category, capability, location);
        this.fatigue = 0;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = Math.max(0, fatigue);
        this.fatigue = Math.min(100, this.fatigue);
    }

    public boolean isExhausted() {
        return fatigue >= 100;
    }

    public void rest(int amount) {
        setFatigue(fatigue - amount);
        fatigue = Math.max(0, fatigue);
    }

    @Override
    public boolean canPerform(Capability capability) {
        if (isExhausted()) {
            return false;
        }
        return super.canPerform(capability);
    }

}
