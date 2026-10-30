package marsops.facility;

public class Storage extends Facility {
    public Storage(int id, String name, Location location, int capacity) {
        super(id, name, location, capacity);
    }

    @Override
    public String getType() {
        return "Stroage";
    }

}
