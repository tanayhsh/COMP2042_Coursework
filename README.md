# Snake Game – COMP2042 Coursework

## GitHub
https://github.com/tanayhsh/COMP2042_Coursework

## Compilation Instructions
(to be added later)

## Implemented and Working Properly
- `+` / `=` / numpad `+`: enter fullscreen
- `-` / numpad `-`: exit fullscreen
- `p` : pause game
## Implemented but Not Working Properly
- **Obstacle safe zone and wrapping:** the safe zone uses grid distance, so it doesn't account for border wrapping. An obstacle can still appear just across a border when the snake is about to wrap.

## Features Not Implemented
(none so far)

## New Java Classes
| Class | Location | Purpose |
|---|---|---|
| EntityManager | `com.comp2042.view.EntityManager` | Loads and holds all snake and coin images so model classes contain no JavaFX code |
| CoinType | `com.comp2042.model.CoinType` | Enum identifying each coin kind so the view can choose the matching image |

## Modified Java Classes

### Summary of changes
| Class | Changes | Reason |
|---|---|---|
| GameController | Fixed duplicate `ran == 1` in `newCoin()`; rewrote `valKey()` | Bug fixes (see below) |
| Obstacle | Spawn range changed to `0..x-1`; added duplicate-position check | Bug fixes (see below) |
| Snake | Added obstacle check after every move in `move()` | Bug fix (see below) |
| GameController | Removed `calculateBonusScore()`, `legacyScoreMultiplier`, `coinSoundPath`, `unusedObstacleCount`; updated `drawGrid` calls | Dead code that was never executed; follows `GameView` refactor |
| Snake | Removed unused `headColor`/`bodyColor`; renamed `getDead()` to `isDead()` | Snake is drawn with images, so colours were dead code|
| Game | Removed duplicate `powerUp` assignment; renamed `multiplier` to `cellSize` | Redundant line; value is the pixel size of one grid cell |
| GameView | Merged duplicate `drawGrid` methods, removed unused `gc`, `a`, `b` params; `makeScene()` takes an `Obstacle` | DRY: rendering logic in one place |
| MainMenuController | Passes `obstacle` to `makeScene()` | Follows `GameView` refactor |
| ViewManager | Renamed `initalizeMainMenu()` to `initializeMainMenu()` | Spelling typo |
| All classes | Renamed `snek` to `snake` | Clear, descriptive naming |
| Snake | Removed all `Image`/`ImageView` fields and getters | makes Snake unit testable without JavaFX |
| Coin, GoldCoin, BlueCoin, RainbowCoin, SilverCoin | Removed `Image` fields and `getImage()`; added `getType()` returning a `CoinType` | each image now loads once instead of on every coin spawn |
| GameView | Uses `EntityManager` for all images | Rendering code now owns the visuals |

### Bugs fixed
| Bug | Location | Cause | Fix |
|---|---|---|---|
| RainbowCoin never spawned | `GameController.newCoin()` | `ran == 1` was checked twice, so the second branch was unreachable | Changed the second check to `ran == 2` |
| Snake could reverse from UP to DOWN | `GameController.valKey()` | DOWN check compared against `DOWN` instead of `UP` | Rewrote so only 90° turns are accepted |
| Obstacles off-grid / never in column 0 | `Obstacle.newObstacles()` | `rnd.nextInt(x) + 1` produced range `1..x` | Changed to `rnd.nextInt(x)` |
| Obstacles could stack on one cell | `Obstacle.newObstacles()` | No check against existing obstacles, which made `maxScore` inaccurate | Added `obstacles.contains(p)` check |
| Snake survived wrapping onto an obstacle | `Snake.move()` | Obstacle check ran before the border wrap, so the wrapped position was never checked | Added an obstacle check after every move |
| Obstacles could spawn directly in front of the snake | `Obstacle.newObstacles()` | Only the head cell itself was excluded | Added a safe zone: no obstacles within 3 moves of the head |
| maxScore off by one per obstacle | `Game.decMaxScore()` | Subtracted `num - 1` instead of `num`; could make winning impossible and freeze coin spawning | Subtract exactly one per obstacle |
| maxScore not restored on reset | `Game.resetScore()` | Only score and points were reset | Store `initialMaxScore` and restore it |
| Body segment left under head on death | `Snake.move()` | Head position added to body before death check | Remove the extra segment when the head is moved back |
| Turn sprites left over after reset | `GameView.turnArray` | Array never cleared | Added `resetTurns()`, called from `reset()` |
| ESC left the game loop running and kept the old direction | `GameController.handle()` | Timeline not stopped; static `key`/`lastKey` not reset | Stop the animation and reset direction on ESC |
| Snake drawn too large in fullscreen | `GameView.drawHead()/drawBody()` | `fullScale` applied twice (`cellSize * fullScale * fullScale`) | Apply it once |
| `+` key rarely triggered fullscreen | `GameController.handle()` | `KeyCode.PLUS` only fires on some keyboards | Also accept `EQUALS`, `ADD`, and `SUBTRACT` for minus |
| Fullscreen hint said "Press ESC", which also returns to menu | `ViewManager.initGameStage()` | JavaFX default exit key clashed with the game's ESC | Hint now says "Press -"|
| Final frame not drawn on wall/obstacle death | `GameController` timeline | Redraw skipped unless the snake hit itself | Always redraw |
| Confusing scaling variable names | `GameView.scaleToFullscreen()` | `xScaling` used the screen height | Renamed to `heightScale`/`widthScale`, used `Math.min` |

## Unexpected Problems
(none so far)

### Package structure
| Package | Responsibility | Classes |
|---|---|---|
| `com.comp2042` | Entry point | Main |
| `com.comp2042.model` | Game logic and state, no JavaFX rendering | Snake, Game, Obstacle, CoinType |
| `com.comp2042.model.coin` | Coin types and their points | Coin, GoldCoin, BlueCoin, RainbowCoin, SilverCoin |
| `com.comp2042.view` | Drawing, images and windows | GameView, SpriteManager, ViewManager |
| `com.comp2042.controller` | Input handling and game loop | GameController, MainMenuController |