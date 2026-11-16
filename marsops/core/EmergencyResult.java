package marsops.core;

import marsops.emergency.Emergency;

public class EmergencyResult {
    private final boolean resolved;
    private final Emergency emergency;
    private final String message;

    private EmergencyResult(boolean resolved, Emergency emergency, String message) {
        this.resolved = resolved;
        this.emergency = emergency;
        this.message = message;
    }

    public static EmergencyResult resolved(Emergency emergency, String message) {
        return new EmergencyResult(true, emergency, message);
    }

    public static EmergencyResult unresolved(Emergency emergency, String reason) {
        return new EmergencyResult(false, emergency, reason);
    }

    public boolean isResolved() {
        return resolved;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (resolved) {
            return "Emergency resolved: " + message;
        }
        return "Emergency unresolved: " + message;
    }
}