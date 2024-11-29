package io.github.some_example_name.weapon.range;

import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.weapon.bullets.PistolAmmo;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class Mauser extends Weapon {
    public Mauser(Player player, List<Enemy> enemies) {
        super(player, enemies,
              (String) Settings.WEAPON_STATS.get("mauser").get("name"), 
              (Texture) Settings.WEAPON_STATS.get("mauser").get("texture"), 
              (int) Settings.WEAPON_STATS.get("mauser").get("damage"), 
              (float) Settings.WEAPON_STATS.get("mauser").get("fireRate"), 
              (float) Settings.WEAPON_STATS.get("mauser").get("ballSpeed"),
              (String) Settings.WEAPON_STATS.get("mauser").get("pistol_shot")
        );
    }

    @Override
    protected String getPapShootSoundPath() {
        return (String) Settings.WEAPON_STATS.get("mauser_pap").get("pistol_shot");
    }
}