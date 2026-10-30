package marsops.facility;

import marsops.resource.FacilityStatus;
import marsops.resource.ResourceStore;
import marsops.resource.ResourceType;

public abstract class Facility {

    private int id;
    private String name;
    private Location location;
    private int capacity;
    private FacilityStatus status;
    private ResourceStore resources = new ResourceStore();
    private Equipment[] equipment = new Equipment[20];
    private int equipmentCount;

    protected Facility(int id, String name, Location location, int capacity) {
        this(id, name, location, capacity, FacilityStatus.OPERATIONAL);
    }

    protected Facility(int id, String name, Location location, int capacity, FacilityStatus status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        if (status == null) {
            this.status = FacilityStatus.OPERATIONAL;
        } else
            this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public FacilityStatus getStatus() {
        return this.status;
    }

    public void setSTatus(FacilityStatus status) {
        this.status = status;
    }

    public boolean isOperational() {
        return status == FacilityStatus.OPERATIONAL;
    }

    public void damage() {
        if (status != FacilityStatus.DESTROYED)
            status = FacilityStatus.DAMAGED;
    }

    public void restore() {
        if (status != FacilityStatus.DESTROYED)
            status = FacilityStatus.OPERATIONAL;
    }

    public void destroy() {
        status = FacilityStatus.DESTROYED;
    }

    public int getQuantity(ResourceType type) {
        return resources.getQuantity(type);
    }

    public void addResource(ResourceType type, int amount) {
        resources.add(type, amount);
    }

    public boolean removeResource(ResourceType type, int amount) {
        return resources.remove(type, amount);
    }

    public Equipment[] addEquipment(Equipment item) {
        Equipment[] copy = new Equipment[equipmentCount];
        for (int i = 0; i < equipmentCount; i++) {
            copy[i] = equipment[i];
        }
        return copy;
    }

    public boolean hasOpEquipment(EquipmentType type) {
        for (int i = 0; i < equipmentCount; i++) {
            if (equipment[i].getType() == type && equipment[i].isOperational()) {
                return true;
            }
        }
        return false;
    }

    public Equipment findOpEquipment(EquipmentType type) {
        for (int i = 0; i < equipmentCount; i++) {
            if (equipment[i].getType() == type && equipment[i].isOperational()) {
                return equipment[i];

            }
        }
        return null;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " #" + id + " '" + name + "' [" + status + "] resources: " + resources;
    }

}
