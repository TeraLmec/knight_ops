package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hitbox {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    public static void renderHitbox(SpriteBatch batch, Rectangle hitbox, Color color) {
        if (Settings.SHOW_HITBOXES) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            shapeRenderer.end();
            batch.begin();
        }
    }

    public static void renderMeleeHitbox(SpriteBatch batch, Rectangle hitbox, Color color, float rotation) {
        if (Settings.SHOW_MELEE_HITBOX) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width / 2, hitbox.height / 2, hitbox.width, hitbox.height, 1, 1, rotation);
            shapeRenderer.end();
            batch.begin();
        }
    }
}