# Snake Game – COMP2042 Coursework

## GitHub
https://github.com/tanayhsh/COMP2042_Coursework

## Compilation Instructions
(fill in later)

## Implemented and Working Properly
(none so far)

## Implemented but Not Working Properly
(none so far)

## Features Not Implemented
(none so far)

## New Java Classes
| Class | Location | Purpose |
|---|---|---|
| (none yet) | | |

## Modified Java Classes

### Summary of changes
| Class | Changes | Reason |
|---|---|---|
| GameController | Fixed duplicate `ran == 1` in `newCoin()`; rewrote `valKey()`; removed `calculateBonusScore()`, `legacyScoreMultiplier`, `coinSoundPath`, `unusedObstacleCount` and unused imports; updated `drawGrid` calls | Bug fixes (see below); dead code misled readers; follow `GameView` refactor |
| Obstacle | Spawn range changed to `0..x-1`; added duplicate-position check | Bug fixes (see below) |
| Snake | Removed unused `headColor`/`bodyColor` and getters; renamed `getDead()` to `isDead()` | Snake is drawn with images, so the colours were dead code; Java boolean getter convention |
| Game | Removed duplicate `this.powerUp` assignment; renamed `multiplier` to `cellSize` | Redundant line; the value is the pixel size of one grid cell, not a score multiplier |
| GameView | Merged two duplicate `drawGrid` methods; removed unused `gc`, `a`, `b` parameters; `makeScene()` now takes an `Obstacle` | DRY, so rendering logic lives in one place; simpler method signature |
| MainMenuController | Passes `obstacle` to `makeScene()` | Follows the `GameView` refactor |
| ViewManager | Renamed `initalizeMainMenu()` to `initializeMainMenu()` | Spelling typo |
| All classes | Renamed `snek` to `snake` | Clear, descriptive naming |

### Bugs fixed
| Bug | Location | Cause | Fix |
|---|---|---|---|
| RainbowCoin never spawned | `GameController.newCoin()` | `ran == 1` was checked twice, so the second branch was unreachable | Changed the second check to `ran == 2` |
| Snake could reverse from UP to DOWN | `GameController.valKey()` | DOWN check compared against `DOWN` instead of `UP` | Rewrote so only 90° turns are accepted |
| Obstacles off-grid / never in column 0 | `Obstacle.newObstacles()` | `rnd.nextInt(x) + 1` produced range `1..x` | Changed to `rnd.nextInt(x)` |
| Obstacles could stack on one cell | `Obstacle.newObstacles()` | No check against existing obstacles, which made `maxScore` inaccurate | Added `obstacles.contains(p)` check |

### Refactoring rationale
- **Dead code removal:** unused methods, fields and imports deleted to reduce confusion and maintenance cost.
- **DRY:** duplicate rendering methods merged so drawing logic lives in one place.
- **Naming:** unclear or misspelt identifiers renamed so the code is self-explanatory.
- Each refactor was checked by running the game to ensure the behavior remained unchanged.