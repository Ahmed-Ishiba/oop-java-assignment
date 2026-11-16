# environment

- `Condition`: NORMAL, DUST_STORM, COMMUNICATION_BLACKOUT.
- `Environment`: holds the current condition. It can tell if it is
  `isNormal()`, give a `getRestrictionReason()` text, and
  `isRemoteControlAvailable()`.

`Environment` doesn't know which missions are restricted. It asks the mission:
`isBlocked(mission)` just calls `mission.isBlockedBy(condition)`, and each mission
type decides for itself.

`MarsColony` uses it in two places: when a mission is submitted, and when the
condition changes while a mission is active (the mission is then failed with
`getRestrictionReason()`).