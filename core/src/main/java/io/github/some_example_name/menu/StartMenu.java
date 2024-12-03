package io.github.some_example_name.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.AssetLoader;

public class StartMenu {
    private Stage stage;
    private boolean isVisible;
    private Runnable onStartGame;
    private Runnable onOpenSettings;
    private Runnable onQuitGame;
    private Sound buttonClickSound;

    public StartMenu(Runnable onStartGame, Runnable onOpenSettings, Runnable onQuitGame) {
        this.onStartGame = onStartGame;
        this.onOpenSettings = onOpenSettings;
        this.onQuitGame = onQuitGame;

        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("assets/skin/tracer-ui.json")); // Change this line

        // Load the button click sound
        buttonClickSound = AssetLoader.getSound("button_click");

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton startButton = new TextButton("Lancer une partie", skin);
        TextButton settingsButton = new TextButton("Parametres", skin);
        TextButton quitButton = new TextButton("Quitter le jeu", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                isVisible = false;
                Gdx.input.setInputProcessor(null);
                if (onStartGame != null) {
                    onStartGame.run();
                }
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                isVisible = false;
                Gdx.input.setInputProcessor(null);
                if (onOpenSettings != null) {
                    onOpenSettings.run();
                }
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                isVisible = false;
                Gdx.input.setInputProcessor(null);
                if (onQuitGame != null) {
                    onQuitGame.run();
                }
            }
        });

        table.add(startButton).fillX().uniformX().pad(10).height(60).width(200);
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).fillX().uniformX().pad(10).height(60).width(200);
        table.row();
        table.add(quitButton).fillX().uniformX().pad(10).height(60).width(200);

        isVisible = true;
    }

    public void render() {
        if (isVisible) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    public void show() {
        isVisible = true;
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorCatched(false); // Show the cursor
    }

    public void hide() {
        isVisible = false;
        Gdx.input.setInputProcessor(null);
        // Do not hide the cursor here
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void dispose() {
        stage.dispose();
        buttonClickSound.dispose();
    }

    public void updateViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}