package io.github.some_example_name;

import java.util.HashMap;
import java.util.Map;

/**
 * Settings class that contains various constants and configurations for the game.
 */
public class Settings {
    // Spawn constants
    public static final float SPAWN_INTERVAL = 1f;
    public static final float SPAWN_DISTANCE_THRESHOLD = 600;
    public static final float INITIAL_SPAWN_INTERVAL = 4f;
    public static final float ROUND_INTERVAL = 6f;

    // Player constants
    public static final long DASH_DURATION = 500;
    public static final float DASH_MULTIPLIER = 2.75f;
    public static final int MELEE_DAMAGE = 45;
    public static final long MELEE_COOLDOWN = 1000;
    public static final float KNOCKBACK_DISTANCE = 100f;
    public static final float MIN_CURSOR_DISTANCE = 50f;
    public static final int LEVEL_STAT = 20;
    public static final int BASE_HP = 250000;
    public static final float BASE_SPEED = 700f;
    public static final float ATTACK_STATE_TIME = 0.1f;
    public static final float DEATH_ANIMATION_FRAME_DURATION = 0.1f;

    // Enemy constants
    public static final float HURT_SPEED_MULT = 0.75f;
    public static final float KNOCKBACK_DURATION = 0.3f;

    // Map constants
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    public static final int MAP_WIDTH_IN_TILES = 50;
    public static final int MAP_HEIGHT_IN_TILES = 50;
    public static final String MAP_PATH = "assets/map/dungeon.tmx";
    public static final float UNIT_SCALE = 6.0f;

    // Enemy stats map
    public static final Map<String, Map<String, Object>> ENEMY_STATS = new HashMap<>();

    static {
        // Armored Axeman stats
        Map<String, Object> armoredAxemanStats = new HashMap<>();
        armoredAxemanStats.put("hp", 200);
        armoredAxemanStats.put("range", 80f);
        armoredAxemanStats.put("speedRange", new int[]{350, 550});
        armoredAxemanStats.put("dmg", 45);
        armoredAxemanStats.put("attackCooldown", 1500L);
        armoredAxemanStats.put("points", 25);
        armoredAxemanStats.put("xpReward", 15);
        armoredAxemanStats.put("texture", AssetLoader.getTexture("armored_axeman_walk"));
        ENEMY_STATS.put("ArmoredAxeman", armoredAxemanStats);

        // Armored Orc stats
        Map<String, Object> armoredOrcStats = new HashMap<>();
        armoredOrcStats.put("hp", 190);
        armoredOrcStats.put("range", 90f);
        armoredOrcStats.put("speedRange", new int[]{400, 550});
        armoredOrcStats.put("dmg", 40);
        armoredOrcStats.put("attackCooldown", 1300L);
        armoredOrcStats.put("points", 25);
        armoredOrcStats.put("xpReward", 15);
        armoredOrcStats.put("texture", AssetLoader.getTexture("armored_orc_walk"));
        ENEMY_STATS.put("ArmoredOrc", armoredOrcStats);

        // Armored Skeleton stats
        Map<String, Object> armoredSkeletonStats = new HashMap<>();
        armoredSkeletonStats.put("hp", 175);
        armoredSkeletonStats.put("range", 120f);
        armoredSkeletonStats.put("speedRange", new int[]{400, 550});
        armoredSkeletonStats.put("dmg", 55);
        armoredSkeletonStats.put("attackCooldown", 1000L);
        armoredSkeletonStats.put("points", 25);
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
        greatSwordSkeletonStats.put("hp", 100);
        greatSwordSkeletonStats.put("range", 150f);
        greatSwordSkeletonStats.put("speedRange", new int[]{350, 500});
        greatSwordSkeletonStats.put("dmg", 75);
        greatSwordSkeletonStats.put("attackCooldown", 1500L);
        greatSwordSkeletonStats.put("points", 30);
        greatSwordSkeletonStats.put("xpReward", 25);
        greatSwordSkeletonStats.put("texture", AssetLoader.getTexture("great_sword_skeleton_walk"));
        ENEMY_STATS.put("GreatSwordSkeleton", greatSwordSkeletonStats);

        // Lancer stats
        Map<String, Object> lancerStats = new HashMap<>();
        lancerStats.put("hp", 1500);
        lancerStats.put("range", 80f);
        lancerStats.put("speedRange", new int[]{500, 750});
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
        orcStats.put("speedRange", new int[]{350, 550});
        orcStats.put("dmg", 40);
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
        slimeStats.put("range", 120f);
        slimeStats.put("speedRange", new int[]{350, 550});
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
        wereWolfStats.put("speedRange", new int[]{300, 500});
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
        mauserStats.put("damage", 30);
        mauserStats.put("fireRate", 0.275f);
        mauserStats.put("ballSpeed", 3500f);
        WEAPON_STATS.put("mauser", mauserStats);

        // Winchester stats
        Map<String, Object> winchesterStats = new HashMap<>();
        winchesterStats.put("name", "Winchester");
        winchesterStats.put("texture", AssetLoader.getTexture("winchester_normal"));
        winchesterStats.put("damage", 40);
        winchesterStats.put("fireRate", 1.3f);
        winchesterStats.put("ballSpeed", 3500f);
        WEAPON_STATS.put("winchester", winchesterStats);

        // Vector stats
        Map<String, Object> vectorStats = new HashMap<>();
        vectorStats.put("name", "Vector");
        vectorStats.put("texture", AssetLoader.getTexture("vector_normal"));
        vectorStats.put("damage", 15);
        vectorStats.put("fireRate", 0.1f);
        vectorStats.put("ballSpeed", 5500f);
        WEAPON_STATS.put("vector", vectorStats);

        // BMG stats
        Map<String, Object> bmgStats = new HashMap<>();
        bmgStats.put("name", "BMG");
        bmgStats.put("texture", AssetLoader.getTexture("bmg_normal"));
        bmgStats.put("damage", 150);
        bmgStats.put("fireRate", 2f);
        WEAPON_STATS.put("bmg", bmgStats);

        // AR stats
        Map<String, Object> arStats = new HashMap<>();
        arStats.put("name", "AR");
        arStats.put("texture", AssetLoader.getTexture("ar_normal"));
        arStats.put("damage", 32);
        arStats.put("fireRate", 0.35f);
        arStats.put("ballSpeed", 6000f);
        WEAPON_STATS.put("ar", arStats);

        // Upgraded weapons
        Map<String, Object> mauserPapStats = new HashMap<>();
        mauserPapStats.put("name", "Mauser");
        mauserPapStats.put("texture", AssetLoader.getTexture("mauser_pap"));
        mauserPapStats.put("damage", 45);
        mauserPapStats.put("fireRate", 0.25f);
        mauserPapStats.put("ballSpeed", 4000f);
        WEAPON_STATS.put("mauser_pap", mauserPapStats);

        Map<String, Object> winchesterPapStats = new HashMap<>();
        winchesterPapStats.put("name", "Winchester");
        winchesterPapStats.put("texture", AssetLoader.getTexture("winchester_pap"));
        winchesterPapStats.put("damage", 60);
        winchesterPapStats.put("fireRate", 0.9f);
        winchesterPapStats.put("ballSpeed", 3500f);
        WEAPON_STATS.put("winchester_pap", winchesterPapStats);

        Map<String, Object> bmgPapStats = new HashMap<>();
        bmgPapStats.put("name", "BMG");
        bmgPapStats.put("texture", AssetLoader.getTexture("bmg_pap"));
        bmgPapStats.put("damage", 150);
        bmgPapStats.put("fireRate", 1.2f);
        bmgPapStats.put("ballSpeed", 5500f);
        WEAPON_STATS.put("bmg_pap", bmgPapStats);

        Map<String, Object> arPapStats = new HashMap<>();
        arPapStats.put("name", "AR");
        arPapStats.put("texture", AssetLoader.getTexture("ar_pap"));
        arPapStats.put("damage", 50);
        arPapStats.put("fireRate", 0.1f);
        arPapStats.put("ballSpeed", 5000f);
        WEAPON_STATS.put("ar_pap", arPapStats);

        Map<String, Object> vectorPapStats = new HashMap<>();
        vectorPapStats.put("name", "Vector");
        vectorPapStats.put("texture", AssetLoader.getTexture("vector_pap"));
        vectorPapStats.put("damage", 35);
        vectorPapStats.put("fireRate", 0.06f);
        vectorPapStats.put("ballSpeed", 7000f);
        WEAPON_STATS.put("vector_pap", vectorPapStats);
    }

    // FirstScreen constants
    public static final String BACKGROUND_MUSIC_PATH = "assets/sounds/game/blindpick.mp3";
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
