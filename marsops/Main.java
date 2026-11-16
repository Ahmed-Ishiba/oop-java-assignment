package marsops;

import marsops.agent.*;
import marsops.colony.MarsColony;
import marsops.core.EmergencyResult;
import marsops.core.MissionResult;
import marsops.emergency.*;
import marsops.environment.Condition;
import marsops.facility.*;
import marsops.mission.*;
import marsops.resource.ResourceType;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== MARS-OPS Demonstration ===\n");

        // ===== Scenario 1: Successful Exploration =====
        System.out.println("--- Scenario 1: Successful Exploration ---");
        scenario1SuccessfulExploration();
        System.out.println();

        // ===== Scenario 2: Mission Rejection (unsuitable agent) =====
        System.out.println("--- Scenario 2: Mission Rejected (Unsuitable Agent) ---");
        scenario2MissionRejection();
        System.out.println();

        // ===== Scenario 3: Resource Shortage =====
        System.out.println("--- Scenario 3: Resource Shortage ---");
        scenario3ResourceShortage();
        System.out.println();

        // ===== Scenario 4: Emergency Handling (Oxygen Leak) =====
        System.out.println("--- Scenario 4: Oxygen Leak Emergency ---");
        scenario4OxygenLeak();
        System.out.println();

        // ===== Scenario 5: Environmental Effect (Dust Storm) =====
        System.out.println("--- Scenario 5: Dust Storm Blocks Exploration ---");
        scenario5DustStorm();
        System.out.println();
    }


    private static void scenario1SuccessfulExploration() {
        MarsColony colony = new MarsColony("Alpha Base");

        Habitat habitat = new Habitat(1, "Habitat A", new Location("Habitat A"), 6);
        habitat.addResource(ResourceType.OXYGEN, 300);
        habitat.addResource(ResourceType.WATER, 150);
        habitat.addResource(ResourceType.FOOD, 100);
        habitat.addResource(ResourceType.BATTERY_POWER, 200);
        habitat.addResource(ResourceType.SPARE_PARTS, 5);
        habitat.addResource(ResourceType.MEDICAL_SUPPLIES, 20);

        Laboratory lab = new Laboratory(2, "Laboratory", new Location("Lab"), 4);
        lab.addResource(ResourceType.OXYGEN, 100);
        lab.addResource(ResourceType.BATTERY_POWER, 100);
        lab.addResource(ResourceType.WATER, 50);
        lab.addEquipment(new Equipment(1, "Microscope", EquipmentType.SCIENTIFIC));
        lab.addEquipment(new Equipment(2, "Med-Bay Scanner", EquipmentType.MEDICAL));

        Storage storage = new Storage(3, "Storage Facility", new Location("Storage"), 10);
        storage.addResource(ResourceType.OXYGEN, 200);
        storage.addResource(ResourceType.FOOD, 200);
        storage.addResource(ResourceType.WATER, 200);
        storage.addResource(ResourceType.FUEL, 100);
        storage.addResource(ResourceType.SPARE_PARTS, 10);
        storage.addResource(ResourceType.BATTERY_POWER, 300);
        storage.addResource(ResourceType.MEDICAL_SUPPLIES, 15);

        PowerStation power = new PowerStation(4, "Power Station", new Location("Power"), 2);
        power.addResource(ResourceType.BATTERY_POWER, 100);

        CommunicationStation comm = new CommunicationStation(5, "Communication Station", new Location("Comms"), 2);

        colony.addFacility(habitat);
        colony.addFacility(lab);
        colony.addFacility(storage);
        colony.addFacility(power);
        colony.addFacility(comm);

        Location habLoc = habitat.getLocation();
        Scientist scientist = new Scientist(1, "Dr. Ada Voss", habLoc);
        ExplorationRover rover = new ExplorationRover(4, "Rover Perseverance", habLoc);
        colony.addAgent(scientist);
        colony.addAgent(rover);

        ExplorationMission mission = new ExplorationMission(1, "Jezero Crater");
        mission.assign(Arrays.asList(scientist, rover));

        System.out.println("BEFORE:");
        System.out.println("Colony: " + colony);
        System.out.println("Resources O2: " + colony.getResourceTotal(ResourceType.OXYGEN));
        System.out.println("Fuel: " + colony.getResourceTotal(ResourceType.FUEL));

        MissionResult result = colony.submitMission(mission);
        System.out.println("Submit -> " + result);
        System.out.println("Mission state: " + mission.getState());

        colony.advanceTime(3);

        System.out.println("AFTER:");
        System.out.println("Colony: " + colony);
        System.out.println("Mission state: " + mission.getState());
        System.out.println("Scientist fatigue: " + scientist.getFatigue());
        System.out.println("Rover battery: " + rover.getBattery());
    }


    private static void scenario2MissionRejection() {
        MarsColony colony = new MarsColony("Beta Base");

        Habitat habitat = new Habitat(1, "Habitat B", new Location("Habitat B"), 6);
        habitat.addResource(ResourceType.OXYGEN, 300);
        habitat.addResource(ResourceType.SPARE_PARTS, 5);
        habitat.addResource(ResourceType.BATTERY_POWER, 200);
        colony.addFacility(habitat);

        Location habLoc = habitat.getLocation();
        Scientist scientist = new Scientist(1, "Dr. James", habLoc);
        colony.addAgent(scientist);

        RepairMission mission = new RepairMission(1, habitat);
        mission.assign(Arrays.asList(scientist));

        MissionResult result = colony.submitMission(mission);
        System.out.println("Mission cannot be assigned.");
        System.out.println("Reason: " + result.getReason());
        System.out.println("Mission state: " + mission.getState());
    }

    private static void scenario3ResourceShortage() {
        MarsColony colony = new MarsColony("Gamma Base");

        Habitat habitat = new Habitat(1, "Habitat C", new Location("Habitat C"), 6);
        habitat.addResource(ResourceType.OXYGEN, 300);
        habitat.addResource(ResourceType.WATER, 150);
        colony.addFacility(habitat);

        Location habLoc = habitat.getLocation();
        Scientist scientist = new Scientist(1, "Dr. Chen", habLoc);
        ExplorationRover rover = new ExplorationRover(2, "Rover Z", habLoc);
        colony.addAgent(scientist);
        colony.addAgent(rover);

        ExplorationMission mission = new ExplorationMission(1, "Olympus Mons") {
            {
                requireResource(ResourceType.OXYGEN, 5000);
            }
        };
        mission.assign(Arrays.asList(scientist, rover));

        int oxygenBefore = colony.getResourceTotal(ResourceType.OXYGEN);
        MissionResult result = colony.submitMission(mission);

        System.out.println("Mission cannot be assigned.");
        System.out.println("Reason: " + result.getReason());
        System.out.println("Mission state: " + mission.getState());
        System.out
                .println("Oxygen before/after: " + oxygenBefore + " / " + colony.getResourceTotal(ResourceType.OXYGEN));
    }

    private static void scenario4OxygenLeak() {
        MarsColony colony = new MarsColony("Delta Base");

        Habitat habitat = new Habitat(1, "Habitat D", new Location("Habitat D"), 6);
        habitat.addResource(ResourceType.OXYGEN, 300);
        habitat.addResource(ResourceType.SPARE_PARTS, 5);
        habitat.addResource(ResourceType.BATTERY_POWER, 200);
        colony.addFacility(habitat);

        Location habLoc = habitat.getLocation();
        Engineer engineer = new Engineer(1, "Eng. Hassan", habLoc);
        RepairRobot repairRobot = new RepairRobot(2, "RepairBot-7", habLoc);
        colony.addAgent(engineer);
        colony.addAgent(repairRobot);

        int oxygenBefore = colony.getResourceTotal(ResourceType.OXYGEN);
        int spareBefore = colony.getResourceTotal(ResourceType.SPARE_PARTS);

        OxygenLeak leak = new OxygenLeak(habitat, 50);
        EmergencyResult result = colony.triggerEmergency(leak);

        System.out.println(result);
        System.out.println("Colony oxygen: " + oxygenBefore + " -> " + colony.getResourceTotal(ResourceType.OXYGEN));
        System.out.println("Spare parts: " + spareBefore + " -> " + colony.getResourceTotal(ResourceType.SPARE_PARTS));
        System.out.println("Emergency state: " + leak.getState());
    }


    private static void scenario5DustStorm() {
        MarsColony colony = new MarsColony("Epsilon Base");

        Habitat habitat = new Habitat(1, "Habitat E", new Location("Habitat E"), 6);
        habitat.addResource(ResourceType.OXYGEN, 300);
        habitat.addResource(ResourceType.FUEL, 100);
        habitat.addResource(ResourceType.BATTERY_POWER, 200);
        colony.addFacility(habitat);

        Location habLoc = habitat.getLocation();
        Scientist scientist = new Scientist(1, "Dr. Kim", habLoc);
        ExplorationRover rover = new ExplorationRover(2, "Rover X", habLoc);
        colony.addAgent(scientist);
        colony.addAgent(rover);

        colony.setEnvironmentCondition(Condition.DUST_STORM);

        ExplorationMission mission = new ExplorationMission(1, "Gale Crater");
        mission.assign(Arrays.asList(scientist, rover));

        MissionResult result = colony.submitMission(mission);
        System.out.println("Exploration Mission cannot start.");
        System.out.println("Reason: " + result.getReason());
        System.out.println("Mission state: " + mission.getState());
    }
}