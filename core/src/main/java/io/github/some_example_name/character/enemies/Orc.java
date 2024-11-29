package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class Orc extends Enemy {
    public Orc(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 6, 2, 4,
              (int) Settings.ENEMY_STATS.get("Orc").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("Orc").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("Orc").get("range"), 
              (int) Settings.ENEMY_STATS.get("Orc").get("dmg"),
              (long) Settings.ENEMY_STATS.get("Orc").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("Orc").get("points"),
              (int) Settings.ENEMY_STATS.get("Orc").get("xpReward"));
    }
}