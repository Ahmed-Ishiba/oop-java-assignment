# colony

Holds `MarsColony`, the **facade** of the whole system. Everything else is
reached through it: `Main` only talks to the colony.

It keeps the lists of `Agent`s and `Facility`s, one `Environment`, and at most
one `activeMission`.

- `submitMission(mission)` runs the checks in order (mission still `CREATED`,
  no other active mission, agents assigned, every `AgentRequirement` satisfied,
  resources available, environment not blocking). If one fails it returns
  `MissionResult.rejected(reason)`. Otherwise it consumes resources, assigns the
  agents and makes the mission active.
- `advanceTime(units)` ticks every agent, then starts/ticks the active mission.
  Agents are released and `onComplete()` runs inside `Mission.tick()` when the
  duration is reached.
- `triggerEmergency(emergency)` applies the emergency's immediate effects, looks
  for responders, checks resources, then marks it resolved or unresolved and
  returns an `EmergencyResult`.
- `setEnvironmentCondition(condition)` changes the `Environment` and fails the
  active mission if it is blocked by the new condition.
- Resource totals are summed over **operational** facilities only
  (`getResourceTotal`, `canSupply`, `consumeResources`).

The colony decides; missions and emergencies only describe what they need.