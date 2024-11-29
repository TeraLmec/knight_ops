package io.github.some_example_name.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.FirstScreen;
import io.github.some_example_name.menu.settings.SettingsMenu;

public class StartScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private StartMenu startMenu;
    private SettingsMenu settingsMenu;
    private Music soundtrack;
    private Texture backgroundTexture;

    public StartScreen(Game game) {
            this.game = game;
            batch = new SpriteBatch();
            startMenu = new StartMenu(this::startGame, this::openSettings, this::quitGame);
            settingsMenu = new SettingsMenu(null, startMenu);

            // Load and play the soundtrack
            soundtrack = Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/start_menu/from_past_to_present.mp3"));
            soundtrack.setLooping(true);
            soundtrack.play();

            // Load the background texture
            backgroundTexture = new Texture(Gdx.files.internal("assets/font/affiche.png"));

            // Show the start menu
            startMenu.show();
    }

    private void startGame() {
        // Stop the soundtrack
        soundtrack.stop();
        game.setScreen(new FirstScreen());
    }

    private void openSettings() {
        startMenu.hide();
        settingsMenu.show();
    }

    private void quitGame() {
        Gdx.app.exit();
    }

    @Override
    public void show() {
        // No implementation needed
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        batch.begin();
        // Draw the background texture only if the start menu is visible
        if (startMenu.isVisible()) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();

        batch.begin();
        if (startMenu.isVisible()) {
            startMenu.render();
        } else if (settingsMenu.isVisible()) {
            settingsMenu.render();
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        if (settingsMenu.isVisible()) {
            settingsMenu.updateViewport();
        }
        startMenu.updateViewport();
    }

    @Override
    public void pause() {
        // No implementation needed
    }

    @Override
    public void resume() {
        // No implementation needed
    }

    @Override
    public void hide() {
        // No implementation needed
    }

    @Override
    public void dispose() {
        batch.dispose();
        startMenu.dispose();
        settingsMenu.dispose();
        soundtrack.dispose();
        backgroundTexture.dispose();
    }
}