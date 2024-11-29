package io.github.some_example_name.character.enemies.bosses;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.character.enemies.Boss;
import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.Settings;

public class WereBear extends Boss {
    public WereBear(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(x, y, player, walkTexture, attackTexture, hurtTexture, deathTexture,
        (int) Settings.ENEMY_STATS.get("WereBear").get("hp"),
        (int[]) Settings.ENEMY_STATS.get("WereBear").get("speedRange"),
        (float) Settings.ENEMY_STATS.get("WereBear").get("range"),
        (int) Settings.ENEMY_STATS.get("WereBear").get("dmg"),
        (long) Settings.ENEMY_STATS.get("WereBear").get("attackCooldown"),
        9, // attackFrameCols set to 9
        (int) Settings.ENEMY_STATS.get("WereBear").get("points"),
        (int) Settings.ENEMY_STATS.get("WereBear").get("xpReward"));
    }
}