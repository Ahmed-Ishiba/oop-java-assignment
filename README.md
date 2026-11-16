# documentation  

this is over-view of this project, further documentation can be found in their respective directories  

## System Description and Main Components

MARS-OPS is a Java console simulation of a Mars colony. It models the colony's
crew and robots, its facilities and resources, the missions it runs, the
emergencies that can happen, and the environment around it. Time moves in
discrete units. The user (`Main`) submits missions, triggers emergencies and
advances time, and the colony reports what was accepted, completed, failed or
resolved.

### How it works in short

1. The colony is set up with agents (crew members and robots) and facilities
   (which hold resources and equipment).
2. A mission is submitted. The colony checks that the right agents are
   available, the resources exist and the environment allows it. The mission
   is either accepted and becomes the active mission, or rejected with a reason.
3. Time advances. Agents tire or drain their batteries, and the active mission
   progresses until it completes (applying its effect) or fails.
4. An emergency can be triggered at any time. The colony looks for responders and
   resources and marks it resolved or unresolved.
5. The environment (normal, dust storm, communication blackout) can change and
   fail an active mission that it blocks.

### Main components

| Package | Role |
|---|---|
| [`colony`](marsops/colony) | `MarsColony`, the facade. It owns the agents, facilities, environment and the single active mission, and runs all the rules. |
| [`agent`](marsops/agent) | The workers: an `Agent` interface, an abstract base, and crew members (`Scientist`, `Engineer`, `MedicalOfficer`) and robots (`ExplorationRover`, `RepairRobot`, `Drone`). |
| [`facility`](marsops/facility) | Places such as `Habitat`, `Laboratory`, `Storage`, `PowerStation` and `CommunicationStation`, plus `Equipment`, `LifeSupportSystem` and `Location`. |
| [`resource`](marsops/resource) | `ResourceType` and `ResourceStore`: the quantities each facility holds. |
| [`mission`](marsops/mission) | Abstract `Mission` with its lifecycle, and the four mission types (exploration, research, rescue, repair). |
| [`emergency`](marsops/emergency) | Abstract `Emergency` and its four types (oxygen leak, fire, rover breakdown, medical emergency). |
| [`environment`](marsops/environment) | The current `Condition` and the restrictions it puts on missions. |
| [`core`](marsops/core) | Small shared classes: `AgentRequirement`, `MissionResult` and `EmergencyResult`. |

### Key design ideas

- **Facade:** everything goes through `MarsColony`; missions and emergencies only
  describe what they need, and the colony decides.
- **Inheritance and polymorphism:** `Agent`, `Facility`, `Mission` and `Emergency`
  each have a base type with specialised children.
- **Template Method:** `Mission.complete()` does the shared work and then calls
  the child's `onComplete()`.
- **Requirement objects:** `AgentRequirement` describes who is needed, so
  missions never check concrete agent classes.
- **Composition and aggregation:** a facility owns its `ResourceStore`; the
  colony only references its agents and facilities.

The UML diagrams (class, object, state machine, sequence and use case) are in
`uml-files/`.


Regarding the uml diagrams:  
we know that the diagrams are not complete or do they represent the project 100% since i dropped some things for the sake of simplicity like:  
- setters and getters as well as enums in the class diagram
- the object diagram shows 8 out of 20 (colony, environment, scientist, rover, habitat and mission) the most important
- state machine is just 5 states so it stays the same
- sequence diagram does not show the emergency scenarios (but in the original it does)

---

now to clarify some stuff:  
- mission states are defined as enums and tracked using the following methods `MissionState`, `isFinished`,`fail`, `start`, `complete` and other methods defined in the missions abstract class
- when a mission is underway and an environmental change happens the mission fails
- for the emergencies we decided the requirements were
    - fire -> need 1 repair man and 1 repair robot
    - medical -> 1 human with medical capability and consume 1 medical supplies
    - oxygen leak -> 1 repair human, 1 repair robot and consume 2 spare parts
    - rover breakdown ->1 repair human, 1 repair robot, consume 2 spare parts and 5 battery power