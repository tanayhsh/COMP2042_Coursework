# Snake Game – COMP2042 Coursework

## GitHub
https://github.com/tanayhsh/COMP2042_Coursework

## Compilation Instructions
(to be added later)

## Implemented and Working Properly
(to be added later)

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
| GameController | Fixed duplicate `ran == 1` in `newCoin()`; rewrote `valKey()` | Bug fixes (see below) |
| Obstacle | Spawn range changed to `0..x-1`; added duplicate-position check | Bug fixes (see below) |
| Snake | Added obstacle check after every move in `move()` | Bug fix (see below) |

### Bugs fixed
| Bug | Location | Cause | Fix |
|---|---|---|---|
| RainbowCoin never spawned | `GameController.newCoin()` | `ran == 1` was checked twice, so the second branch was unreachable | Changed the second check to `ran == 2` |
| Snake could reverse from UP to DOWN | `GameController.valKey()` | DOWN check compared against `DOWN` instead of `UP` | Rewrote so only 90° turns are accepted |
| Obstacles off-grid / never in column 0 | `Obstacle.newObstacles()` | `rnd.nextInt(x) + 1` produced range `1..x` | Changed to `rnd.nextInt(x)` |
| Obstacles could stack on one cell | `Obstacle.newObstacles()` | No check against existing obstacles, which made `maxScore` inaccurate | Added `obstacles.contains(p)` check |
| Snake survived wrapping onto an obstacle | `Snake.move()` | Obstacle check ran before the border wrap, so the wrapped position was never checked | Added an obstacle check after every move |

## Unexpected Problems
(none so far)