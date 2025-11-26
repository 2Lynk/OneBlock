# OneBlock Admin Panel - Usage Guide

## Overview
The OneBlock mod now includes a fully server-side admin panel that allows OPs to adjust all game settings through an in-game GUI. No client-side mods required!

## Opening the Admin Panel

### GUI Method (Recommended)
```
/oneblock admin
```
This opens a chest-like interface with all settings displayed as clickable items.

### Command Method (Alternative)
You can also use text commands:
```
/oneblock config list
/oneblock config set <setting> <value>
/oneblock config toggle <feature>
```

## Using the GUI

The admin panel is organized into 6 rows:

### Row 1: Wave Settings
- **Mob Waves Toggle** - Enable/disable mob waves completely
- **Wave Chance** - Adjust how often waves are scheduled (default: 1 in 100)
- **Max Wave Size** - Maximum number of mobs in a wave (default: 100)
- **Wave Size Divisor** - Controls wave size scaling (default: 100)

### Row 2: Drop Settings
- **Item Drops Toggle** - Enable/disable random item drops
- **Item Drop Chance** - How often items drop (default: 1 in 100)
- **Friendly Mobs Toggle** - Enable/disable friendly mob spawns
- **Friendly Mob Chance** - How often friendly mobs spawn (default: 1 in 100)

### Row 3: Item Settings
- **Item Drop Count** - How many items spawn when drop chance triggers (1-64, default: 1)
- **Lava Bucket Chance** - Chance for lava bucket to replace regular item drops (default: 1 in 500)

### Row 4: End Portal Frame Settings
- **Portal Frame Drops Toggle** - Enable/disable end portal frame drops
- **Portal Frame Chance** - How often they drop (default: 1 in 200)
- **Min Breaks Required** - Minimum blocks broken before they can drop (default: 10,000)

### Row 5: Progression
- **Progression Cap** - Total breaks until maximum block hardness is unlocked (default: 10,000)

### Row 6: Wave Countdown Tiers
- **Tier 0-6** - Adjust how many breaks until wave spawns for each progression tier
  - Tier 0 (0+ breaks): 12 breaks
  - Tier 1 (500+ breaks): 10 breaks
  - Tier 2 (1500+ breaks): 8 breaks
  - Tier 3 (3000+ breaks): 6 breaks
  - Tier 4 (5000+ breaks): 5 breaks
  - Tier 5 (8000+ breaks): 4 breaks
  - Tier 6 (12000+ breaks): 3 breaks

### Row 7: Actions
- **Reset to Defaults** - Restore all settings to their original values
- **Help** - Shows control instructions
- **Close** - Exit the admin panel

## Controls

### Adjusting Values
- **Left Click**: Decrease value by 1
- **Right Click**: Increase value by 1
- **Shift + Left Click**: Decrease value by 10
- **Shift + Right Click**: Increase value by 10

### Toggle Items
- **Click**: Toggle between ON/OFF

### Special Items
- Some items (like Min Breaks Required and Progression Cap) adjust in increments of 100 when using shift-click

## Examples

### Make waves more frequent
1. Open `/oneblock admin`
2. Click on "Wave Chance"
3. Left-click to decrease the value (lower = more frequent)
4. Set to 50 for waves twice as often

### Increase wave difficulty
1. Open `/oneblock admin`
2. Find "Max Wave Size"
3. Right-click to increase (or shift+right-click for +10)
4. Set to 150 for larger waves

### Disable item drops temporarily
1. Open `/oneblock admin`
2. Click "Item Drops: ON"
3. It will toggle to "Item Drops: OFF"

### Make early game easier
1. Open `/oneblock admin`
2. Navigate to wave countdown tiers
3. Click Tier 0 and right-click to increase countdown
4. More breaks = more time to prepare

## Command Reference

### View Settings
```
/oneblock config list
```

### Get Specific Setting
```
/oneblock config get waveChance
/oneblock config get itemDropChance
/oneblock config get mobWavesEnabled
```

### Set Values
```
/oneblock config set waveChance 50
/oneblock config set maxWaveSize 150
/oneblock config set progressionCap 20000
```

### Toggle Features
```
/oneblock config toggle mobWaves
/oneblock config toggle itemDrops
/oneblock config toggle friendlyMobs
/oneblock config toggle endPortalFrame
```

### Reset Everything
```
/oneblock config reset
```

## Settings Reference

| Setting | Type | Default | Description |
|---------|------|---------|-------------|
| waveChance | Integer | 100 | 1-in-X chance to schedule a wave per break |
| itemDropChance | Integer | 100 | 1-in-X chance for item to drop per break |
| itemDropCount | Integer | 1 | Number of items to spawn when drop triggers (1-64) |
| lavaBucketChance | Integer | 500 | 1-in-X chance for lava bucket instead of regular item |
| friendlyMobChance | Integer | 100 | 1-in-X chance for friendly mob per break |
| endPortalFrameChance | Integer | 200 | 1-in-X chance for portal frame (after requirement) |
| endPortalFrameRequirement | Integer | 10000 | Minimum breaks needed for portal frames |
| maxWaveSize | Integer | 100 | Maximum mobs that can spawn in one wave |
| waveSizeDivisor | Integer | 100 | Divisor for calculating wave sizes |
| progressionCap | Integer | 10000 | Breaks needed to unlock all block types |
| mobWavesEnabled | Boolean | true | Enable/disable mob waves |
| itemDropsEnabled | Boolean | true | Enable/disable item drops |
| friendlyMobsEnabled | Boolean | true | Enable/disable friendly mob spawns |
| endPortalFrameEnabled | Boolean | true | Enable/disable portal frame drops |

## Notes

- All changes are saved automatically with the world data
- Settings persist across server restarts
- Only OPs (permission level 2+) can access the admin panel
- Changes take effect immediately
- The GUI is fully server-side - no client mods needed!
