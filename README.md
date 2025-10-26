# 🧱 OneBlock — Player Guide (Event Rules & Exact Chances)

Welcome to **OneBlock**: your endless regenerating island in the void.  
Each block break has a chance to cause something special — whether it's a new block, a monster attack, or a friendly visit.  
This guide explains exactly what can happen, when, and how the **difficulty setting** changes your odds.

---

## 🏝️ Your Start

- You begin on a **small circular island** about **5 blocks in radius**, **2 blocks thick**:  
  Bottom at **Y=62**, top at **Y=63**, and your OneBlock at **Y=64**.  
- Island material matches the dimension:  
  - **Overworld:** Grass and dirt layers.  
  - **Nether:** Netherrack.  
  - **The End:** End Stone.  
- You always spawn safely above your OneBlock.  
- Everything else is **void** — this is your whole world.

---

## 🔁 What Happens on Each Break

Every time you break the OneBlock, one of several things may happen:

| Event | Description | Base Chance | Easy | Normal | Hard | Peaceful |
|:------|:-------------|:------------|:------|:--------|:-------|:----------|
| 🧱 **Block Regeneration** | A new random block appears from the progression pool. | Always | — | — | — | ✅ Always |
| 💀 **Monster Attack** | Instantly spawns a wave of enemies around your OneBlock. | 1 in 100 | 1 in 200 (half as likely) | 1 in 100 | 1 in 50 (twice as likely) | ❌ Disabled |
| 🐑 **Friendly Visit** | Spawns peaceful or neutral mobs — animals, villagers, traders. | 1 in 100 | 1 in 50 (twice as likely) | 1 in 100 | 1 in 200 (half as likely) | ✅ Allowed |
| 🪙 **Random Item Drop** | Drops a random safe item on or near your OneBlock. | 1 in 100 | 1 in 50 (twice as likely) | 1 in 100 | 1 in 100 (half as likely) | ✅ Allowed |

> These chances are rolled **independently** on every block break.  
> It’s possible (though rare) for multiple events to happen on the same break.

---

## ⚔️ Monster Attacks (Waves)

When a monster event succeeds, enemies spawn instantly around your island.  
The wave strength scales with your **total OneBlock breaks** and **world difficulty**.

### 📈 Wave Scaling
| Stage | Typical Enemies | Notes |
|:------|:----------------|:------|
| Early (0–499) | Zombies, Spiders | Small waves, basic mobs |
| Mid (500–1499) | Skeletons, Creepers, Husks | Mixed danger |
| Late (1500–2999) | Strays, Endermen | Stronger enemies, elites |
| Extreme (3000+) | Larger mixed waves | Frequent elites on Hard |

### 💪 Difficulty Effects
| Difficulty | Behavior |
|:------------|:----------|
| **Easy** | Waves are smaller and less frequent. |
| **Normal** | Standard scaling and balance. |
| **Hard** | Waves are larger, include elites, and occur more often. |
| **Peaceful** | Hostile events disabled entirely. |

### 📍 Spawn Details
- Mobs spawn in a **4-block radius** around your OneBlock.  
- Only **safe air positions** are used; no suffocation or block spawns.  
- Works across **all dimensions** (Overworld, Nether, End).  
- All spawns are announced globally in multiplayer.

---

## 🐾 Friendly Visits

Not every surprise is hostile — sometimes the world sends help.

When a friendly event triggers, the mod rolls between **land** and **aquatic** pools based on your island setup.

### 🐮 Land Spawns (Default)
Includes:
- **Farm animals:** Chicken, Sheep, Cow, Pig, Rabbit, Goat, Horse, Donkey, Mule, Mooshroom  
- **Neutrals:** Wolf, Llama, Bee, Fox, Camel, Panda, Polar Bear  
- **Ambients:** Parrot, Cat, Turtle, Armadillo, Sniffer, Allay  
- **Villager:** Rarely spawns after mid-game progress  
- **Wandering Trader:** Occasionally appears with two leashed llamas

### 🌊 Aquatic Spawns
If a **5×5 pool of water at least 2 blocks deep** surrounds your OneBlock, the friendly pool switches to aquatic creatures:
- Cod, Salmon, Tropical Fish, Pufferfish  
- Axolotl  
- Squid, Glow Squid  
- Frog  
- Dolphin (rare bonus)

> The pool must be **completely water-filled** (no air gaps) to qualify as aquatic.

---

## 🧱 Block Progression

Your total OneBlock breaks determine what blocks can appear.  
Each tier unlocks harder materials, avoiding unsafe or incomplete blocks.

| Total Breaks | Example Blocks |
|:--------------|:---------------|
| 0–500 | Dirt, Wood, Stone, Terracotta |
| 500–1000 | Adds basic ores and deepslate |
| 1000–2000 | Adds Basalt, Blackstone, and harder stones |
| 2000–5000 | Expands to Nether/End blocks |
| 5000–8000 | Adds rare materials |
| 8000–10000+ | Unlocks all blocks up to Obsidian hardness |

### 🚫 Excluded Blocks
Unsafe or unusable blocks are skipped automatically:
- Bedrock, Barrier, Light, Debug Stick  
- Portals, TNT, Ice, Command or Structure blocks  
- Blocks with entities (chests, beacons, spawners)  
- Falling blocks (sand, gravel, concrete powder)  
- Non-full shapes (stairs, slabs, fences, torches, etc.)

---

## 🎁 Random Item Drops

If an item drop event succeeds, a random **safe item** spawns above your OneBlock.

- **Allowed:** Common materials, tools, foods, decorative blocks  
- **Blocked:** Dangerous or game-breaking items  
  - End Portal Frame, Bedrock, Barrier, Light  
  - Command / Structure / Jigsaw blocks  
  - Explosives or boss spawn eggs  
  - Non-full blocks (stairs, slabs, panes, torches, beds, etc.)

> Item drops are more generous on **Easy**, standard on **Normal**, and rarer on **Hard**.

---

## ❌ Restricted & Blacklisted Items

- **End Portal Frames** never appear in drops or block results.  
- **Portals** (End, Nether, Gateway) cannot be generated by the OneBlock.  
- **Boss spawn eggs** and unsafe blocks are filtered out entirely.  
- **Cheaty items** require datapacks or mod extensions to be introduced manually.

---

## 💾 Persistence & Safety

- If you die without a spawn point, you’ll **respawn safely** above your OneBlock.  
- All progress — block breaks, events, and state — is **automatically saved** between sessions.  
- The mod guarantees the OneBlock always regenerates correctly, even after crashes or force stops.

---

## 📊 TL;DR — Chance Summary

| Event | Base Chance | Easy | Normal | Hard | Peaceful |
|:------|:-------------|:------|:--------|:-------|:-----------|
| 💀 Hostile Wave | 1 in 100 | 1 in 200 | 1 in 100 | 1 in 50 | ❌ |
| 🐑 Friendly Visit | 1 in 120 | 1 in 60 | 1 in 120 | 1 in 240 | ✅ |
| 🪙 Item Drop | 1 in 100 | 1 in 50 | 1 in 100 | 1 in 200 | ✅ |
| 🧱 Block Regeneration | Always | Always | Always | Always | Always |

- **Easy:** Safer world, more friendly and item events.  
- **Normal:** Balanced survival experience.  
- **Hard:** More frequent events, more danger, larger waves.  
- **Peaceful:** Only friendly and item events can occur.

---

> “It all begins on one small island — what you build from there is entirely up to you.”
