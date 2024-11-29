package io.github.some_example_name.weapon.range;

import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.weapon.bullets.PistolAmmo;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class Ar extends Weapon {
    public Ar(Player player, List<Enemy> enemies) {
        super(player, enemies,
              (String) Settings.WEAPON_STATS.get("ar").get("name"), 
              (Texture) Settings.WEAPON_STATS.get("ar").get("texture"), 
              (int) Settings.WEAPON_STATS.get("ar").get("damage"), 
              (float) Settings.WEAPON_STATS.get("ar").get("fireRate"), 
              (float) Settings.WEAPON_STATS.get("ar").get("ballSpeed"),
              (String) Settings.WEAPON_STATS.get("ar").get("rifle_shot")
        );
    }

    @Override
    protected String getPapShootSoundPath() {
        return (String) Settings.WEAPON_STATS.get("ar_pap").get("rifle_shot");
    }
}