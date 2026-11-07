# Snake Game – COMP2042 Coursework

## GitHub
https://github.com/tanayhsh/COMP2042_Coursework

## Compilation Instructions
(fill in later)

## Implemented and Working Properly
(fill in as you add features)

## Implemented but Not Working Properly

## Features Not Implemented

## New Java Classes
| Class | Location | Purpose |
|---|---|---|

## Modified Java Classes
| Class | Change | Reason |
|---|---|---|
| GameController | Fixed duplicate `ran == 1` in `newCoin()` | RainbowCoin could never spawn |
| Obstacle | Spawn range `0..x-1`, duplicate check | Obstacles appeared off-grid or stacked |
| GameController | Rewrote `valKey()` | Snake could reverse UP→DOWN into itself |
| GameController | Removed unused bonus score code, sound path, obstacle count | Dead code |
| Snake | Removed unused colour fields | Dead code |
| GameView | Merged duplicate `drawGrid`, removed unused params | Duplication |

## Bug Fixes
| Bug | Location | Fix |
|---|---|---|

## Unexpected Problems