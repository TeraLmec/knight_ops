package io.github.some_example_name.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Intersector;
import io.github.some_example_name.FirstScreen;
import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.bullets.PapAmmo;
import io.github.some_example_name.weapon.bullets.PistolAmmo;
import io.github.some_example_name.weapon.bullets.ShotgunAmmo;
import io.github.some_example_name.weapon.range.Bmg;
import io.github.some_example_name.weapon.range.Winchester;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.AssetLoader;
import java.util.Random;
import io.github.some_example_name.Settings;

public abstract class Weapon extends Sprite {
    private Player player;
    private String name;
    private int damage;
    private Texture weaponTexture;
    private float stateTime = 0f;
    private Vector2 position;
    private Vector2 direction;
    private boolean shooting = false;
    private List<Ball> balls = new ArrayList<>();
    private float fireRate;
    private float timeSinceLastShot = 0f;
    private Vector2 playerCenter = new Vector2();
    private Vector3 mousePosition = new Vector3();
    private List<Enemy> enemies;
    private List<Explosion> explosions = new ArrayList<>();
    private Sound shootSound;
    private float volume;
    private float ballSpeed;
    private boolean pap = false;
    private Random random = new Random();
    private int weaponCost;

    public Weapon(Player player, List<Enemy> enemies, String name, Texture spriteTexture, int damage, float fireRate, float ballSpeed, String shootSoundKey, int weaponCost) {
        super(spriteTexture);
        this.player = player;
        this.name = name;
        this.damage = damage;
        this.weaponTexture = spriteTexture; // Change this line
        this.position = new Vector2(player.getX(), player.getY());
        this.direction = new Vector2();
        this.enemies = enemies;
        this.shootSound = AssetLoader.getSound(shootSoundKey); // Change this line
        this.volume = Settings.SHOOT_VOLUME; // Use volume from Settings
        this.fireRate = fireRate;
        this.ballSpeed = ballSpeed;
        this.weaponCost = weaponCost;
    }

    public void update(float delta, OrthographicCamera camera, List<Enemy> enemies) {
        updatePlayerCenter();
        updateMousePosition(camera);
        updateDirection();
        updatePosition();
        updateBalls(delta);
        updateExplosions(delta);
        timeSinceLastShot += delta;
        this.enemies = enemies;
    }

    public boolean isPap() {
        return pap;
    }
    public void setPap(boolean pap) {
        this.pap = pap;
        /* if (pap) {
            updateShootSound();
        } */
    }

    /* private void updateShootSound() {
        String papShootSoundKey = getPapShootSoundKey();
        if (papShootSoundKey != null) {
            shootSound.dispose();
            shootSound = AssetLoader.getSound(papShootSoundKey);
        }
    } */

    protected abstract String getPapShootSoundKey();

    private void updatePlayerCenter() {
        playerCenter.set(player.getX(), player.getY());
    }

    private void updateMousePosition(OrthographicCamera camera) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
    }

    private void updateDirection() {
        direction.set(mousePosition.x, mousePosition.y).sub(playerCenter).nor();
    }

    private void updatePosition() {
        position.set(playerCenter.add(direction.scl(75)));
    }

    public void updateBalls(float delta) {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.update(delta, enemies, iterator);
            ball.checkCollisions(enemies, iterator, damage, explosions);
            if (ball.isOutOfBounds(FirstScreen.getTiledMapWidth(), FirstScreen.getTiledMapHeight())) {
                iterator.remove();
            }
        }
    } 

    private void updateExplosions(float delta) {
        Iterator<Explosion> iterator = explosions.iterator();
        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();
            explosion.update(delta);
            if (explosion.isFinished()) {
                iterator.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Ball ball : balls) {
            ball.render(batch);
        }
    
        TextureRegion currentFrame = new TextureRegion(getTexture()); // Use getTexture() to get the updated texture
        boolean flipY = direction.x < 0;
        flipFrameIfNeeded(currentFrame, flipY);
    
        float aspectRatio = (float) currentFrame.getRegionWidth() / currentFrame.getRegionHeight();
        float width = 650 * aspectRatio;
        float height = 650;
        float x = position.x - width / 2;
        float y = position.y - (height / 2) - 20;
    
        float rotation = direction.angleDeg();
    
        batch.draw(currentFrame, x, y, width / 2, height / 2, width, height, 1, 1, rotation);
    
        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }
    }

    private void flipFrameIfNeeded(TextureRegion frame, boolean flipY) {
        if ((mousePosition.x < player.getX() && !frame.isFlipY()) || (mousePosition.x >= player.getX() && frame.isFlipY())) {
            frame.flip(false, true);
        }
    }

    private Vector2 calculateBallSpawnPosition(Vector2 playerCenter, Vector2 direction) {
        float rotation = direction.angleRad();
        float offsetX = (float) Math.cos(rotation) * 50;
        float offsetY = (float) Math.sin(rotation) * 50 - 20;
        return new Vector2(playerCenter.x + offsetX, playerCenter.y + offsetY);
    }

    protected void hitscan() {
        Vector2 start = new Vector2(playerCenter);
        Vector2 end = new Vector2(start).add(direction.scl(1000)); // Extend the line to a large distance

        for (Enemy enemy : enemies) {
            if (enemy.getBoundingRectangle().contains(start) || enemy.getBoundingRectangle().contains(end) ||
                Intersector.intersectSegmentRectangle(start, end, enemy.getBoundingRectangle())) {
                enemy.takeDamage(damage);
            }
        }
    }

    public void shoot(Vector2 playerCenter, Vector2 direction) {
        if (isPap()) {
            handlePapShoot(playerCenter, direction);
        } else {
            handleNormalShoot(playerCenter, direction);
        }
        float pitch = Settings.MIN_PITCH + random.nextFloat() * (Settings.MAX_PITCH - Settings.MIN_PITCH);
        shootSound.play(volume, pitch, 0);
    }

    private void handlePapShoot(Vector2 playerCenter, Vector2 direction) {
        if (this instanceof Winchester) {
            shootWithSpread(playerCenter, direction, PapAmmo::new);
        } else if (this instanceof Bmg) {
            ((Bmg) this).startHitscan(playerCenter, direction);
        } else {
            shootSingleBullet(playerCenter, direction, PapAmmo::new);
        }
    }

    private void handleNormalShoot(Vector2 playerCenter, Vector2 direction) {
        if (this instanceof Winchester) {
            shootWithSpread(playerCenter, direction, ShotgunAmmo::new);
        } else if (this instanceof Bmg) {
            ((Bmg) this).startHitscan(playerCenter, direction);
        } else {
            shootSingleBullet(playerCenter, direction, PistolAmmo::new);
        }
    }

    private void shootWithSpread(Vector2 playerCenter, Vector2 direction, BallFactory factory) {
        float spreadAngle = 40f;
        int bulletCount = 7;
        for (int i = 0; i < bulletCount; i++) {
            float angle = direction.angleDeg() - spreadAngle / 2 + (spreadAngle / (bulletCount - 1)) * i;
            Vector2 bulletDirection = new Vector2(direction).setAngleDeg(angle);
            Vector2 ballSpawnPosition = calculateBallSpawnPosition(playerCenter, bulletDirection);
            balls.add(factory.create(ballSpawnPosition, bulletDirection, ballSpeed));
        }
    }

    private void shootSingleBullet(Vector2 playerCenter, Vector2 direction, BallFactory factory) {
        Vector2 ballSpawnPosition = calculateBallSpawnPosition(playerCenter, direction);
        balls.add(factory.create(ballSpawnPosition, direction, ballSpeed));
    }

    @FunctionalInterface
    private interface BallFactory {
        Ball create(Vector2 position, Vector2 direction, float speed);
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getBallSpeed() {
        return ballSpeed;
    }

    public float getVolume() {
        return volume;
    }

    public float getFireRate() {
        return fireRate;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public Sound getShootSound() {
        return shootSound;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public void setBallSpeed(float ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public int getWeaponCost() {
        return weaponCost;
    }

    public void dispose() {
        // Dispose of the weapon texture
        weaponTexture.dispose(); // Change this line
        shootSound.dispose(); // Dispose of the sound
    }
}