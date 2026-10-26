package marsops.agent;

import marsops.emergency.Emergency;
import marsops.mission.Mission;
import marsops.facility.*;
import java.util.*;

public interface Agent {
    int getId();

    String getName();

    Location getLocation();

    Capability getCapability();

    void setLocation(Location location);

    Category getCategory();

    int getHealth();

    boolean isAvailable();

    boolean canPerform(Capability capability);

    boolean canRespondTo(Emergency emergency);

    void assignTo(Mission mission);

    void release();

    Mission getCurrentMission();

    void heal(int amount);

    void takeDamage(int amount);
}