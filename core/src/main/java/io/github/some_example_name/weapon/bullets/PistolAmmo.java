package io.github.some_example_name.weapon.bullets;

import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.weapon.Ball;

public class PistolAmmo extends Ball {
    public PistolAmmo(Vector2 startPosition, Vector2 direction, float speed) {
        super(AssetLoader.getTexture("pistol_ammo"), startPosition, direction, AssetLoader.getTexture("bullet_collision"), 12, 1, speed, 4f);
    }
}