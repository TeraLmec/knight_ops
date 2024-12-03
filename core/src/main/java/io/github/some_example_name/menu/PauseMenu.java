package io.github.some_example_name.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.some_example_name.menu.settings.SettingsMenu;
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.FirstScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private Stage stage;
    private boolean isVisible;
    private SettingsMenu settingsMenu;
    private final FirstScreen round;

    public PauseMenu(Runnable onResume, Runnable onRestart, Runnable onOpenSettings, Runnable onQuitGame, FirstScreen round) {
        stage = new Stage(new ScreenViewport());
        this.round = round;
        this.stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("assets/skin/tracer-ui.json")); // Change this line
        settingsMenu = new SettingsMenu(this, null);

        TextButton resumeButton = new TextButton("Reprendre la partie", skin);
        TextButton restartButton = new TextButton("Recommencer la partie", skin);
        TextButton settingsButton = new TextButton("Parametres", skin);
        TextButton quitButton = new TextButton("Quitter le jeu", skin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                Gdx.input.setCursorCatched(true);
                if (onResume != null) {
                    onResume.run();
                }
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                if (onRestart != null) {
                    onRestart.run();
                    Gdx.input.setCursorCatched(true);
                }
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsMenu.show();
                setVisible(false);
                Gdx.input.setInputProcessor(null);
                if (onOpenSettings != null) {
                    onOpenSettings.run();
                }
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onQuitGame != null) {
                    onQuitGame.run();
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(resumeButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(restartButton).fillX().uniformX();
        table.row();
        table.add(settingsButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton).fillX().uniformX();

        stage.addActor(table);
        isVisible = false;
    }

    public void render(SpriteBatch batch) {
        if (isVisible) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        settingsMenu.render();
    }

    public void show() {
        setVisible(true);
        Gdx.input.setInputProcessor(stage);
    }

    public void hide() {
        setVisible(false);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(round.getPlayer());
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (visible) {
            Gdx.input.setInputProcessor(stage);
            Gdx.input.setCursorCatched(false); // Show the cursor
        } else {
            Gdx.input.setInputProcessor(null);
            // Do not hide the cursor here
        }
    }

    public SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    public void dispose() {
        stage.dispose();
    }

    public void updateViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}