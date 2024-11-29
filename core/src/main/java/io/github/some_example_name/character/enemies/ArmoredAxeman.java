package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class ArmoredAxeman extends Enemy {
    public ArmoredAxeman(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 9, 2, 4,
              (int) Settings.ENEMY_STATS.get("ArmoredAxeman").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("ArmoredAxeman").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("ArmoredAxeman").get("range"), 
              (int) Settings.ENEMY_STATS.get("ArmoredAxeman").get("dmg"),
              (long) Settings.ENEMY_STATS.get("ArmoredAxeman").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("ArmoredAxeman").get("points"),
              (int) Settings.ENEMY_STATS.get("ArmoredAxeman").get("xpReward"));
    }
}