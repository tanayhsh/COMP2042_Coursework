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
| Direction | `com.comp2042.model.Direction` | Enum for the four movement directions. Each value stores its x/y offset and the entity rotation angle, and `isOpposite()` detects reversals. Replaces the `'U'/'D'/'L'/'R'` chars and hard-coded degree values |
| Difficulty | `com.comp2042.model.Difficulty` |  Enum for EASY, NORMAL and HARD. Each value stores its game speed and whether borders wrap. Replaces the `'E'/'N'/'H'` chars |

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
| Snake | `move()` now takes a `Direction`; four near-identical `switch` cases (~50 lines) collapsed into one path; `borderControl()` renamed `isOutOfBounds()`; wrapping handled by a new `wrap()` method using `Math.floorMod` | DRY: movement logic exists once, so fixes apply to every direction; clearer names |
| GameController | Four-way `if/else` on the key replaced by `snake.move(toDirection(key))` with a small `toDirection()` helper | Removes duplication; converts input to the model's own type at one point |
| Game | Constructor takes a `Difficulty` instead of a `char`; the `if / else if` difficulty block replaced by reading speed and border rules from the enum | Type safety: invalid difficulties can't compile; settings live with the difficulty they belong to |
| MainMenuController | `DIFFICULTY` constant is now `Difficulty.NORMAL` for exapmle| Follows the `Difficulty` refactor |
| SnakeTest, GameTest | Updated to use `Direction` and `Difficulty`; added wrap-in-all-directions and difficulty tests | Tests modified to match new behaviour |

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
| Unknown difficulty left the game frozen | `Game` constructor | Difficulty was a `char`; any value other than `'E'`, `'N'` or `'H'` matched no branch, leaving `speed` at 0 so the snake never moved | Replaced with the `Difficulty` enum, so only valid difficulties can be passed in, and the compiler enforces it |

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

### Testing
| Test class | What it covers |
|---|---|
| SnakeTest | Starting position, movement, wall death, wrapping (all directions), obstacle death, wrap-onto-obstacle, no duplicate segment on death |
| ObstacleTest | Obstacles stay inside the grid, never stack, respect the safe zone around the head, reset clears them |
| GameTest | Coins needed win calculation, one deducted per obstacle, reset restores it, win detection, difficulty speed and border settings |
| DirectionTest | Opposite directions detected, perpendicular and same directions are not opposite, each direction moves exactly one cell |

All tests are run with `mvn test` or from IntelliJ (right-click `src/test/java` → Run 'All Tests').