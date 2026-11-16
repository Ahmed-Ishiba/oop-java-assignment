package marsops.core;

import marsops.mission.Mission;

public class MissionResult {
    private final boolean accepted;
    private final Mission mission;
    private final String reason;

    private MissionResult(boolean accepted, Mission mission, String reason) {
        this.accepted = accepted;
        this.mission = mission;
        this.reason = reason;
    }

    public static MissionResult accepted(Mission mission) {
        return new MissionResult(true, mission, null);
    }

    public static MissionResult rejected(Mission mission, String reason) {
        return new MissionResult(false, mission, reason);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public Mission getMission() {
        return mission;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        if (accepted) {
            return "Mission accepted: " + mission.getDescription();
        }
        return "Mission rejected: " + reason;
    }
}