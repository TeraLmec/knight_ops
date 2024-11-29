package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class ArmoredOrc extends Enemy {
    public ArmoredOrc(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 7, 2, 4,
              (int) Settings.ENEMY_STATS.get("ArmoredOrc").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("ArmoredOrc").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("ArmoredOrc").get("range"), 
              (int) Settings.ENEMY_STATS.get("ArmoredOrc").get("dmg"),
              (long) Settings.ENEMY_STATS.get("ArmoredOrc").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("ArmoredOrc").get("points"),
              (int) Settings.ENEMY_STATS.get("ArmoredOrc").get("xpReward"));
    }
}