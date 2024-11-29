package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.menu.PauseMenu;
import io.github.some_example_name.weapon.spawn_weapons.WeaponManager;
import io.github.some_example_name.menu.settings.BrightnessManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.character.Enemy;
import java.util.List;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

/**
 * FirstScreen class that implements the Screen interface.
 * This class handles the main game screen, including rendering, updating game elements, and handling user input.
 */
public class FirstScreen implements Screen {
    private SpriteBatch batch;
    private Player player;
    private static float borderThickness;
    private static Spawn spawnManager;
    private CameraManager cameraManager;
    private PauseMenu pauseMenu;
    private boolean isPaused;
    private static boolean gameOver = false;
    private List<Enemy> enemies;
    private MapManager mapManager;
    private Music backgroundMusic;
    private BitmapFont font;
    private WeaponManager weaponManager;
    private GameOverScreen gameOverScreen;

    @Override
    public void show() {
        initialize();
        AssetLoader.load();
        setupGameElements();
        gameOverScreen = new GameOverScreen(batch, font, this);
    }

    /**
     * Initializes the screen elements.
     */
    private void initialize() {
        batch = new SpriteBatch();
        pauseMenu = new PauseMenu(() -> isPaused = false, this::restartGame, this::openSettings, Gdx.app::exit);
        Gdx.input.setCursorCatched(true);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(Settings.BACKGROUND_MUSIC_PATH));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        font = new BitmapFont();
        font.getData().setScale(Settings.FONT_SCALE);

        // Set the callback for resuming music
        pauseMenu.getSettingsMenu().setOnResolutionChange(() -> {
            if (!isPaused) {
                backgroundMusic.play();
            }
        });
    }

    /**
     * Sets up the game elements such as map, player, enemies, and weapon manager.
     */
    private void setupGameElements() {
        mapManager = new MapManager();
        mapManager.loadMap();

        spawnManager = new Spawn(mapManager.getTiledMap(), mapManager.getUnitScale());
        player = spawnManager.getPlayer();
        enemies = spawnManager.getEnemies();

        weaponManager = new WeaponManager(mapManager.getTiledMap(), player, mapManager.getUnitScale());

        borderThickness = mapManager.getBorderThickness();

        cameraManager = new CameraManager(MapManager.getTiledMapWidth(), MapManager.getTiledMapHeight());
        cameraManager.updatePosition(new Vector2(player.getX(), player.getY()));

        // Start the countdown for round 1
        spawnManager.update(0);
    }

    /**
     * Restarts the game by reinitializing game elements and resetting the game state.
     */
    public void restartGame() {
        setupGameElements();
        isPaused = false;
        gameOver = false;
        backgroundMusic.stop();
        backgroundMusic.play();
    }

    /**
     * Opens the settings menu.
     */
    private void openSettings() {
        pauseMenu.hide();
        pauseMenu.getSettingsMenu().show();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (gameOver) {
            Gdx.input.setCursorCatched(false);
            gameOverScreen.render(delta);
            return;
        }

        handlePause();

        if (isPaused) {
            renderPauseMenu();
            return;
        }

        updateGameElements(delta);
        renderGameElements();
    }

    /**
     * Renders the game elements.
     */
    public void renderGameElements() {
        batch.setShader(BrightnessManager.getShaderProgram());
        batch.begin();
        BrightnessManager.getShaderProgram().setUniformf("u_brightness", BrightnessManager.getBrightness());
        mapManager.render(cameraManager.getCamera());
        batch.setProjectionMatrix(cameraManager.getCamera().combined);

        spawnManager.render(batch);
        weaponManager.render(batch);
        player.render(batch);
        renderCustomCursor();
        updateRoundInfo();
        updateScore();
        updateXp();
        updateKillCount();
        updateRemainingEnemies();
        batch.end();
        batch.setShader(null);
    }

    /**
     * Updates the score display.
     */
    private void updateScore() {
        int currentScore = ScoreManager.getInstance().getScore();
        Vector3 screenCoords = new Vector3(Gdx.graphics.getWidth() + Settings.SCORE_TEXT_X, Settings.SCORE_TEXT_Y, 0);
        Vector3 worldCoords = cameraManager.getCamera().unproject(screenCoords);
        font.draw(batch, "Points: " + currentScore, worldCoords.x, worldCoords.y);
    }

    /**
     * Updates the XP bar display.
     */
    private void updateXp() {
        int currentXp = player.getXpManager().getCurrentXp();
        int xpToNextLevel = player.getXpManager().getXpToNextLevel();
        int currentLevel = player.getXpManager().getLevel();
        
        Vector3 screenCoords = new Vector3(Settings.XP_BAR_X, Settings.XP_BAR_Y, 0);
        Vector3 worldCoords = cameraManager.getCamera().unproject(screenCoords);
    
        float barWidth = Settings.XP_BAR_WIDTH;
        float barHeight = Settings.XP_BAR_HEIGHT;
        float barX = worldCoords.x;
        float barY = worldCoords.y;
    
        float xpPercentage = (float) currentXp / xpToNextLevel;
        float xpBarWidth = barWidth * xpPercentage;
    
        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cameraManager.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(barX, barY, xpBarWidth, barHeight);
        shapeRenderer.end();
        batch.begin();
    
        font.draw(batch, "Level: " + currentLevel, barX + barWidth / 2 + Settings.LEVEL_TEXT_OFFSET_X, barY + barHeight / 2 + Settings.LEVEL_TEXT_OFFSET_Y);
    }

    /**
     * Handles the pause functionality.
     */
    private void handlePause() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (pauseMenu.getSettingsMenu().isVisible()) {
                pauseMenu.getSettingsMenu().hide();
                pauseMenu.show();
            } else {
                isPaused = !isPaused;
                if (isPaused) {
                    pauseMenu.show();
                    backgroundMusic.pause();
                } else {
                    pauseMenu.hide();
                    backgroundMusic.play();
                }
            }
        }
    }

    /**
     * Renders the pause menu.
     */
    private void renderPauseMenu() {
        batch.begin();
        pauseMenu.render(batch);
        batch.end();
    }

    /**
     * Updates the game elements.
     * @param delta time elapsed since the last frame
     */
    private void updateGameElements(float delta) {
        cameraManager.updatePosition(new Vector2(player.getX(), player.getY()));
        player.update(delta, cameraManager.getCamera());
        spawnManager.update(delta);
        weaponManager.update(delta, cameraManager.getCamera());
    }

    /**
     * Updates the round information display.
     */
    private void updateRoundInfo() {
        String roundInfo = "Round: " + spawnManager.getCurrentRound();
        if (!spawnManager.isRoundInProgress()) {
            roundInfo += " - Next round in: " + (int) spawnManager.getRoundCountdown();
        }
        Vector3 screenCoords = new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 50, 0);
        Vector3 worldCoords = cameraManager.getCamera().unproject(screenCoords);
        font.draw(batch, roundInfo, worldCoords.x, worldCoords.y);
    }

    /**
     * Renders the custom cursor.
     */
    private void renderCustomCursor() {
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cameraManager.unproject(mousePosition);
        batch.draw(AssetLoader.getTexture("custom_cursor"), mousePosition.x - AssetLoader.getTexture("custom_cursor").getWidth() / 2, mousePosition.y - AssetLoader.getTexture("custom_cursor").getHeight() / 2);
    }

    /**
     * Updates the kill count display.
     */
    private void updateKillCount() {
        String killCountText = "Kills: " + player.getKillCount();
        Vector3 screenCoords = new Vector3(50, Gdx.graphics.getHeight() - 50, 0);
        Vector3 worldCoords = cameraManager.getCamera().unproject(screenCoords);
        font.draw(batch, killCountText, worldCoords.x, worldCoords.y);
    }

    /**
     * Updates the remaining enemies count display.
     */
    private void updateRemainingEnemies() {
        String remainingEnemiesText = "Remaining Enemies: " + (spawnManager.getTotalEnemiesToSpawn() - player.getRoundKillCount());
        Vector3 screenCoords = new Vector3(50, Gdx.graphics.getHeight() - 100, 0);
        Vector3 worldCoords = cameraManager.getCamera().unproject(screenCoords);
        font.draw(batch, remainingEnemiesText, worldCoords.x, worldCoords.y);
    }

    @Override
    public void resize(int width, int height) {
        cameraManager.resize(width, height);
        mapManager.resize(cameraManager.getCamera());
        if (pauseMenu.getSettingsMenu().isVisible()) {
            pauseMenu.getSettingsMenu().updateViewport();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        AssetLoader.getTexture("custom_cursor").dispose();
        mapManager.dispose();
        pauseMenu.dispose();
        backgroundMusic.dispose();
    }

    public static float getBorderThickness() {
        return borderThickness;
    }

    public static float getTiledMapWidth() {
        return MapManager.getTiledMapWidth();
    }

    public static float getTiledMapHeight() {
        return MapManager.getTiledMapHeight();
    }

    public static void setGameOver(boolean isGameOver) {
        gameOver = isGameOver;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}