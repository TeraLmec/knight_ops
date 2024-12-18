package io.github.some_example_name.weapon.spawn_weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.AssetLoader;

public class Pap {
    private Texture texture;
    private Rectangle bounds;
    private int size = 300;

    public Pap(float x, float y) {
        this.texture = AssetLoader.getTexture("pap");
        if (this.texture == null) {
            throw new IllegalArgumentException("Texture not found: pap");
        }
        this.bounds = new Rectangle(x - size / 2, y - size / 2, size, size);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}