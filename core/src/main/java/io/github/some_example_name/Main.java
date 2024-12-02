package io.github.some_example_name;

import com.badlogic.gdx.Game;
import io.github.some_example_name.menu.StartScreen;

/**
 * Main class that extends Game and sets the initial screen.
 */
public class Main extends Game {

    /**
     * Called when the application is first created.
     * Loads assets and sets the initial screen to StartScreen.
     */
    @Override
    public void create() {
        // Load all necessary assets
        AssetLoader.load();
        
        // Set the initial screen to StartScreen
        setScreen(new StartScreen(this));
        /* setScreen(new FirstScreen()); */
    }
}