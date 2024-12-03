package io.github.some_example_name.weapon.range;

import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;

public class Vector extends Weapon {
    public Vector(Player player, List<Enemy> enemies) {
        super(player, enemies,
              (String) Settings.WEAPON_STATS.get("vector").get("name"), 
              (Texture) Settings.WEAPON_STATS.get("vector").get("texture"), 
              (int) Settings.WEAPON_STATS.get("vector").get("damage"), 
              (float) Settings.WEAPON_STATS.get("vector").get("fireRate"), 
              (float) Settings.WEAPON_STATS.get("vector").get("ballSpeed"),
              "rifle_shot"
        );
    }

    @Override
    protected String getPapShootSoundKey() {
        return "vector_pap_rifle_shot";
    }
}