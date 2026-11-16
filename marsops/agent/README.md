# agent

The "workers" of the colony.

```
Agent (interface)
  -> AbstractAgent (shared state: id, name, health, location, AgentState, currentMission)
       -> CrewMember (adds fatigue)   -> Scientist, Engineer, MedicalOfficer
       -> Robot      (adds battery)   -> ExplorationRover, RepairRobot, Drone
```

- `Agent` is the interface the rest of the code depends on (`MarsColony`,
  `Mission`, `Emergency` never use the concrete classes).
- `AbstractAgent` implements the common behaviour: `assignTo()` sets
  `ON_MISSION`, `release()` sets `AVAILABLE`, `takeDamage()` drops health and
  sets `INACTIVE` at 0, `canRespondTo(emergency)` asks `emergency.requires(this)`.
- `CrewMember` and `Robot` override `canPerform()` (exhausted / empty battery
  means no) and `tick()` (fatigue goes up on a mission, battery goes down).
- The leaf classes (`Scientist`, `Drone`, ...) only fix the `Category` and
  `Capability` in their constructor.
- Enums: `Category` (HUMAN/ROBOT), `Capability` (what the agent can do),
  `AgentState` (AVAILABLE/ON_MISSION/INACTIVE).

An `AgentRequirement` matches an agent by `Category` + `Capability`.