package marsops.environment;

import marsops.mission.Mission;

public class Environment {
    private Condition condition = Condition.NORMAL;
    public Condition getCondition() {return condition;}
    public void setCondition(Condition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }
        this.condition = condition;
    }
    public boolean isNormal() {return condition == Condition.NORMAL;}
    public boolean isBlocked(Mission mission) {return mission.isBlockedBy(condition);}

    public String getRestrictionReason() {
        switch (condition) {
            case DUST_STORM:
                return "Surface operations are not allowed during a dust storm.";
            case COMMUNICATION_BLACKOUT:
                return "Communication-dependent operations are not allowed during a communication blackout.";
            default:
                return "No environmental restrictions.";
        }
    }

    public boolean isRemoteControlAvailable() {return condition != Condition.COMMUNICATION_BLACKOUT;}

    public String describe() {
        switch (condition) {
            case DUST_STORM:
                return "Dust storm";
            case COMMUNICATION_BLACKOUT:
                return "Communication blackout";
            default:
                return "Normal";
        }
    }

    public String toString() {return "Environment[" + describe() + "]";}
}