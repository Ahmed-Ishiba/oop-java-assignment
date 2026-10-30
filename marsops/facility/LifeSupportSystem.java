package marsops.facility;

public class LifeSupportSystem {
    private int habitatId;
    private boolean leaking;
    private int efficiency;

    public LifeSupportSystem(int habitatId) {
        this.habitatId = habitatId;
        this.leaking = false;
        this.efficiency = 100;
    }

    public int getHabitatId() {
        return habitatId;
    }

    public boolean isLeaking() {
        return leaking;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void registerLeak() {
        leaking = true;
        efficiency = efficiency - 30;
        if (efficiency < 0) {
            efficiency = 0;
        }
    }

    public void sealLeak() {
        leaking = false;
        efficiency = 100;
    }

    @Override
    public String toString() {
        return "LifeSupportSystem[habitat=" + habitatId + ", leaking=" + leaking + ", efficiency=" + efficiency + "%]";
    }
}