package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class Slime extends Enemy {
    public Slime(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(6, player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 6, 2, 4,
              (int) Settings.ENEMY_STATS.get("Slime").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("Slime").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("Slime").get("range"), 
              (int) Settings.ENEMY_STATS.get("Slime").get("dmg"),
              (long) Settings.ENEMY_STATS.get("Slime").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("Slime").get("points"),
              (int) Settings.ENEMY_STATS.get("Slime").get("xpReward"));
    }
}