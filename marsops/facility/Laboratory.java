package marsops.facility;

public class Laboratory extends Facility {
    public Laboratory(int id, String name, Location location, int capacity) {
        super(id, name, location, capacity);
    }

    @Override
    public String getType() {
        return "Laboratory";
    }
}
