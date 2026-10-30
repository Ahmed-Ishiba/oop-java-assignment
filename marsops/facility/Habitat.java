package marsops.facility;

public class Habitat extends Facility {
    private LifeSupportSystem lifeSupport;

    public Habitat(int id, String name, Location location, int capacity) {
        super(id, name, location, capacity);
        this.lifeSupport = new LifeSupportSystem(id);
    }

    public LifeSupportSystem getLifeSupportSystem() {
        return lifeSupport;

    }

    @Override
    public String getType() {
        return "Habitat";
    }

}