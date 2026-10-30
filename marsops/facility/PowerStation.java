package marsops.facility;

import marsops.resource.ResourceType;

public class PowerStation extends Facility {

    private int outputPerTimeUnit;

    public PowerStation(int id, String name, Location location, int capacity) {
        this(id, name, location, capacity, 10);
    }

    public PowerStation(int id, String name, Location location, int capacity, int outputPerTimeUnit) {
        super(id, name, location, capacity);
        if (outputPerTimeUnit < 0) {
            this.outputPerTimeUnit = 0;
        } else {
            this.outputPerTimeUnit = outputPerTimeUnit;
        }
    }

    public int getOutputPerTimeUnit() {
        return outputPerTimeUnit;
    }

    public int generatePower() {
        if (!isOperational()) {
            return 0;
        }
        addResource(ResourceType.BATTERY_POWER, outputPerTimeUnit);
        return outputPerTimeUnit;
    }

    @Override
    public String getType() {
        return "Power Station";
    }
}
