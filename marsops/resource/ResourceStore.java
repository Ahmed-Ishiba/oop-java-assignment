package marsops.resource;

import java.util.*;

public class ResourceStore {
    private int[] quantities = new int[ResourceType.values().length];

    public int getQuantity(ResourceType type) {
        return quantities[type.ordinal()];
    }

    public void add(ResourceType type, int amount) {
        if (amount < 0)
            return;
        quantities[type.ordinal()] = quantities[type.ordinal()] + amount;
    }

    public boolean has(ResourceType type, int amount) {
        if (amount < 0)
            return true;
        return quantities[type.ordinal()] >= amount;
    }

    public boolean remove(ResourceType type, int amount) {
        if (amount < 0)
            return false;
        if (!has(type, amount))
            return false;
        quantities[type.ordinal()] -= amount;
        return true;
    }

    public int removeUpTo(ResourceType type, int amount) {
        int available = quantities[type.ordinal()];
        if (amount < 0) {
            amount = 0;
        }
        int toRemove = available;
        if (amount < available) {
            toRemove = amount;
        }
        quantities[type.ordinal()] = available - toRemove;
        return toRemove;
    }
}