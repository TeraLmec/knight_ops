package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class CustomCursor {
    private final Texture cursorTexture;
    private final int frameWidth;
    private final int frameHeight;

    public CustomCursor(String textureName) {
        cursorTexture = AssetLoader.getTexture(textureName);
        frameWidth = cursorTexture.getWidth() / 4;
        frameHeight = cursorTexture.getHeight();
    }

    public void render(SpriteBatch batch, CameraManager cameraManager) {
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cameraManager.unproject(mousePosition);
        int frameIndex = 0;
        if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            frameIndex = (int) ((TimeUtils.millis() / 150) % 4);
        }
        float rotation = frameIndex * 30; // Rotate 15 degrees plus each frame
        batch.draw(cursorTexture, mousePosition.x - frameWidth / 2, mousePosition.y - frameHeight / 2, 
                   frameWidth / 2, frameHeight / 2, frameWidth, frameHeight, 1, 1, rotation, 
                   frameIndex * frameWidth, 0, frameWidth, frameHeight, false, false);
    }
}