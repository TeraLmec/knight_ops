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
              (String) Settings.WEAPON_STATS.get("winchester").get("shotgun_shot")
        );
    }

    @Override
    protected String getPapShootSoundPath() {
        return (String) Settings.WEAPON_STATS.get("winchester_pap").get("shotgun_shot");
    }
}