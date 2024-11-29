package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.Settings;
import io.github.some_example_name.character.Enemy;

public class Wizard extends Enemy {
    public Wizard(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 6, 2, 4,
              (int) Settings.ENEMY_STATS.get("Wizard").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("Wizard").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("Wizard").get("range"), 
              (int) Settings.ENEMY_STATS.get("Wizard").get("dmg"),
              (long) Settings.ENEMY_STATS.get("Wizard").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("Wizard").get("points"),
              (int) Settings.ENEMY_STATS.get("Wizard").get("xpReward"));
    }
}