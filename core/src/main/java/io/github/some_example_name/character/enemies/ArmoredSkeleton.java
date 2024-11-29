
package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;

public class ArmoredSkeleton extends Enemy {
    public ArmoredSkeleton(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, 8, 2, 4,
              (int) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("hp"),
              (int[]) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("speedRange"),
              (float) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("range"), 
              (int) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("dmg"),
              (long) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("attackCooldown"),
              (int) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("points"),
              (int) Settings.ENEMY_STATS.get("ArmoredSkeleton").get("xpReward"));
    }
}