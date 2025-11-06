# Changelog

### 2.0.0
- Initial version

### 2.0.1
- Fixed datapack versions so it's no longer invalid.

### 2.0.2
- Fixed gravity blocks spawning (gravel, sand etc)
- Fixed that blocks like Chorus blocks can spawn and stay there. Only water and air are not allowed at the oneblock location, if found it will spawn a new block.
- Added friendly mob spawns
- Added difficulty based spawns (Easy has more frequent item drops and friendly spawns while having less enemies spawn, normal is unchanged, hard will spawn more frequent and harder enemies and less frequent friendly or item spawns)
- Added aquatic mob spawns (if under the one block is enough water)

### 2.0.3
- Added monster spawns for The Nether and The End
  > If playing with the void Nether and void End datapacks enabled, triggering a mob wave will include monsters from that dimension. So for example, Blazes and Wither Skeletons can now spawn as part of the mob wave in The Nether.


## 2.1.0
### Added
- Multiplayer mode with per-player islands.
- Island generation system for individual player spawns.
- Dynamic block weighting that fades to full randomness after 10,000 breaks.
- Friendly mob and aquatic spawn events (fish, axolotls, frogs, dolphins).
- Wandering Trader friendly event.
- Automatic legacy save migration for older worlds.
- Update checker that notifies players when a new version is available.
- Global stats system when multiplayer is disabled (shared island worlds).
- Weighted spawn system based on world difficulty (Easy, Normal, Hard).
- System message announcing legacy data import on load.

### Changed
- Block weighting rebalanced — logs and planks are now much more common than wool.
- Early progression focuses on useful materials, shifting toward random blocks later.
- Mob and friendly spawn events are now instant (no countdown).
- Player spawn system rewritten to use modern API (fixes invalid player data issues).
- Event frequency now scales with world difficulty.
- Simplified spawn point logic for safety and backward compatibility.
- Smoothed block hardness scaling for a more natural progression curve.
- Shared island behavior when multiplayer pack is not enabled.
- Server message now shows each player’s island coordinates on join.

### Improved
- Stability and performance during state loading and island generation.
- Persistent save handling for both per-player and global stats.
- World state system refactored for cleaner serialization.
- Weighted bag generation optimized for better balance and variety.
- Compatibility with existing worlds preserved through automatic migration.
- Code cleanup and consistency improvements across world generation and event logic.

### Fixed
- Invalid player data errors from older spawn point logic.
- Duplicate block entries appearing in random selection pools.
- Crashes caused by legacy world states using old persistent keys.
- Incorrect spawn weighting that caused excessive wool generation.
- Rare event scheduling bugs with simultaneous monster/friendly spawns.
- Player data not saving properly after forced server shutdowns.
- Safe spawn validation to prevent players spawning in the void.

