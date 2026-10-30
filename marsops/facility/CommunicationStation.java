package marsops.facility;

public class CommunicationStation extends Facility {
    public CommunicationStation(int id, String name, Location location, int capacity) {
        super(id, name, location, capacity);
    }

    public boolean isCommunicationAvailable() {
        return isOperational();
    }

    @Override
    public String getType() {
        return "Communication Station";
    }
}