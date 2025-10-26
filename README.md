# 🧱 OneBlock — Player Guide (Event Rules & Exact Chances)

Welcome to **OneBlock**: your endless regenerating island in the void.  
Each block break has a chance to cause something special — whether it’s a new block, a monster attack, or a friendly visit.  
This guide explains exactly what can happen, when, and how the **difficulty setting** influences your odds.

---

## 🏝️ Your Start

- You begin on a **small circular island**, roughly **5 blocks in radius** and **2 blocks thick**:  
  Bottom layer at **Y=62**, top at **Y=63**, and your OneBlock sits at **Y=64**.  
- The island’s materials match its dimension:  
  - **Overworld:** Grass and dirt layers.  
  - **Nether:** Netherrack.  
  - **The End:** End Stone.  
- You always spawn safely above your OneBlock.  
- Everything beyond is **void** — this is your whole world.

---

## 🔁 What Happens on Each Break

Each time you break the OneBlock, the game rolls several independent chances to determine what happens.

| Event | Description | Base Chance | Easy | Normal | Hard | Peaceful |
|:------|:-------------|:------------|:------|:--------|:-------|:----------|
| 🧱 **Block Regeneration** | A new random block appears from the progression pool. | Always | — | — | — | ✅ Always |
| 💀 **Monster Attack** | Spawns an instant wave of enemies around your OneBlock. | 1 in 100 | 1 in 200 *(half as likely)* | 1 in 100 | 1 in **33** *(≈3× more likely)* | ❌ Disabled |
| 🐑 **Friendly Visit** | Spawns peaceful or neutral mobs — animals, villagers, traders. | 1 in 100 | 1 in 50 *(twice as likely)* | 1 in 100 | 1 in 300 *(three times less likely)* | ✅ Allowed |
| 🪙 **Random Item Drop** | Drops a random safe item above your OneBlock. | 1 in 100 | 1 in 50 *(twice as likely)* | 1 in 100 | 1 in 300 *(three times less likely)* | ✅ Allowed |

> These chances are **rolled independently** every time you break the OneBlock.  
> Multiple outcomes can occur on the same break — though that’s rare!

---

## ⚔️ Monster Attacks (Waves)

When a hostile event succeeds, enemies spawn instantly around your island.  
The number and type of enemies scale with your total OneBlock breaks and difficulty.

### 📈 Wave Scaling
| Stage | Typical Enemies | Notes |
|:------|:----------------|:------|
| Early (0–499) | Zombies, Spiders | Small waves, basic mobs |
| Mid (500–1499) | Skeletons, Creepers, Husks | Varied midgame fights |
| Late (1500–2999) | Strays, Endermen | Stronger enemies, elites |
| Extreme (3000+) | Larger mixed waves | Frequent elites on Hard |

### 💪 Difficulty Effects
| Difficulty | Behavior |
|:------------|:----------|
| **Easy** | Smaller, less frequent waves. |
| **Normal** | Balanced size and rate. |
| **Hard** | Larger, tougher, and significantly more frequent waves. |
| **Peaceful** | Hostile waves are disabled entirely. |

### 📍 Spawn Behavior
- Mobs spawn within a **4-block radius** around your OneBlock.  
- Only valid air positions are used (no suffocation).  
- Works in **all dimensions** (Overworld, Nether, End).  
- In multiplayer, wave messages are broadcast to all players.

---

## 🐾 Friendly Visits

Sometimes instead of chaos, the OneBlock sends help — or company.

### 🐮 Land Spawns (Default)
The friendly pool includes:
- **Farm animals:** Chicken, Sheep, Cow, Pig, Rabbit, Goat, Horse, Donkey, Mule, Mooshroom  
- **Neutrals:** Wolf, Llama, Bee, Fox, Camel, Panda, Polar Bear  
- **Ambients:** Parrot, Cat, Turtle, Armadillo, Sniffer, Allay  
- **Villager:** Rarely spawns after mid-game  
- **Wandering Trader:** Occasionally appears with two leashed llamas

### 🌊 Aquatic Spawns
If you have a **5×5 pool of water that’s two blocks deep** around your OneBlock, friendly events switch to aquatic spawns:
- Cod, Salmon, Tropical Fish, Pufferfish  
- Axolotl  
- Squid, Glow Squid  
- Frog  
- Dolphin *(rare bonus)*

> The water must fully fill the 5×5 area — both layers need to be water blocks for the pool to count.

---

## 🧱 Block Progression

The OneBlock evolves as you play.  
Your total break count determines which blocks can appear, moving from soft materials to rare and hard ones.

| Total Breaks | Example Materials |
|:--------------|:----------------|
| 0–500 | Dirt, Logs, Stone, Terracotta |
| 500–1000 | Adds ores and deepslate |
| 1000–2000 | Adds Basalt, Blackstone |
| 2000–5000 | Expands to Nether & End materials |
| 5000–8000 | Introduces rare blocks |
| 8000–10000+ | Includes all blocks up to Obsidian hardness |

### 🚫 Excluded Blocks
Unsafe or unstable blocks never appear:
- Bedrock, Barrier, Light, Debug Stick  
- Portals, TNT, Ice, Command or Structure blocks  
- Blocks with entities (chests, beacons, spawners)  
- Falling blocks (sand, gravel, concrete powder)  
- Non-full shapes (stairs, slabs, fences, torches, etc.)

---

## 🎁 Random Item Drops

Item events spawn **safe, valid items** directly above your OneBlock.

**Allowed:**  
Tools, food, and most non-hazardous items.  

**Blocked:**  
- End Portal Frame, Bedrock, Barrier, Light  
- Command / Structure / Jigsaw blocks  
- Explosives, boss spawn eggs  
- Non-full blocks (stairs, slabs, torches, panes, beds, etc.)

> Friendly and item events happen **twice as often** on *Easy*,  
> at the **base rate** on *Normal*,  
> and are **three times less likely** on *Hard*.

---

## ❌ Restricted & Blacklisted Items

- **End Portal Frames** are never dropped or generated.  
- **Portals** (End, Nether, Gateway) never appear as block results.  
- **Boss spawn eggs** and unsafe items are permanently filtered out.  
- “Cheaty” items must be added manually via datapacks or commands.

---

## 💾 Persistence & Safety

- If you die without a bed or anchor, you respawn safely above your OneBlock.  
- World state (break count, waves, events) saves automatically between sessions.  
- The mod guarantees regeneration even after crashes or forced shutdowns.

---

## 📊 TL;DR — Event Chances

| Event | Base | Easy | Normal | Hard | Peaceful |
|:------|:------|:------|:--------|:-------|:-----------|
| 💀 Hostile Wave | 1 in 100 | 1 in 200 *(half as likely)* | 1 in 100 | 1 in **33** *(3× more likely)* | ❌ |
| 🐑 Friendly Visit | 1 in 100 | 1 in 50 *(2× more likely)* | 1 in 100 | 1 in 300 *(3× less likely)* | ✅ |
| 🪙 Item Drop | 1 in 100 | 1 in 50 *(2× more likely)* | 1 in 100 | 1 in 300 *(3× less likely)* | ✅ |
| 🧱 Block Regeneration | Always | Always | Always | Always | Always |

**Difficulty summary:**  
- 🟢 **Easy** – safer, more friendlies and loot.  
- ⚪ **Normal** – balanced and consistent.  
- 🔴 **Hard** – more danger, more frequent monster waves.  
- ⚫ **Peaceful** – only friendly or item events occur.

---

> “It all begins on one small island — what you build from there is entirely up to you.”
