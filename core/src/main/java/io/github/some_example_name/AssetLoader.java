package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetLoader class responsible for loading and managing game assets such as textures.
 */
public class AssetLoader implements Disposable {
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, Sound> sounds = new HashMap<>();
    private static final Map<String, Music> music = new HashMap<>();

    /**
     * Loads all game assets.
     */
    public static void load() {
        loadTextures();
        loadSounds();
        loadMusic();
    }

    /**
     * Loads a texture and stores it in the textures map.
     * @param key the key to associate with the texture
     * @param path the path to the texture file
     */
    private static void loadTexture(String key, String path) {
        textures.put(key, new Texture(Gdx.files.internal(path)));
    }

    /**
     * Loads all textures used in the game.
     */
    private static void loadTextures() {
        // Player textures
        loadTexture("player_walk", "assets/player/walk.png");
        loadTexture("player_idle", "assets/player/idle.png");
        loadTexture("player_melee", "assets/player/melee.png");
        loadTexture("player_death", "assets/player/player_death.png");

        // Werebear textures
        loadTexture("werebear_walk", "assets/enemies/bosses/wereBear/walk.png");
        loadTexture("werebear_attack", "assets/enemies/bosses/wereBear/attack.png");
        loadTexture("werebear_hurt", "assets/enemies/bosses/wereBear/hurt.png");
        loadTexture("werebear_death", "assets/enemies/bosses/wereBear/death.png");

        // Armored Orc textures
        loadTexture("armored_orc_walk", "assets/enemies/armoredOrc/walk.png");
        loadTexture("armored_orc_attack", "assets/enemies/armoredOrc/attack.png");
        loadTexture("armored_orc_hurt", "assets/enemies/armoredOrc/hurt.png");
        loadTexture("armored_orc_death", "assets/enemies/armoredOrc/death.png");

        // Armored Skeleton textures
        loadTexture("armored_skeleton_walk", "assets/enemies/armoredSkeleton/walk.png");
        loadTexture("armored_skeleton_attack", "assets/enemies/armoredSkeleton/attack.png");
        loadTexture("armored_skeleton_hurt", "assets/enemies/armoredSkeleton/hurt.png");
        loadTexture("armored_skeleton_death", "assets/enemies/armoredSkeleton/death.png");

        // Elite Orc textures
        loadTexture("elite_orc_walk", "assets/enemies/eliteOrc/walk.png");
        loadTexture("elite_orc_attack", "assets/enemies/eliteOrc/attack.png");
        loadTexture("elite_orc_hurt", "assets/enemies/eliteOrc/hurt.png");
        loadTexture("elite_orc_death", "assets/enemies/eliteOrc/death.png");

        // Great Sword Skeleton textures
        loadTexture("great_sword_skeleton_walk", "assets/enemies/greatSwordSkeleton/walk.png");
        loadTexture("great_sword_skeleton_attack", "assets/enemies/greatSwordSkeleton/attack.png");
        loadTexture("great_sword_skeleton_hurt", "assets/enemies/greatSwordSkeleton/hurt.png");
        loadTexture("great_sword_skeleton_death", "assets/enemies/greatSwordSkeleton/death.png");

        // Orc textures
        loadTexture("orc_walk", "assets/enemies/orc/walk.png");
        loadTexture("orc_attack", "assets/enemies/orc/attack.png");
        loadTexture("orc_hurt", "assets/enemies/orc/hurt.png");
        loadTexture("orc_death", "assets/enemies/orc/death.png");

        // Orc Rider textures
        loadTexture("orc_rider_walk", "assets/enemies/bosses/orcRider/walk.png");
        loadTexture("orc_rider_attack", "assets/enemies/bosses/orcRider/attack.png");
        loadTexture("orc_rider_hurt", "assets/enemies/bosses/orcRider/hurt.png");
        loadTexture("orc_rider_death", "assets/enemies/bosses/orcRider/death.png");

        // Lancer textures
        loadTexture("lancer_walk", "assets/enemies/bosses/lancer/walk.png");
        loadTexture("lancer_attack", "assets/enemies/bosses/lancer/attack.png");
        loadTexture("lancer_hurt", "assets/enemies/bosses/lancer/hurt.png");
        loadTexture("lancer_death", "assets/enemies/bosses/lancer/death.png");

        // Skeleton textures
        loadTexture("skeleton_walk", "assets/enemies/skeleton/walk.png");
        loadTexture("skeleton_attack", "assets/enemies/skeleton/attack.png");
        loadTexture("skeleton_hurt", "assets/enemies/skeleton/hurt.png");
        loadTexture("skeleton_death", "assets/enemies/skeleton/death.png");
        
        // Armored Axeman textures
        loadTexture("armored_axeman_walk", "assets/enemies/armoredAxeman/walk.png");
        loadTexture("armored_axeman_attack", "assets/enemies/armoredAxeman/attack.png");
        loadTexture("armored_axeman_hurt", "assets/enemies/armoredAxeman/hurt.png");
        loadTexture("armored_axeman_death", "assets/enemies/armoredAxeman/death.png");

        // Slime textures
        loadTexture("slime_walk", "assets/enemies/slime/walk.png");
        loadTexture("slime_attack", "assets/enemies/slime/attack.png");
        loadTexture("slime_hurt", "assets/enemies/slime/hurt.png");
        loadTexture("slime_death", "assets/enemies/slime/death.png");

        // Werewolf textures
        loadTexture("werewolf_walk", "assets/enemies/bosses/wereWolf/walk.png");
        loadTexture("werewolf_attack", "assets/enemies/bosses/wereWolf/attack.png");
        loadTexture("werewolf_hurt", "assets/enemies/bosses/wereWolf/hurt.png");
        loadTexture("werewolf_death", "assets/enemies/bosses/wereWolf/death.png");

        // Wizard textures
        loadTexture("wizard_walk", "assets/enemies/wizard/walk.png");
        loadTexture("wizard_attack", "assets/enemies/wizard/attack.png");
        loadTexture("wizard_hurt", "assets/enemies/wizard/hurt.png");
        loadTexture("wizard_death", "assets/enemies/wizard/death.png");

        // Weapon textures
        loadTexture("pap_ammo", "assets/weapons/projectiles/pap_ammo.png");
        loadTexture("pap_collision", "assets/weapons/collisions/laser_collision.png");
        loadTexture("mauser_normal", "assets/weapons/mauser_normal.png");
        loadTexture("bmg_normal", "assets/weapons/50bmg_normal.png");
        loadTexture("ar_normal", "assets/weapons/ar_normal.png");
        loadTexture("vector_normal", "assets/weapons/vector_normal.png");
        loadTexture("winchester_normal", "assets/weapons/winchester_normal.png");
        loadTexture("pistol_ammo", "assets/weapons/projectiles/pistol_ammo.png");
        loadTexture("rifle_ammo", "assets/weapons/projectiles/rifle_ammo.png");
        loadTexture("shotgun_ammo", "assets/weapons/projectiles/shotgun_ammo.png");
        loadTexture("bullet_collision", "assets/weapons/collisions/bullet_collision.png");

        // Pap weapon textures
        loadTexture("mauser_pap", "assets/weapons/mauser_pap.png");
        loadTexture("winchester_pap", "assets/weapons/winchester_pap.png");
        loadTexture("bmg_pap", "assets/weapons/50bmg_pap.png");
        loadTexture("ar_pap", "assets/weapons/ar_pap.png");
        loadTexture("vector_pap", "assets/weapons/vector_pap.png");

        // Miscellaneous textures
        loadTexture("custom_cursor", "assets/crosshair/crosshair.png");

        // Pap texture
        loadTexture("pap", "assets/weapons/pap/pap.png");

        // Smoke texture
        loadTexture("smoke", "assets/player/dash.png");

        // Background texture
        loadTexture("background", "assets/font/affiche.png");
    }

    /**
     * Loads a sound and stores it in the sounds map.
     * @param key the key to associate with the sound
     * @param path the path to the sound file
     */
    private static void loadSound(String key, String path) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    /**
     * Loads all sounds used in the game.
     */
    private static void loadSounds() {
        loadSound("melee_normal", "assets/sounds/melee_normal_sound.mp3");
        loadSound("melee_touched", "assets/sounds/melee_touched_sound.mp3");
        loadSound("hit_marker", "assets/sounds/hit_marker1.mp3");
        loadSound("button_click", "assets/sounds/start_menu/clickButton.mp3");
        loadSound("pistol_shot", "assets/sounds/pistol_shot.mp3");
        loadSound("shotgun_shot", "assets/sounds/shotgun_shot.mp3");
        loadSound("rifle_shot", "assets/sounds/rifle_shot.mp3");
        loadSound("sniper_shot", "assets/sounds/sniper_shot.mp3");
        loadSound("winchester_pap_shotgun_shot", "assets/sounds/shotgun_shot.mp3");
        loadSound("bmg_pap_sniper_shot", "assets/sounds/sniper_shot.mp3");
        loadSound("pap_shot", "assets/sounds/pap_shot.mp3");
        loadSound("dodged", "assets/sounds/dodged.mp3");
        loadSound("enemy_attack", "assets/sounds/enemy_attack.mp3");
        loadSound("player_death", "assets/sounds/player_death.mp3");
        loadSound("pap_done", "assets/sounds/pap_done.mp3");
        loadSound("pap_deny", "assets/sounds/pap_deny.mp3");
        loadSound("pap_jingle", "assets/sounds/pap_jingle.mp3");
        loadSound("new_round", "assets/sounds/new_round.mp3");
        loadSound("button_hover", "assets/sounds/button_hover.mp3");
    }

    /**
     * Loads a music and stores it in the music map.
     * @param key the key to associate with the music
     * @param path the path to the music file
     */
    private static void loadMusic(String key, String path) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    /**
     * Loads all music used in the game.
     */
    private static void loadMusic() {
        loadMusic("background_music", "assets/sounds/start_menu/from_past_to_present.mp3");
        loadMusic("combat_music", "assets/sounds/game/blindpick.mp3");
        loadMusic("end_game1", "assets/sounds/end_game1.mp3");
        loadMusic("end_game2", "assets/sounds/end_game2.mp3");
    }

    /**
     * Retrieves a texture by its name.
     * @param name the name of the texture
     * @return the texture associated with the name, or null if not found
     */
    public static Texture getTexture(String name) {
        Texture texture = textures.get(name);
        if (texture == null) {
            System.out.println("Texture not found: " + name);
        }
        return texture;
    }

    /**
     * Retrieves a sound by its name.
     * @param name the name of the sound
     * @return the sound associated with the name, or null if not found
     */
    public static Sound getSound(String name) {
        return sounds.get(name);
    }

    /**
     * Retrieves a music by its name.
     * @param name the name of the music
     * @return the music associated with the name, or null if not found
     */
    public static Music getMusic(String name) {
        return music.get(name);
    }

    /**
     * Disposes of all loaded assets.
     */
    @Override
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        sounds.clear();
        for (Music music : music.values()) {
            music.dispose();
        }
        music.clear();
    }
}