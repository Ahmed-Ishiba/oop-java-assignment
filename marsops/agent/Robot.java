package marsops.agent;

import marsops.facility.Location;

public class Robot extends AbstractAgent {
    private int battery;

    public Robot(int id, String name, Category category,
            Capability capability, Location location) {
        super(id, name, category, capability, location);
        this.battery = 100;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        if (battery < 0) {
            this.battery = 0;
        } else if (battery > 100) {
            this.battery = 100;
        } else {
            this.battery = battery;
        }
    }

    public void charge(int amount) {
        if (amount < 0) {
            return;
        }
        battery = battery + amount;
        if (battery > 100) {
            battery = 100;
        }
    }

    public int consumeBattery(int amount) {
        if (amount < 0) {
            return 0;
        }
        int consumed = battery;
        if (amount < battery) {
            consumed = amount;
        }
        battery = battery - consumed;
        return consumed;
    }

    @Override
    public boolean canPerform(Capability capability) {
        if (battery <= 0) {
            return false;
        }
        return super.canPerform(capability);
    }

    public void tick() {
        if (getCurrentMission() != null) {
            battery = battery - 10;
            if (battery < 0) {
                battery = 0;
            }
        }
    }
}