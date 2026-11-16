# resource

- `ResourceType`: OXYGEN, WATER, FOOD, FUEL, BATTERY_POWER, SPARE_PARTS,
  MEDICAL_SUPPLIES.
- `ResourceStore`: stores one quantity per `ResourceType` in an `int[]` indexed
  by the enum's ordinal. `add`, `has`, `remove` (all-or-nothing) and
  `removeUpTo` (takes what is available and returns the amount taken).
- `FacilityStatus`: OPERATIONAL, DAMAGED, UNDER_REPAIR, DESTROYED. It is used by
  `Facility`.

Each `Facility` owns its own `ResourceStore`. `MarsColony` adds the stores of the
operational facilities together to get colony totals, and `consumeResources`
takes from the facilities one by one with `removeResourceUpTo`.