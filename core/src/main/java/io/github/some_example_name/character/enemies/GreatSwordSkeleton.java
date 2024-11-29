package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class GreatSwordSkeleton extends Enemy {
    public GreatSwordSkeleton(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(9, player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 9, 2, 4,
              (int) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("range"), 
              (int) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("dmg"),
              (long) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("points"),
              (int) Settings.ENEMY_STATS.get("GreatSwordSkeleton").get("xpReward"));
    }
}