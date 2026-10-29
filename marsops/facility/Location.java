package marsops.facility;

import java.util.*;

public class Location {
    public static final Location MARS_SURFACE = new Location("Mars Surface", true);

    private String name;
    private boolean surface;

    public Location(String name) {
        this(name, false);
    }

    private Location(String name, boolean surface) {
        this.name = name;
        this.surface = surface;
    }

    public String getName() {
        return name;
    }

    public boolean isSurface() {
        return surface;
    }

}
