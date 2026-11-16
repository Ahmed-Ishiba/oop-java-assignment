# facility

The physical places of the colony.

```
Facility (abstract: id, name, location, capacity, status, ResourceStore, Equipment[20])
  -> Habitat (owns a LifeSupportSystem)
  -> Laboratory
  -> Storage
  -> PowerStation (generatePower() adds BATTERY_POWER)
  -> CommunicationStation (isCommunicationAvailable())
```

- `Facility` holds a `ResourceStore` (from the `resource` package) and an array
  of `Equipment`. It forwards `getQuantity / addResource / removeResource /
  removeResourceUpTo` to the store.
- `status` uses `FacilityStatus`. `damage()`, `restore()` and `destroy()` change
  it; only an `OPERATIONAL` facility counts for colony resource totals.
- `Equipment` has an `EquipmentType` and an `EquipmentStatus`
  (OPERATIONAL/DAMAGED). Missions list the `EquipmentType`s they need.
- `LifeSupportSystem` is used by `OxygenLeak` (`registerLeak()` / `sealLeak()`).
- `Location` is a simple named place. `Location.MARS_SURFACE` is the shared
  surface location (used by `RoverBreakdown`).