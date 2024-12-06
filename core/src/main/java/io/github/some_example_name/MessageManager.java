package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.some_example_name.character.Player;

public class MessageManager {
    private BitmapFont font;
    private GlyphLayout layout;
    private Player player;
    private String message;
    private long messageStartTime;
    private static final long DISPLAY_DURATION = 1500;
    private static final long FADE_DURATION = 1000;

    public MessageManager(Player player) {
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();
        this.player = player;
        this.message = "";
        this.messageStartTime = 0;
    }

    public void displayMessage(SpriteBatch batch, String message, float x, float y) {
        layout.setText(font, message);
        font.getData().setScale(2); // Increase font size
        float textWidth = layout.width;
        font.draw(batch, message, x - textWidth * 0.95f, y + 50); // Center the message
        font.getData().setScale(1); // Reset font size
    }

    public void render(SpriteBatch batch) {
        if (!message.isEmpty()) {
            long elapsedTime = TimeUtils.timeSinceMillis(messageStartTime);
            if (elapsedTime < DISPLAY_DURATION + FADE_DURATION) {
                float alpha = 1f;
                if (elapsedTime > DISPLAY_DURATION) {
                    alpha = 1f - (elapsedTime - DISPLAY_DURATION) / (float) FADE_DURATION;
                }
                font.setColor(1, 1, 1, alpha);
                displayMessage(batch, message, player.getX(), player.getY() + 50);
                font.setColor(Color.WHITE); // Reset color
            } else {
                message = "";
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.messageStartTime = TimeUtils.millis();
    }
}