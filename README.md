https://modrinth.com/mod/theoneblock

# 🧱 OneBlock — Player Guide (What happens & exact chances)

Welcome to **OneBlock**: you start on a tiny island in the void with a single block that keeps coming back. Every time you break it, something can happen — a new block appears, a mob wave might be scheduled, and (if enabled by the pack) you may get random item drops.

This page explains **everything that can happen** and the **exact chances / rules** that are defined in the current build.

---

## 🏝️ Your Start

- A **small circular island** (radius **5**), **2 blocks thick**:
  - Y=62 (bottom), Y=63 (top surface), your OneBlock sits at **Y=64**.
- The island & OneBlock **match the dimension**:
  - **Overworld**: grass (top), dirt (under); OneBlock is **grass block**.
  - **Nether**: **netherrack** (both layers & OneBlock).
  - **The End**: **end stone** (both layers & OneBlock).
- Your **spawn point** is set just **above** the OneBlock.

> Tip: Terrain is otherwise **void**; only this island appears (structures can be globally enabled/disabled).

---

## 🔁 What happens on each break?

Each time you break the OneBlock:

1) **A new block appears** (the default outcome).
   - The block is chosen **fairly** from safe, full-cube blocks.
   - As you progress, **harder blocks** become available more often (see “Block progression”).

2) **A mob wave may be scheduled** (**1% chance per break**, but only if no wave is already scheduled).
   - If scheduled, the game starts a **countdown** in “breaks remaining”.
   - When the countdown hits **0**, a wave **spawns instantly** (see “Mob waves” below).

3) **(If enabled) a random item may drop** on/near the OneBlock.
   - Items come from a **safe list** (see “Random items”).
   - **End Portal Frames are never dropped** by this system.

> The only **hard-coded** chance in this build is the **1-in-100 (1%)** chance to **schedule** a mob wave on a break when no wave is currently pending.

---

## ⚔️ Mob waves (chances, size, countdown, and mob mix)

### Scheduling chance
- **Per break:** **1%** chance to **schedule** a wave **if no wave is already scheduled**.

### Countdown (how many more breaks until it spawns)
| Your total OneBlock breaks | Breaks till the wave spawns |
|---:|---:|
| 0–499 | **12** |
| 500–1499 | **10** |
| 1500–2999 | **8** |
| 3000–4999 | **6** |
| 5000–7999 | **5** |
| 8000–11999 | **4** |
| 12000+ | **3** |

> You’ll see a chat message like:  
> **“⚠ Monsters will appear after X more block breaks…”**  
> When the countdown reaches 0:  
> **“A wave of N enemies has appeared!”**

### Wave size (how many mobs)
- **0–99 breaks:** always **1** mob.  
- **100+ breaks:** the size is **uniform random** from **1** up to **(floor(breaks / 100) + 1)**, capped at **100**.
  - Examples:
    - 150 breaks → **1–2** mobs
    - 250 breaks → **1–3** mobs
    - 900 breaks → **1–10** mobs
    - 5,000+ breaks → scales up but never exceeds **100**

### What can spawn (and how the odds shift)
The wave chooses each mob **uniformly from a “pool”** that grows with progress.  
Some entries are **duplicated** later to **increase their odds**.

| Total breaks | Mob pool (entries) |
|---:|---|
| 0–499 | Zombie, Spider |
| 500–1499 | Zombie, Spider, Skeleton |
| 1500–2999 | + Creeper, Husk, Stray |
| 3000–4999 | + Enderman |
| 5000–7999 | + **Creeper again** (Creeper appears **twice** → higher chance) |
| 8000+ | + **Enderman again** (Enderman appears **twice** → higher chance) |

> Spawning radius is about **4 blocks** around the OneBlock (tries nearby air spots, falls back to center if needed).

After a wave spawns, the system resets so future breaks can **again** schedule a wave at **1%**.

---

## 🧱 Block progression (what blocks you get as you break)

Blocks are chosen from **safe**, **full-cube**, **non-gravity**, **non-technical** blocks — no TNT, no portals, no beds/chests (no block entities), no falling sand/gravel, etc.

Your **total breaks** control how “hard” a block can be:

- We track a **progress** value:  
  `progress = min(totalBreaks / 10,000, 1.0)`
- This drives a **hardness cap** that rises smoothly from easy to very hard:  
  `maxHardness = 0.5 + 49.5 * progress`

**Milestones (hardness cap examples):**
- **0 breaks:** cap ≈ **0.5** → early soft blocks (dirt/log-like hardness)
- **1,000 breaks:** cap ≈ **5.45**
- **5,000 breaks:** cap ≈ **25.25**
- **10,000+ breaks:** cap = **50** → even **obsidian-level** hardness now allowed

**How a block is picked (simplified):**
1) Try up to **128 times** to find a random block **≤ current hardness cap** (this dominates).
2) If that fails, try up to **128** more with a **probability gate** that favors harder blocks late-game.
3) Fallback: pick the **softest** of **64** random samples (guarantees a result).

**Never selected as break-result blocks (examples):**
- **Bedrock, Barrier, Light**, command/structure/jigsaw blocks  
- **End Portal**, **Nether Portal**, **End Gateway**, **Ice**, **TNT**  
- Any **falling** blocks (sand, gravel, concrete powder)  
- Any blocks with **block entities** (e.g., chests, beacons, spawners)  
- **Partial shapes** (stairs, fences, walls, torches, carpets, etc.) are excluded by the “full-cube” rule

---

## 🎁 Random items (if enabled by the pack)

When an item drop is chosen, the mod spawns a **random allowed item** on/above the OneBlock:

- **Allowed**: most normal items (foods, tools, many block items that are safe).
- **Never dropped**:
  - **End Portal Frame**, **Bedrock**, **Barrier**, **Light**, command/structure/jigsaw blocks, **Debug Stick**.
  - **Dangerous spawn eggs** for **Ender Dragon**, **Wither**, **Warden** are blocked.
- Some awkward/partial blocks (slabs, stairs, fences, panes, carpets, torches, beds, candles, anvils, etc.) are **excluded** from the item pool to keep things playable on a one-block island.

> **Important:** In the current build, the **exact per-break chance** of an item drop is **not fixed here** (it’s decided by the break handler logic). Items, when they do appear, **will never** include End Portal Frames.

---

## ❌ About End Portal Frames (and other “cheaty” items)

- **End Portal Frames do not drop** from the random items system (they are explicitly blacklisted).
- **End Portal / End Gateway / Nether Portal blocks** are **never** picked as break results.
- If you want End Portal Frames to be obtainable, that would need a **separate custom event** not present in this build.

---

## 💾 Quality-of-life

- If you **die** without a personal spawn (bed/anchor), you respawn **at the OneBlock**, made safe (no fire, fall reset).
- Waves and progress are stored so your **run persists** across sessions.

---

## 📌 TL;DR (chances & rules you can rely on)

- **Mob wave scheduling:** **1% per break** (only when no wave is pending).  
- **Wave countdown:** **12 → 10 → 8 → 6 → 5 → 4 → 3** breaks as you pass **0/500/1500/3000/5000/8000/12000** total breaks.  
- **Wave size:** from **1** up to **(floor(totalBreaks/100) + 1)**; never above **100**.  
- **Mob mix:** Zombies/Spiders early → add Skeletons → then Creeper/Husk/Stray → then Enderman;  
  **Creeper** weighted up at **5000+**, **Enderman** weighted up at **8000+**.  
- **Block results:** smooth difficulty curve; more **hard blocks** unlock as you approach **10,000** breaks.  
- **Item drops:** allowed & safe items only; **no End Portal Frames**.

Good luck — and watch that countdown! 👀
