package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.character.enemies.*;
import io.github.some_example_name.character.enemies.bosses.Lancer;
import io.github.some_example_name.character.enemies.bosses.OrcRider;
import io.github.some_example_name.character.enemies.bosses.WereBear;
import io.github.some_example_name.character.enemies.bosses.WereWolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class Spawn {
    private List<Enemy> enemies;
    private List<MapObject> enemySpawns;
    private float unitScale;
    private Player player;
    private Random random;
    private float spawnTimer;
    private static final float SPAWN_INTERVAL = Settings.SPAWN_INTERVAL;
    private static final float SPAWN_DISTANCE_THRESHOLD = Settings.SPAWN_DISTANCE_THRESHOLD;
    private int currentRound;
    private int enemiesToSpawn;
    private float roundCountdown;
    private boolean roundInProgress;
    private static final float ROUND_INTERVAL = Settings.ROUND_INTERVAL;
    private int totalEnemiesToSpawn;

    public Spawn(TiledMap tiledMap, float unitScale) {
        this.enemies = new ArrayList<>();
        this.unitScale = unitScale;
        this.random = new Random();
        this.spawnTimer = 0;
        this.currentRound = 0; // Start with round 0 to trigger the countdown for round 1
        this.enemiesToSpawn = 0; // No enemies to spawn initially
        this.roundCountdown = ROUND_INTERVAL;
        this.roundInProgress = false;
        this.totalEnemiesToSpawn = 0; // Initialize total enemies to spawn

        // Initialize player
        this.player = createPlayer(tiledMap, unitScale);

        // Initialize enemy spawns
        this.enemySpawns = new ArrayList<>();
        for (MapObject enemySpawn : tiledMap.getLayers().get("spawns").getObjects()) {
            if (enemySpawn instanceof RectangleMapObject && !enemySpawn.getName().equals("player")) {
                this.enemySpawns.add(enemySpawn);
            }
        }
    }

    public  Player createPlayer(TiledMap tiledMap, float unitScale) {
        // Find the player spawn point
        MapObject playerSpawn = tiledMap.getLayers().get("spawns").getObjects().get("player");
        if (playerSpawn instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) playerSpawn).getRectangle();
            float playerX = rect.x * unitScale;
            float playerY = rect.y * unitScale;
            return new Player(AssetLoader.getTexture("player_walk"), AssetLoader.getTexture("player_melee"), playerX, playerY, getEnemies());
        }
        return null;
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void update(float delta) {
        if (roundInProgress) {
            updateEnemies(delta);
            spawnNewEnemies(delta);
            if (enemies.isEmpty() && enemiesToSpawn == 0) {
                roundInProgress = false;
                roundCountdown = ROUND_INTERVAL;
            }
        } else {
            roundCountdown -= delta;
            if (roundCountdown <= 0) {
                startNextRound();
                player.resetRoundKillCount();
            }
        }
    }

    private void updateEnemies(float delta) {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.update(delta);
            if (enemy.isDead() && enemy.isAnimationFinished()) {
                enemyIterator.remove();
            }
        }
    }

    private void startNextRound() {
        currentRound++;
        totalEnemiesToSpawn = enemiesToSpawn = 10 + (currentRound - 1) * 5;
        spawnTimer = 0;
        roundInProgress = true;

        // Spawn a boss every 3 rounds
        if (currentRound % 3 == 0) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        if (!enemySpawns.isEmpty()) {
            MapObject randomSpawn;
            Rectangle rect;
            float bossX, bossY;
            do {
                randomSpawn = getRandomSpawn();
                rect = ((RectangleMapObject) randomSpawn).getRectangle();
                bossX = rect.x * unitScale;
                bossY = rect.y * unitScale;
            } while (!isFarFromPlayer(bossX, bossY));

            enemies.add(createRandomBoss(bossX, bossY));
        }
    }

    private Enemy createRandomBoss(float bossX, float bossY) {
        int bossType = random.nextInt(4); // 4 bosses
        switch (bossType) {
            case 0:
                return new Lancer(bossX, bossY, player, AssetLoader.getTexture("lancer_walk"), AssetLoader.getTexture("lancer_attack"), AssetLoader.getTexture("lancer_hurt"), AssetLoader.getTexture("lancer_death"));
            case 1:
                return new OrcRider(bossX, bossY, player, AssetLoader.getTexture("orc_rider_walk"), AssetLoader.getTexture("orc_rider_attack"), AssetLoader.getTexture("orc_rider_hurt"), AssetLoader.getTexture("orc_rider_death"));
            case 2:
                return new WereBear(bossX, bossY, player, AssetLoader.getTexture("werebear_walk"), AssetLoader.getTexture("werebear_attack"), AssetLoader.getTexture("werebear_hurt"), AssetLoader.getTexture("werebear_death"));
            case 3:
                return new WereWolf(bossX, bossY, player, AssetLoader.getTexture("werewolf_walk"), AssetLoader.getTexture("werewolf_attack"), AssetLoader.getTexture("werewolf_hurt"), AssetLoader.getTexture("werewolf_death"));
            default:
                throw new IllegalArgumentException("Unknown boss type: " + bossType);
        }
    }

    private void spawnNewEnemies(float delta) {
        if (enemiesToSpawn > 0) {
            spawnTimer += delta;
            if (spawnTimer >= SPAWN_INTERVAL) {
                spawnTimer = 0;
                if (!enemySpawns.isEmpty()) {
                    MapObject randomSpawn = getRandomSpawn();
                    Rectangle rect = ((RectangleMapObject) randomSpawn).getRectangle();
                    float enemyX = rect.x * unitScale;
                    float enemyY = rect.y * unitScale;

                    if (isFarFromPlayer(enemyX, enemyY)) {
                        enemies.add(createRandomEnemy(enemyX, enemyY));
                        enemiesToSpawn--;
                    }
                }
            }
        }
    }

    private MapObject getRandomSpawn() {
        int randomIndex = random.nextInt(enemySpawns.size());
        return enemySpawns.get(randomIndex);
    }

    private boolean isFarFromPlayer(float enemyX, float enemyY) {
        float distanceToPlayer = calculateDistance(player.getX(), player.getY(), enemyX, enemyY);
        return distanceToPlayer >= SPAWN_DISTANCE_THRESHOLD;
    }

    private Enemy createRandomEnemy(float enemyX, float enemyY) {
        int enemyType = random.nextInt(currentRound == 1 ? 2 : 9);
        switch (enemyType) {
            case 0:
                return new ArmoredSkeleton(enemyX, enemyY, player, AssetLoader.getTexture("armored_skeleton_walk"), AssetLoader.getTexture("armored_skeleton_attack"), AssetLoader.getTexture("armored_skeleton_hurt"), AssetLoader.getTexture("armored_skeleton_death"));
            case 1:
                return new ArmoredOrc(enemyX, enemyY, player, AssetLoader.getTexture("armored_orc_walk"), AssetLoader.getTexture("armored_orc_attack"), AssetLoader.getTexture("armored_orc_hurt"), AssetLoader.getTexture("armored_orc_death"));
            case 2:
                return new ArmoredAxeman(enemyX, enemyY, player, AssetLoader.getTexture("armored_axeman_walk"), AssetLoader.getTexture("armored_axeman_attack"), AssetLoader.getTexture("armored_axeman_hurt"), AssetLoader.getTexture("armored_axeman_death"));
            case 3:
                return new EliteOrc(enemyX, enemyY, player, AssetLoader.getTexture("elite_orc_walk"), AssetLoader.getTexture("elite_orc_attack"), AssetLoader.getTexture("elite_orc_hurt"), AssetLoader.getTexture("elite_orc_death"));
            case 4:
                return new GreatSwordSkeleton(enemyX, enemyY, player, AssetLoader.getTexture("great_sword_skeleton_walk"), AssetLoader.getTexture("great_sword_skeleton_attack"), AssetLoader.getTexture("great_sword_skeleton_hurt"), AssetLoader.getTexture("great_sword_skeleton_death"));
            case 5:
                return new Orc(enemyX, enemyY, player, AssetLoader.getTexture("orc_walk"), AssetLoader.getTexture("orc_attack"), AssetLoader.getTexture("orc_hurt"), AssetLoader.getTexture("orc_death"));
            case 6:
                return new Skeleton(enemyX, enemyY, player, AssetLoader.getTexture("skeleton_walk"), AssetLoader.getTexture("skeleton_attack"), AssetLoader.getTexture("skeleton_hurt"), AssetLoader.getTexture("skeleton_death"));
            case 7:
                return new Slime(enemyX, enemyY, player, AssetLoader.getTexture("slime_walk"), AssetLoader.getTexture("slime_attack"), AssetLoader.getTexture("slime_hurt"), AssetLoader.getTexture("slime_death"));
            case 8:
                return new Wizard(enemyX, enemyY, player, AssetLoader.getTexture("wizard_walk"), AssetLoader.getTexture("wizard_attack"), AssetLoader.getTexture("wizard_hurt"), AssetLoader.getTexture("wizard_death"));
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getEnemyCount() {
        return enemies.size();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public float getRoundCountdown() {
        return roundCountdown;
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }

    public int getTotalEnemiesToSpawn() {
        return totalEnemiesToSpawn;
    }

    public void dispose() {
        // Dispose of player resources
        if (player != null) {
            player.dispose();
        }

        // Dispose of enemy resources
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }

        // Clear the lists
        enemies.clear();
        enemySpawns.clear();
    }
}