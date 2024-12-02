package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.some_example_name.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {

    /**
     * The main method, entry point of the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        createApplication();
    }

    /**
     * Creates a new instance of the application with the default configuration.
     * @return a new Lwjgl3Application instance
     */
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    /**
     * Sets up the default configuration for the application.
     * @return the default Lwjgl3ApplicationConfiguration
     */
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        
        // Set the title of the application window
        configuration.setTitle("rogue_project");
        
        // Enable VSync to synchronize the frame rate with the monitor's refresh rate
        configuration.useVsync(true);
        
        // Set the foreground FPS slightly higher than the display's refresh rate
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        
        // Set the application to fullscreen mode using the display's current mode
        configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        /* configuration.setWindowedMode(1000, 1000); */
        
        // Set the window icons for different resolutions
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        
        return configuration;
    }
}