## LWJGL3 Launcher

The LWJGL3 Launcher is responsible for launching the desktop application using the LWJGL3 backend. It configures the application settings such as title, VSync, window mode, and fullscreen mode.

### Main Class

- **Lwjgl3Launcher**: This class contains the main method to start the application.

### Methods

- **main(String[] args)**: The entry point of the application.
- **createApplication()**: Creates a new instance of the application with the default configuration.
- **getDefaultConfiguration()**: Sets up the default configuration for the application, including title, VSync, window mode, fullscreen mode, and window icons.

## Main Class

The Main class is the entry point of the game application. It extends the Game class from libGDX and sets the initial screen.

### Main Class

- **Main**: This class extends Game and sets the initial screen.

### Methods

- **create()**: Called when the application is first created. It loads assets and sets the initial screen to StartScreen.

## FirstScreen Class

The FirstScreen class implements the Screen interface and handles the main game screen, including rendering, updating game elements, and handling user input.

### Main Class

- **FirstScreen**: This class implements the Screen interface and manages the main game screen.

### Methods

- **show()**: Initializes the screen elements and sets up game elements.
- **initialize()**: Initializes the screen elements.
- **setupGameElements()**: Sets up the game elements such as map, player, enemies, and weapon manager.
- **restartGame()**: Restarts the game by reinitializing game elements and resetting the game state.
- **openSettings()**: Opens the settings menu.
- **render(float delta)**: Renders the game elements.
- **renderGameElements()**: Renders the game elements.
- **updateScore()**: Updates the score display.
- **updateXp()**: Updates the XP bar display.
- **handlePause()**: Handles the pause functionality.
- **renderPauseMenu()**: Renders the pause menu.
- **updateGameElements(float delta)**: Updates the game elements.
- **updateRoundInfo()**: Updates the round information display.
- **renderCustomCursor()**: Renders the custom cursor.
- **updateKillCount()**: Updates the kill count display.
- **updateRemainingEnemies()**: Updates the remaining enemies count display.
- **resize(int width, int height)**: Resizes the screen.
- **pause()**: Pauses the screen.
- **resume()**: Resumes the screen.
- **hide()**: Hides the screen.
- **dispose()**: Disposes of the screen resources.

## Character Class

The Character class is an abstract class that represents a character in the game. It handles common character properties and behaviors such as health, position, animation, and rendering.

### Main Class

- **Character**: This abstract class represents a character in the game.

### Methods

- **Character(int walkFrameCols, float x, float y, int hp, float speed, Texture walkSheet)**: Constructor for the Character class.
- **sheetSplitter(Texture texture, int frameCols, float frameDuration)**: Splits the sprite sheet into individual frames for the walk animation.
- **updateAnimation(float deltaTime)**: Updates the animation state time.
- **isAnimationFinished()**: Checks if the walk animation is finished.
- **render(SpriteBatch batch)**: Renders the character and its health bar.
- **drawHealthBar(SpriteBatch batch)**: Draws the health bar above the character.
- **collisionBorder(float mapWidth, float mapHeight)**: Handles collision with the map borders.
- **update(float delta)**: Updates the character's state.
- **getHp()**: Gets the current health points of the character.
- **setHp(int hp)**: Sets the health points of the character.
- **getMaxHp()**: Gets the maximum health points of the character.
- **setMaxHp(int maxHp)**: Sets the maximum health points of the character.
- **takeDamage(int damage)**: Reduces the character's health points by the specified damage amount.
- **heal(int amount)**: Increases the character's health points by the specified amount.
- **isDead()**: Checks if the character is dead.
- **attack()**: Abstract method for character attack behavior.
- **die()**: Abstract method for character death behavior.
- **getX()**: Gets the x position of the character.
- **setX(float x)**: Sets the x position of the character.
- **getY()**: Gets the y position of the character.
- **setY(float y)**: Sets the y position of the character.
- **setPosition(float x, float y)**: Sets the position of the character.
- **getSpeed()**: Gets the movement speed of the character.
- **setSpeed(float speed)**: Sets the movement speed of the character.
- **isWalking()**: Checks if the character is walking.
- **setWalking(boolean walking)**: Sets the walking state of the character.
- **getStateTime()**: Gets the current state time of the animation.
- **setStateTime(float stateTime)**: Sets the state time of the animation.
- **getScale()**: Gets the scale of the character.
- **setScale(float scale)**: Sets the scale of the character.
- **getWalkAnimation()**: Gets the walk animation of the character.
- **setWalkAnimation(Animation<TextureRegion> walkAnimation)**: Sets the walk animation of the character.
- **getWalkFrameCols()**: Gets the number of columns in the walk animation sprite sheet.
- **setWalkFrameCols(int walkFrameCols)**: Sets the number of columns in the walk animation sprite sheet.
- **getWalkSprites()**: Gets the walk sprites of the character.
- **dispose()**: Disposes of the character's resources.

## Settings Class

The Settings class contains various constants and configurations for the game.

### Main Class

- **Settings**: This class contains constants and configurations for the game.

### Constants

- **SPAWN_INTERVAL**: Interval between spawns.
- **SPAWN_DISTANCE_THRESHOLD**: Distance threshold for spawning.
- **INITIAL_SPAWN_INTERVAL**: Initial spawn interval.
- **ROUND_INTERVAL**: Interval between rounds.
- **DASH_DURATION**: Duration of the dash.
- **DASH_MULTIPLIER**: Multiplier for dash speed.
- **MELEE_DAMAGE**: Damage dealt by melee attacks.
- **MELEE_COOLDOWN**: Cooldown time for melee attacks.
- **KNOCKBACK_DISTANCE**: Distance for knockback.
- **MIN_CURSOR_DISTANCE**: Minimum distance for the cursor.
- **LEVEL_STAT**: Level stat.
- **BASE_HP**: Base health points.
- **BASE_SPEED**: Base movement speed.
- **ATTACK_STATE_TIME**: State time for attacks.
- **DEATH_ANIMATION_FRAME_DURATION**: Frame duration for death animation.
- **HURT_SPEED_MULT**: Speed multiplier when hurt.
- **KNOCKBACK_DURATION**: Duration of knockback.
- **TILE_WIDTH**: Width of a tile.
- **TILE_HEIGHT**: Height of a tile.
- **MAP_WIDTH_IN_TILES**: Width of the map in tiles.
- **MAP_HEIGHT_IN_TILES**: Height of the map in tiles.
- **MAP_PATH**: Path to the map file.
- **UNIT_SCALE**: Unit scale for the map.
- **BACKGROUND_MUSIC_PATH**: Path to the background music file.
- **FONT_SCALE**: Scale of the font.
- **XP_BAR_WIDTH**: Width of the XP bar.
- **XP_BAR_HEIGHT**: Height of the XP bar.
- **XP_BAR_X**: X position of the XP bar.
- **XP_BAR_Y**: Y position of the XP bar.
- **LEVEL_TEXT_OFFSET_X**: X offset for the level text.
- **LEVEL_TEXT_OFFSET_Y**: Y offset for the level text.
- **SCORE_TEXT_X**: X position of the score text.
- **SCORE_TEXT_Y**: Y position of the score text.

### Maps

- **ENEMY_STATS**: Map containing enemy stats.
- **WEAPON_STATS**: Map containing weapon stats.

## AssetLoader Class

The AssetLoader class is responsible for loading and managing game assets such as textures.

### Main Class

- **AssetLoader**: This class loads and manages game assets.

### Methods

- **load()**: Loads all game assets.
- **loadTexture(String key, String path)**: Loads a texture and stores it in the textures map.
- **loadTextures()**: Loads all textures used in the game.
- **getTexture(String name)**: Retrieves a texture by its name.
- **dispose()**: Disposes of all loaded textures.
