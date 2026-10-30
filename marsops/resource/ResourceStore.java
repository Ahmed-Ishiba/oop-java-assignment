package marsops.resource;

import java.util.*;

public class ResourceStore {
    private int[] quantites = new int[ResourceType.values().length];

    public int getQuantity(ResourceType type) {
        return quantites[type.ordinal()];
    }

    public void add(ResourceType type, int amount) {
        if (amount < 0)
            return;
        quantites[type.ordinal()] = quantites[type.ordinal()] + amount;
    }

    public boolean has(ResourceType type, int amount) {
        if (amount < 0)
            return true;
        return quantites[type.ordinal()] >= amount;
    }

    public boolean remove(ResourceType type, int amount) {
        if (amount < 0)
            return false;
        if (!has(type, amount))
            return false;
        quantites[type.ordinal()] -= amount;
        return true;
    }
}