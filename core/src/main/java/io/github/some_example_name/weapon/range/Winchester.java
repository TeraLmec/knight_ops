package io.github.some_example_name.weapon.range;

import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class Winchester extends Weapon {
    public Winchester(Player player, List<Enemy> enemies) {
        super(player, enemies,
              (String) Settings.WEAPON_STATS.get("winchester").get("name"), 
              (Texture) Settings.WEAPON_STATS.get("winchester").get("texture"), 
              (int) Settings.WEAPON_STATS.get("winchester").get("damage"), 
              (float) Settings.WEAPON_STATS.get("winchester").get("fireRate"), 
              (float) Settings.WEAPON_STATS.get("winchester").get("ballSpeed"),
              "shotgun_shot",
              (int) Settings.WINCHESTER_COST
        );
    }

    @Override
    protected String getPapShootSoundKey() {
        return "winchester_pap_shotgun_shot";
    }
}