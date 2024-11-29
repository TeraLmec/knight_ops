package io.github.some_example_name.character.enemies.bosses;
import io.github.some_example_name.Settings;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.character.enemies.Boss;
import com.badlogic.gdx.graphics.Texture;

public class OrcRider extends Boss {
    public OrcRider(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(x, y, player, walkTexture, attackTexture, hurtTexture, deathTexture,
        (int) Settings.ENEMY_STATS.get("OrcRider").get("hp"),
        (int[]) Settings.ENEMY_STATS.get("OrcRider").get("speedRange"),
        (float) Settings.ENEMY_STATS.get("OrcRider").get("range"),
        (int) Settings.ENEMY_STATS.get("OrcRider").get("dmg"),
        (long) Settings.ENEMY_STATS.get("OrcRider").get("attackCooldown"),8,
        (int) Settings.ENEMY_STATS.get("OrcRider").get("points"),
        (int) Settings.ENEMY_STATS.get("OrcRider").get("xpReward"));
    }
}