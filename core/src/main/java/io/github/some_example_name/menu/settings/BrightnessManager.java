package io.github.some_example_name.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BrightnessManager {
    private static float brightness = 1.0f;
    private static ShaderProgram shaderProgram;

    static {
        String vertexShader = Gdx.files.internal("assets/shaders/default.vert").readString();
        String fragmentShader = Gdx.files.internal("assets/shaders/brightness.frag").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        if (!shaderProgram.isCompiled()) {
            Gdx.app.error("ShaderProgram", "Compilation failed:\n" + shaderProgram.getLog());
        }
    }

    public static void setBrightness(float value) {
        brightness = value;
    }

    public static float getBrightness() {
        return brightness;
    }

    public static ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}