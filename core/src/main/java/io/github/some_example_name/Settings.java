package io.github.some_example_name;

import java.util.HashMap;
import java.util.Map;

/**
 * Settings class that contains various constants and configurations for the game.
 */
public class Settings {
    // Colors
    public static final String SPECIAL = "#e6bc49";
    public static final String DENY = "#8c0000";
    public static final String DEFAULT = "#999999";
    public static final String PAP = "#9c006d";
    public static final String ACQUIRED = "#009c7c";

    // Spawn constants
    public static final float SPAWN_INTERVAL = 2f;
    public static final float SPAWN_DISTANCE_THRESHOLD = 0;
    public static final float ROUND_INTERVAL = 8f;

    // Player constants
    public static final long DASH_DURATION = 750;
    public static final float DASH_MULTIPLIER = 2.85f;
    public static final int MELEE_DAMAGE = 55;
    public static final long MELEE_COOLDOWN = 2500;
    public static final float KNOCKBACK_DISTANCE = 120f;
    public static final float MIN_CURSOR_DISTANCE = 30f;
    public static final int LEVEL_STAT = 20;
    public static final int BASE_HP = 250;
    public static final float BASE_SPEED = 600f;
    public static final float ATTACK_STATE_TIME = 0.1f;
    public static final float DEATH_ANIMATION_FRAME_DURATION = 0.1f;

    // Enemy constants
    public static final float HURT_SPEED_MULT = 0.8f;
    public static final float KNOCKBACK_DURATION = 0.4f;

    // Map constants
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    public static final int MAP_WIDTH_IN_TILES = 50;
    public static final int MAP_HEIGHT_IN_TILES = 50;
    public static final String MAP_PATH = "assets/map/dungeon.tmx";
    public static final float UNIT_SCALE = 6.0f;

    // Sound pitch variation constants
    public static final float MIN_PITCH = 0.85f;
    public static final float MAX_PITCH = 1.15f;

    // Volume settings
    public static final float SHOOT_VOLUME = 0.75f;
    public static final float HIT_VOLUME = 0.35f;
    public static final float MELEE_NORMAL_VOLUME = 0.35f;
    public static final float MELEE_TOUCHED_VOLUME = 0.35f;
    public static final float BUTTON_CLICK_VOLUME = 0.5f;
    public static final float MUSIC_VOLUME = 0.75f;
    public static final float PAP_JINGLE_VOLUME = 0.8f;
    public static final float DODGED_VOLUME = 0.5f;
    public static final float ENEMY_DAMAGE_VOLUME = 0.5f;
    public static final float PLAYER_DEATH_VOLUME = 0.7f;
    public static final float PAP_DONE_VOLUME = 0.8f;
    public static final float PAP_DENY_VOLUME = 0.8f;
    public static final float BUTTON_HOVER_VOLUME = 0.5f;
    public static final float NEW_ROUND_VOLUME = 0.8f;
    public static final float GUN_PURCHASE_VOLUME = 0.8f;

    // Weapon costs
    public static final int MAUSER_COST = 500;
    public static final int WINCHESTER_COST = 750;
    public static final int VECTOR_COST = 1250;
    public static final int BMG_COST = 1500;
    public static final int AR_COST = 1150;

    // Weapon upgrade multiplier
    public static final float DMG_MULT = 1.5f;
    public static final float FIRE_RATE_MULT = 0.8f;
    public static final float BALL_SPEED_MULT = 1.15f;

    // Enemy stats map
    public static final Map<String, Map<String, Object>> ENEMY_STATS = new HashMap<>();

    static {
        // Armored Axeman stats
        Map<String, Object> armoredAxemanStats = new HashMap<>();
        armoredAxemanStats.put("hp", 250);
        armoredAxemanStats.put("range", 80f);
        armoredAxemanStats.put("speedRange", new int[]{350, 600});
        armoredAxemanStats.put("dmg", 45);
        armoredAxemanStats.put("attackCooldown", 1500L);
        armoredAxemanStats.put("points", 35);
        armoredAxemanStats.put("xpReward", 15);
        armoredAxemanStats.put("texture", AssetLoader.getTexture("armored_axeman_walk"));
        ENEMY_STATS.put("ArmoredAxeman", armoredAxemanStats);

        // Armored Orc stats
        Map<String, Object> armoredOrcStats = new HashMap<>();
        armoredOrcStats.put("hp", 190);
        armoredOrcStats.put("range", 90f);
        armoredOrcStats.put("speedRange", new int[]{400, 600});
        armoredOrcStats.put("dmg", 55);
        armoredOrcStats.put("attackCooldown", 1300L);
        armoredOrcStats.put("points", 35);
        armoredOrcStats.put("xpReward", 15);
        armoredOrcStats.put("texture", AssetLoader.getTexture("armored_orc_walk"));
        ENEMY_STATS.put("ArmoredOrc", armoredOrcStats);

        // Armored Skeleton stats
        Map<String, Object> armoredSkeletonStats = new HashMap<>();
        armoredSkeletonStats.put("hp", 175);
        armoredSkeletonStats.put("range", 120f);
        armoredSkeletonStats.put("speedRange", new int[]{400, 600});
        armoredSkeletonStats.put("dmg", 55);
        armoredSkeletonStats.put("attackCooldown", 1000L);
        armoredSkeletonStats.put("points", 35);
        armoredSkeletonStats.put("xpReward", 15);
        armoredSkeletonStats.put("texture", AssetLoader.getTexture("armored_skeleton_walk"));
        ENEMY_STATS.put("ArmoredSkeleton", armoredSkeletonStats);

        // Elite Orc stats
        Map<String, Object> eliteOrcStats = new HashMap<>();
        eliteOrcStats.put("hp", 300);
        eliteOrcStats.put("range", 150f);
        eliteOrcStats.put("speedRange", new int[]{400, 650});
        eliteOrcStats.put("dmg", 60);
        eliteOrcStats.put("attackCooldown", 750L);
        eliteOrcStats.put("points", 30);
        eliteOrcStats.put("xpReward", 25);
        eliteOrcStats.put("texture", AssetLoader.getTexture("elite_orc_walk"));
        ENEMY_STATS.put("EliteOrc", eliteOrcStats);

        // Great Sword Skeleton stats
        Map<String, Object> greatSwordSkeletonStats = new HashMap<>();
        greatSwordSkeletonStats.put("hp", 120);
        greatSwordSkeletonStats.put("range", 150f);
        greatSwordSkeletonStats.put("speedRange", new int[]{350, 650});
        greatSwordSkeletonStats.put("dmg", 75);
        greatSwordSkeletonStats.put("attackCooldown", 1500L);
        greatSwordSkeletonStats.put("points", 40);
        greatSwordSkeletonStats.put("xpReward", 25);
        greatSwordSkeletonStats.put("texture", AssetLoader.getTexture("great_sword_skeleton_walk"));
        ENEMY_STATS.put("GreatSwordSkeleton", greatSwordSkeletonStats);

        // Lancer stats
        Map<String, Object> lancerStats = new HashMap<>();
        lancerStats.put("hp", 1500);
        lancerStats.put("range", 80f);
        lancerStats.put("speedRange", new int[]{650, 800});
        lancerStats.put("dmg", 95);
        lancerStats.put("attackCooldown", 2500L);
        lancerStats.put("points", 500);
        lancerStats.put("xpReward", 200);
        lancerStats.put("texture", AssetLoader.getTexture("lancer_walk"));
        ENEMY_STATS.put("Lancer", lancerStats);

        // Orc Rider stats
        Map<String, Object> orcRiderStats = new HashMap<>();
        orcRiderStats.put("hp", 1200);
        orcRiderStats.put("range", 65f);
        orcRiderStats.put("speedRange", new int[]{450, 650});
        orcRiderStats.put("dmg", 90);
        orcRiderStats.put("attackCooldown", 2500L);
        orcRiderStats.put("points", 500);
        orcRiderStats.put("xpReward", 200);
        orcRiderStats.put("texture", AssetLoader.getTexture("orc_rider_walk"));
        ENEMY_STATS.put("OrcRider", orcRiderStats);

        // Orc stats
        Map<String, Object> orcStats = new HashMap<>();
        orcStats.put("hp", 110);
        orcStats.put("range", 100f);
        orcStats.put("speedRange", new int[]{350, 600});
        orcStats.put("dmg", 50);
        orcStats.put("attackCooldown", 750L);
        orcStats.put("points", 20);
        orcStats.put("xpReward", 15);
        orcStats.put("texture", AssetLoader.getTexture("orc_walk"));
        ENEMY_STATS.put("Orc", orcStats);

        // Skeleton stats
        Map<String, Object> skeletonStats = new HashMap<>();
        skeletonStats.put("hp", 100);
        skeletonStats.put("range", 90f);
        skeletonStats.put("speedRange", new int[]{400, 600});
        skeletonStats.put("dmg", 35);
        skeletonStats.put("attackCooldown", 600L);
        skeletonStats.put("points", 15);
        skeletonStats.put("xpReward", 10);
        skeletonStats.put("texture", AssetLoader.getTexture("skeleton_walk"));
        ENEMY_STATS.put("Skeleton", skeletonStats);

        // Slime stats
        Map<String, Object> slimeStats = new HashMap<>();
        slimeStats.put("hp", 200);
        slimeStats.put("range", 150f);
        slimeStats.put("speedRange", new int[]{350, 600});
        slimeStats.put("dmg", 25);
        slimeStats.put("attackCooldown", 1000L);
        slimeStats.put("points", 10);
        slimeStats.put("xpReward", 5);
        slimeStats.put("texture", AssetLoader.getTexture("slime_walk"));
        ENEMY_STATS.put("Slime", slimeStats);

        // WereBear stats
        Map<String, Object> wereBearStats = new HashMap<>();
        wereBearStats.put("hp", 1500);
        wereBearStats.put("range", 140f);
        wereBearStats.put("speedRange", new int[]{450, 650});
        wereBearStats.put("dmg", 95);
        wereBearStats.put("attackCooldown", 2500L);
        wereBearStats.put("points", 500);
        wereBearStats.put("xpReward", 200);
        wereBearStats.put("texture", AssetLoader.getTexture("werebear_walk"));
        ENEMY_STATS.put("WereBear", wereBearStats);

        // WereWolf stats
        Map<String, Object> wereWolfStats = new HashMap<>();
        wereWolfStats.put("hp", 1500);
        wereWolfStats.put("range", 110f);
        wereWolfStats.put("speedRange", new int[]{300, 600});
        wereWolfStats.put("dmg", 110);
        wereWolfStats.put("attackCooldown", 900L);
        wereWolfStats.put("points", 500);
        wereWolfStats.put("xpReward", 200);
        wereWolfStats.put("texture", AssetLoader.getTexture("werewolf_walk"));
        ENEMY_STATS.put("WereWolf", wereWolfStats);

        // Wizard stats
        Map<String, Object> wizardStats = new HashMap<>();
        wizardStats.put("hp", 90);
        wizardStats.put("range", 150f);
        wizardStats.put("speedRange", new int[]{450, 650});
        wizardStats.put("dmg", 60);
        wizardStats.put("attackCooldown", 1000L);
        wizardStats.put("points", 30);
        wizardStats.put("xpReward", 25);
        wizardStats.put("texture", AssetLoader.getTexture("wizard_walk"));
        ENEMY_STATS.put("Wizard", wizardStats);
    }

    // Weapon stats map
    public static final Map<String, Map<String, Object>> WEAPON_STATS = new HashMap<>();

    static {
        // Mauser stats
        Map<String, Object> mauserStats = new HashMap<>();
        mauserStats.put("name", "Mauser");
        mauserStats.put("texture", AssetLoader.getTexture("mauser_normal"));
        mauserStats.put("damage", 35);
        mauserStats.put("fireRate", 0.275f);
        mauserStats.put("ballSpeed", 3500f);
        WEAPON_STATS.put("mauser", mauserStats);

        // Winchester stats
        Map<String, Object> winchesterStats = new HashMap<>();
        winchesterStats.put("name", "Winchester");
        winchesterStats.put("texture", AssetLoader.getTexture("winchester_normal"));
        winchesterStats.put("damage", 40);
        winchesterStats.put("fireRate", 2f);
        winchesterStats.put("ballSpeed", 3500f);
        WEAPON_STATS.put("winchester", winchesterStats);

        // Vector stats
        Map<String, Object> vectorStats = new HashMap<>();
        vectorStats.put("name", "Vector");
        vectorStats.put("texture", AssetLoader.getTexture("vector_normal"));
        vectorStats.put("damage", 15);
        vectorStats.put("fireRate", 0.15f);
        vectorStats.put("ballSpeed", 5500f);
        WEAPON_STATS.put("vector", vectorStats);

        // BMG stats
        Map<String, Object> bmgStats = new HashMap<>();
        bmgStats.put("name", "BMG");
        bmgStats.put("texture", AssetLoader.getTexture("bmg_normal"));
        bmgStats.put("damage", 200);
        bmgStats.put("fireRate", 2.5f);
        WEAPON_STATS.put("bmg", bmgStats);

        // AR stats
        Map<String, Object> arStats = new HashMap<>();
        arStats.put("name", "AR");
        arStats.put("texture", AssetLoader.getTexture("ar_normal"));
        arStats.put("damage", 45);
        arStats.put("fireRate", 0.25f);
        arStats.put("ballSpeed", 6000f);
        WEAPON_STATS.put("ar", arStats);

        // Upgraded weapons
        Map<String, Object> mauserPapStats = new HashMap<>();
        mauserPapStats.put("name", "Mauser");
        mauserPapStats.put("texture", AssetLoader.getTexture("mauser_pap"));
        mauserPapStats.put("damage", (int) mauserStats.get("damage") * DMG_MULT);
        mauserPapStats.put("fireRate", (float) mauserStats.get("fireRate") * FIRE_RATE_MULT);
        mauserPapStats.put("ballSpeed", (float) mauserStats.get("ballSpeed") * BALL_SPEED_MULT);
        WEAPON_STATS.put("mauser_pap", mauserPapStats);

        Map<String, Object> winchesterPapStats = new HashMap<>();
        winchesterPapStats.put("name", "Winchester");
        winchesterPapStats.put("texture", AssetLoader.getTexture("winchester_pap"));
        winchesterPapStats.put("damage", (int) winchesterStats.get("damage") * DMG_MULT);
        winchesterPapStats.put("fireRate", (float) winchesterStats.get("fireRate") * FIRE_RATE_MULT);
        winchesterPapStats.put("ballSpeed", (float) winchesterStats.get("ballSpeed") * BALL_SPEED_MULT);
        WEAPON_STATS.put("winchester_pap", winchesterPapStats);

        Map<String, Object> bmgPapStats = new HashMap<>();
        bmgPapStats.put("name", "BMG");
        bmgPapStats.put("texture", AssetLoader.getTexture("bmg_pap"));
        bmgPapStats.put("damage", (int) bmgStats.get("damage") * DMG_MULT);
        bmgPapStats.put("fireRate", (float) bmgStats.get("fireRate") * FIRE_RATE_MULT);
        WEAPON_STATS.put("bmg_pap", bmgPapStats);

        Map<String, Object> arPapStats = new HashMap<>();
        arPapStats.put("name", "AR");
        arPapStats.put("texture", AssetLoader.getTexture("ar_pap"));
        arPapStats.put("damage", (int) arStats.get("damage") * DMG_MULT);
        arPapStats.put("fireRate", (float) arStats.get("fireRate") * FIRE_RATE_MULT);
        arPapStats.put("ballSpeed", (float) arStats.get("ballSpeed") * BALL_SPEED_MULT);
        WEAPON_STATS.put("ar_pap", arPapStats);

        Map<String, Object> vectorPapStats = new HashMap<>();
        vectorPapStats.put("name", "Vector");
        vectorPapStats.put("texture", AssetLoader.getTexture("vector_pap"));
        vectorPapStats.put("damage", (int) vectorStats.get("damage") * DMG_MULT);
        vectorPapStats.put("fireRate", (float) vectorStats.get("fireRate") * FIRE_RATE_MULT);
        vectorPapStats.put("ballSpeed", (float) vectorStats.get("ballSpeed") * BALL_SPEED_MULT);
        WEAPON_STATS.put("vector_pap", vectorPapStats);
    }

    // FirstScreen constants
    public static final float FONT_SCALE = 2.5f;
    public static final float XP_BAR_WIDTH = 700;
    public static final float XP_BAR_HEIGHT = 30;
    public static final float XP_BAR_X = 50;
    public static final float XP_BAR_Y = 30;
    public static final float LEVEL_TEXT_OFFSET_X = -20;
    public static final float LEVEL_TEXT_OFFSET_Y = -20;
    public static final float SCORE_TEXT_X = -250;
    public static final float SCORE_TEXT_Y = 20;

    public static final boolean SHOW_HITBOXES = false;
    public static final boolean SHOW_MELEE_HITBOX = false;
}
