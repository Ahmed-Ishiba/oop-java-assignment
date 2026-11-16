# emergency

`Emergency` is an abstract class describing an event: its `EmergencyType`,
`Location`, `EmergencyState` (ACTIVE, then RESOLVED or UNRESOLVED), the
responders it needs (`AgentRequirement`s) and the resources the response costs.

Each value of the `EmergencyType` enum has one child class:

| EmergencyType | Class | Immediate effect | When resolved |
|---|---|---|---|
| OXYGEN_LEAK | `OxygenLeak` | registers a leak, removes oxygen from the habitat | seals the leak |
| FIRE | `Fire` | damages the lab and its equipment | restores the lab |
| BREAKDOWN | `RoverBreakdown` | fails the rover's mission, rover takes full damage | restores the rover |
| MEDICAL_EMERGENCY | `MedicalEmergency` | none yet (TODO) | heals the patient |

Each child sets its requirements in the constructor
(`addRequirement`, `requireResource`) and implements the abstract
`applyImmediateEffects()`. It can also override the hooks `onResolved()` and
`applyUnresolvedPenalty()`, which the base class calls from `markResolved()` and
`markUnresolved(reason)`. (`OxygenLeak` uses the penalty hook to lose oxygen a
second time.)

An emergency doesn't respond to itself. `MarsColony.triggerEmergency()` finds the
agents (`requires(agent)`), checks the resources and calls `markResolved()` or
`markUnresolved()`.