package io.github.some_example_name.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.some_example_name.menu.PauseMenu;
import io.github.some_example_name.menu.StartMenu;

public class SettingsMenu {
    private Stage stage;
    private boolean isVisible;
    private PauseMenu pauseMenu;
    private StartMenu startMenu;
    private float tempBrightness;
    private SelectBox<String> resolutionSelectBox;
    private String selectedResolution;
    private static final String PREFS_NAME = "settings";
    private static final String RESOLUTION_KEY = "resolution";
    private Runnable onResolutionChange;

    public SettingsMenu(PauseMenu pauseMenu, StartMenu startMenu) {
        this.pauseMenu = pauseMenu;
        this.startMenu = startMenu;
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("assets/skin/tracer-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Luminosité
        TextButton brightnessLabel = new TextButton("Luminosité", skin);
        Slider brightnessSlider = new Slider(0, 1, 0.01f, false, skin);
        brightnessSlider.setValue(BrightnessManager.getBrightness());
        brightnessSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float brightnessValue = brightnessSlider.getValue();
                BrightnessManager.setBrightness(brightnessValue);
            }
        });
        table.add(brightnessLabel).fillX().uniformX();
        table.add(brightnessSlider).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

        // Résolution
        TextButton resolutionLabel = new TextButton("Résolution", skin);
        resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems("Plein écran", "1920x1080", "1600x900", "1280x720");
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedResolution = resolutionSelectBox.getSelected();
            }
        });
        table.add(resolutionLabel).fillX().uniformX();
        table.add(resolutionSelectBox).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

        // Bouton appliquer
        TextButton applyButton = new TextButton("Appliquer", skin);
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                applyResolutionChange();
            }
        });
        table.add(applyButton).colspan(2).center().padTop(20);
        table.row().pad(10, 0, 10, 0);

        // Bouton retour
        TextButton backButton = new TextButton("Retour", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                if (pauseMenu != null) {
                    pauseMenu.show();
                } else if (startMenu != null) {
                    startMenu.show();
                }
            }
        });
        table.add(backButton).colspan(2).center().padTop(20);
    }

    public void applyResolutionChange() {
        if (selectedResolution != null) {
            if (selectedResolution.equals("Plein écran")) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            } else {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                Gdx.graphics.setWindowedMode(width, height);
            }
            updateViewport();
            if (pauseMenu != null) {
                pauseMenu.updateViewport();
            }
            if (startMenu != null) {
                startMenu.updateViewport();
            }
            if (onResolutionChange != null) {
                onResolutionChange.run();
            }
        }
    } 

    public void saveResolution() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putString(RESOLUTION_KEY, selectedResolution);
        prefs.flush();
    }

    public void loadResolution() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        selectedResolution = prefs.getString(RESOLUTION_KEY, "1920x1080");
        resolutionSelectBox.setSelected(selectedResolution);
        applyResolutionChange();
    }

    public void updateViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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
    }

    public void setPauseMenu(PauseMenu pauseMenu) {
        this.pauseMenu = pauseMenu;
    }

    public void setStartMenu(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    public void setOnResolutionChange(Runnable onResolutionChange) {
        this.onResolutionChange = onResolutionChange;
    }

    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }
    
}