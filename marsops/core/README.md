# core

Small helper classes shared by several packages.

- `AgentRequirement(category, capability, count)`: describes *who* is needed.
  `matches(agent)` compares the agent's `Category` and `Capability`. Missions and
  emergencies use it, so they never check concrete agent classes.
- `MissionResult`: returned by `MarsColony.submitMission`. Built with the static
  factories `accepted(mission)` or `rejected(mission, reason)`.
- `EmergencyResult`: returned by `MarsColony.triggerEmergency`. Built with
  `resolved(...)` or `unresolved(...)`.

The results are immutable. Their constructors are private, so they can only be
created through the factories.