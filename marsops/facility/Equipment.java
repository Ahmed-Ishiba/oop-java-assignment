package marsops.facility;

public class Equipment {
    private int id;
    private String name;
    private EquipmentType type;
    private EquipmentStatus status;

    public Equipment(int id, String name, EquipmentType type) {
        this(id, name, type, EquipmentStatus.OPERATIONAL);
    }

    public Equipment(int id, String name, EquipmentType type, EquipmentStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        if (status == null) {
            this.status = EquipmentStatus.OPERATIONAL;
        } else
            this.status = status;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public boolean isOperational() {
        return status == EquipmentStatus.OPERATIONAL;
    }

    public void damage() {
        status = EquipmentStatus.DAMAGED;
    }

    public void repair() {
        status = EquipmentStatus.OPERATIONAL;
    }
}
