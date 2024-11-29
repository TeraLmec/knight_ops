package io.github.some_example_name.character.enemies.bosses;
import io.github.some_example_name.Settings;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.character.enemies.Boss;
import com.badlogic.gdx.graphics.Texture;

public class Lancer extends Boss {
    public Lancer(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture) {
        super(x, y, player, walkTexture, attackTexture, hurtTexture, deathTexture,
        (int) Settings.ENEMY_STATS.get("Lancer").get("hp"),
        (int[]) Settings.ENEMY_STATS.get("Lancer").get("speedRange"),
        (float) Settings.ENEMY_STATS.get("Lancer").get("range"),
        (int) Settings.ENEMY_STATS.get("Lancer").get("dmg"),
        (long) Settings.ENEMY_STATS.get("Lancer").get("attackCooldown"), 8,
        (int) Settings.ENEMY_STATS.get("Lancer").get("points"),
        (int) Settings.ENEMY_STATS.get("Lancer").get("xpReward"));
    }
}