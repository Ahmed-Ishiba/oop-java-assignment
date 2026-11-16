# mission

`Mission` is an abstract class holding everything a mission shares: id,
description, duration, elapsed time, `MissionState`, the list of
`AgentRequirement`s, required resources, required `EquipmentType`s, the assigned
agents and the failure reason.

Each value of the `MissionType` enum has one child class:

| MissionType | Class | Needs | Blocked by |
|---|---|---|---|
| EXPLORATION | `ExplorationMission` | human scientist + surface-travel robot | DUST_STORM |
| RESEARCH | `ResearchMission` | human scientist, 5 battery power | nothing |
| RESCUE | `RescueMission` | surface-travel robot + human medic | DUST_STORM, COMMUNICATION_BLACKOUT |
| REPAIR | `RepairMission` | human + robot repair, 3 spare parts | nothing |

The children only set their requirements in the constructor (using the protected
`requireAgent / requireResource / requireEquipment`) and override
`isBlockedBy(condition)` and `onComplete()`. For example `RepairMission.onComplete()`
restores the facility or equipment.

**Lifecycle** (`MissionState`): `CREATED -> ASSIGNED -> IN_PROGRESS -> COMPLETED`,
or `FAILED` from any unfinished state.
`assign()` -> `start()` -> `tick()` (elapsed++, calls `complete()` at the duration)
-> `complete()` releases the agents then calls the abstract `onComplete()`
(Template Method). Every step throws `IllegalStateException` if called in the
wrong state, so a failed mission can't be reused.

The colony is what decides whether a mission is accepted; the mission only
describes its needs.