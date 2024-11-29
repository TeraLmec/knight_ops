package io.github.some_example_name.weapon.bullets;

import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.weapon.Ball;

public class PapAmmo extends Ball {
    public PapAmmo(Vector2 startPosition, Vector2 direction, float speed) {
        super(AssetLoader.getTexture("pap_ammo"), startPosition, direction, AssetLoader.getTexture("pap_collision"), 12, 1, speed, 2f);
    }
}