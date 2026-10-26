package marsops.core;

// used for status reporting
public class EmergencyResult {
    private final boolean resolved;
    private final String message;

    public EmergencyResult(boolean resolved, String message) {
        this.resolved = resolved;
        this.message = message;
    }
}