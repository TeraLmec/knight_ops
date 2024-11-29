package io.github.some_example_name.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.audio.Sound;
import io.github.some_example_name.ScoreManager;
import io.github.some_example_name.Settings;
import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.FirstScreen;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.weapon.range.Ar;
import io.github.some_example_name.weapon.range.Bmg;
import io.github.some_example_name.weapon.range.Mauser;
import io.github.some_example_name.weapon.range.Vector;
import io.github.some_example_name.weapon.range.Winchester;
import io.github.some_example_name.XpManager;
import java.util.List;

public class Player extends Character implements InputProcessor {
    // Player state variables
    private boolean dashing = false;
    private float dashMultiplier = 1;
    private long dashStartTime;
    private long dashDuration = Settings.DASH_DURATION;
    private int meleeDamage = Settings.MELEE_DAMAGE;
    private boolean isDead = false;
    private long deathTime = 0;
    private boolean attacking = false;
    private float attackStateTime = Settings.ATTACK_STATE_TIME;
    private boolean damageDealt = false;
    private long meleeCooldown = Settings.MELEE_COOLDOWN;
    private long lastMeleeAttackTime = 0;
    private float knockbackDistance = Settings.KNOCKBACK_DISTANCE;
    private boolean meleeTouched = false;
    private float timeSinceLastShot = 0f;
    private static final float MIN_CURSOR_DISTANCE = Settings.MIN_CURSOR_DISTANCE;
    private static float speedShootMultiplier = 1f;

    // Player position and direction
    private Vector2 position;
    private Vector2 direction;
    private Vector2 playerCenter = new Vector2();
    private Vector3 mousePosition = new Vector3();

    // Player weapons
    private Weapon primaryWeapon = createWeapon("mauser_normal");
    private Weapon secondaryWeapon = null;
    private Weapon currentWeapon;

    // Animation and rendering
    private Animation<TextureRegion> meleeAnimation;
    private Animation<TextureRegion> deathAnimation; // Ajoutez cette ligne
    private ShapeRenderer shapeRenderer = new ShapeRenderer(); 
    private Rectangle meleeHitbox = new Rectangle();
    private float hitboxRotation = 0;
    private float width;
    private float height;

    // Enemies
    private List<Enemy> enemies;

    // Melee sounds
    private Sound meleeNormalSound;
    private Sound meleeTouchedSound;
    private XpManager xpManager;
    private int killCount = 0;
    private int roundKillCount = 0;

    // Base stats
    private float baseSpeed = Settings.BASE_SPEED;
    private int baseMeleeDamage = Settings.MELEE_DAMAGE;

    private float speedIncreasePercentage = 0;
    private float meleeDamageIncreasePercentage = 0;

    public Player(Texture walkSheet, Texture meleeSheet, float x, float y, List<Enemy> enemies) {
        super(8, x, y, Settings.BASE_HP, Settings.BASE_SPEED, walkSheet);
        Gdx.input.setInputProcessor(this);
        this.enemies = enemies;
        currentWeapon = primaryWeapon;
        System.out.println("Primary weapon: " + primaryWeapon.getName());
        this.meleeAnimation = sheetSplitter(meleeSheet, 2, Settings.ATTACK_STATE_TIME);
        this.deathAnimation = sheetSplitter(AssetLoader.getTexture("player_death"), 4, Settings.DEATH_ANIMATION_FRAME_DURATION); // Initialisez l'animation de mort
        this.position = new Vector2(getX(), getY());
        this.direction = new Vector2();
        updateDimensions();
        meleeNormalSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/melee_normal_sound.mp3"));
        meleeTouchedSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/melee_touched_sound.mp3"));
        this.xpManager = new XpManager(this);
        this.baseSpeed = this.speed;
        this.baseMeleeDamage = this.meleeDamage;
        System.out.println("Initial HP: " + this.hp + ", Vitesse: " + this.speed);
    }

    private void updateDimensions() {
        float aspectRatio = (float) meleeAnimation.getKeyFrame(0).getRegionWidth() / meleeAnimation.getKeyFrame(0).getRegionHeight();
        width = getWidth() * getScale() * 1.75f;
        height = width / aspectRatio;
        System.out.println("HP: " + this.hp + ", Vitesse: " + this.speed);
    }

    @Override
    public void attack() {
        // Implement attack logic
    }


    public void gainXp(int xp) {
        xpManager.addXp(xp);
    }

    public void update(float delta, OrthographicCamera camera) {
        super.update(delta);
        currentWeapon.update(delta, camera, enemies);
        move(delta);
        updateAnimation(delta);
        handleAttack(delta);
        updatePlayerCenter();
        updatePosition();
        updateMousePosition(camera);
        updateDirection();
        handleWeaponSwitch();
        updateMeleeHitbox();
        timeSinceLastShot += delta;
        shoot(camera);
        if (currentWeapon instanceof Bmg) {
            ((Bmg) currentWeapon).updateHitscan(delta);
        }
    }

    private void move(float deltaTime) {
        float moveX = getMoveX();
        float moveY = getMoveY();
        setWalking(moveX != 0 || moveY != 0);
        handleDash();
        setX(getX() + moveX * getSpeed() * dashMultiplier * speedShootMultiplier * deltaTime);
        setY(getY() + moveY * getSpeed() * dashMultiplier * speedShootMultiplier * deltaTime);
    }

    @Override
    public void takeDamage(int damage) {
        if (isDead) {
            return;
        }
        super.takeDamage(damage);
        System.out.println("Player HP after taking damage: " + this.hp);
        checkIfDead();
    }
    
    private void checkIfDead() {
        if (this.hp <= 0) {

            die();
        }
    }
    
    @Override
    public void die() {
        if (!isDead) {
            isDead = true;
            deathTime = TimeUtils.millis();
            System.out.println("Player is dead");
            setStateTime(0); // Reset state time for death animation
        }
    }

    private float getMoveX() {
        return (Gdx.input.isKeyPressed(Keys.A) ? -1 : 0) + (Gdx.input.isKeyPressed(Keys.D) ? 1 : 0);
    }

    private float getMoveY() {
        return (Gdx.input.isKeyPressed(Keys.W) ? 1 : 0) + (Gdx.input.isKeyPressed(Keys.S) ? -1 : 0);
    }

    private void handleDash() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !dashing) {
            startDash();
        }
        if (dashing) {
            updateDashState();
        }
    }

    private void startDash() {
        dashStartTime = TimeUtils.millis();
        dashing = true;
        dashMultiplier = 2.5f;
    }

    private void updateDashState() {
        long elapsedTime = TimeUtils.timeSinceMillis(dashStartTime);
        if (elapsedTime > dashDuration) {
            endDash();
        } else {
            interpolateDashMultiplier(elapsedTime);
        }
    }

    private void endDash() {
        dashing = false;
        dashMultiplier = 1;
    }

    private void interpolateDashMultiplier(long elapsedTime) {
        float progress = (float) elapsedTime / dashDuration;
        dashMultiplier = 2 - progress; // Gradually reduce from 2 to 1
    }

    private void handleAttack(float deltaTime) {
        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.RIGHT) && canAttack()) {
            attacking = true;
            attackStateTime = 0;
            damageDealt = false;
            lastMeleeAttackTime = TimeUtils.millis(); // Update the last attack time
            if (!damageDealt) {
                dealMeleeDamage();
                damageDealt = true;
            }
            playMeleeSound();
        }
        if (attacking) {
            attackStateTime += deltaTime;
            if (meleeAnimation.isAnimationFinished(attackStateTime)) {
                attacking = false;
            }
        }
    }

    private boolean canAttack() {
        return TimeUtils.timeSinceMillis(lastMeleeAttackTime) >= meleeCooldown;
    }

    private void updateMeleeHitbox() {
        float hitboxSize = meleeAnimation.getKeyFrame(0).getRegionWidth() * 2.5f;
        float distanceFromPlayer = hitboxSize / 3;
        hitboxRotation = direction.angleDeg();
        float hitboxX = position.x + direction.x * distanceFromPlayer - hitboxSize / 2;
        float hitboxY = position.y + direction.y * distanceFromPlayer - hitboxSize / 2;
        meleeHitbox.set(hitboxX, hitboxY, hitboxSize, hitboxSize);
    }

    private void dealMeleeDamage() {
        for (Enemy enemy : enemies) {
            if (meleeHitbox.overlaps(enemy.getBoundingRectangle())) {
                meleeTouched = true;
                enemy.takeDamage(meleeDamage);
                applyKnockback(enemy);
                ScoreManager.getInstance().addPoints(15);
                if (enemy.isDead()) {
                    addMeleeKillPoints(enemy);
                }
            }
        }
    }

    private void playMeleeSound() {
        if (meleeTouched) {
            meleeTouchedSound.play(0.4f);
        } else {
            meleeNormalSound.play(0.4f);
        }
        meleeTouched = false;
    }

    private void applyKnockback(Enemy enemy) {
        Vector2 knockbackDirection = new Vector2(enemy.getX() - position.x, enemy.getY() - position.y).nor();
        Vector2 knockbackVelocity = knockbackDirection.scl(knockbackDistance);
        enemy.setKnockbackVelocity(knockbackVelocity);
    }

    private void addMeleeKillPoints(Enemy enemy) {
        int points = enemy.getPoints();
        ScoreManager.getInstance().addPoints(points * 2);
    }

    private void updatePlayerCenter() {
        playerCenter.set(getX(), getY());
    }

    private void updateMousePosition(OrthographicCamera camera) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);
    }

    private void updateDirection() {
        direction.set(mousePosition.x, mousePosition.y).sub(playerCenter).nor();
        if (playerCenter.dst(mousePosition.x, mousePosition.y) < MIN_CURSOR_DISTANCE) {
            direction.set(1, 0); // Default direction if too close
        }
    }

    private void updatePosition() {
        position.set(playerCenter.add(direction.scl(75)));
    }

    public void render(SpriteBatch batch) {
        if (isDead) {
            TextureRegion currentFrame = deathAnimation.getKeyFrame(getStateTime(), false);
            if (deathAnimation.isAnimationFinished(getStateTime()) && TimeUtils.timeSinceMillis(deathTime) > 500) {
                FirstScreen.setGameOver(true);
            }
            float aspectRatio = (float) currentFrame.getRegionWidth() / currentFrame.getRegionHeight();
            float width = getWidth() * getScale();
            float height = width / aspectRatio;
            batch.draw(currentFrame, getX() - width / 2, getY() - height / 2, width, height);
        } else {
            TextureRegion frame = getCurrentFrame();
            if (frame != null) {
                flipFrameIfNeeded(frame);
            }
            currentWeapon.render(batch);
            super.render(batch);
            if (attacking) {
                renderMeleeAnimation(batch);
                batch.end(); // End the SpriteBatch before starting ShapeRenderer
                renderMeleeHitbox(batch.getProjectionMatrix());
                batch.begin(); // Begin the SpriteBatch again after ShapeRenderer is done
            }
            if (currentWeapon instanceof Bmg) {
                batch.end(); // End the SpriteBatch before starting ShapeRenderer
                ((Bmg) currentWeapon).renderHitscanLine(batch.getProjectionMatrix());
                batch.begin(); // Begin the SpriteBatch again after ShapeRenderer is done
            }
        }
    }

    private void renderMeleeHitbox(Matrix4 projectionMatrix) {
        shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(meleeHitbox.x, meleeHitbox.y, meleeHitbox.width / 2, meleeHitbox.height / 2, meleeHitbox.width, meleeHitbox.height, 1, 1, hitboxRotation);
        shapeRenderer.end();
    }


    public void shoot(OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            speedShootMultiplier = 0.4f;
            currentWeapon.setPosition(playerCenter.add(direction.scl(0.4f)));
            if (timeSinceLastShot >= currentWeapon.getFireRate()) {
                timeSinceLastShot = 0f;
                currentWeapon.shoot(playerCenter, direction);
            }
        } else {
            speedShootMultiplier = 1f;
        }
    }

    private void flipFrameIfNeeded(TextureRegion currentFrame) {
        if ((getMoveX() < 0  && !getCurrentFrame().isFlipX()) || (getMoveX() > 0 && getCurrentFrame().isFlipX())) {
            currentFrame.flip(true, false);
        }
    }

    private void renderMeleeAnimation(SpriteBatch batch) {
        TextureRegion currentFrame = meleeAnimation.getKeyFrame(attackStateTime, false);
        if ((mousePosition.x < getX() && !currentFrame.isFlipY()) || (mousePosition.x >= getX() && currentFrame.isFlipY())) {
            currentFrame.flip(false, true);
        }
        float x = position.x - width / 2;
        float y = position.y - (height / 2) - 20;
        float rotation = direction.angleDeg();
        batch.draw(currentFrame, x, y, width / 2, height / 2, width, height, 1, 1, rotation);
        if (currentWeapon != null) {
            currentWeapon.render(batch);
        }
    }

    public void addWeapon(Weapon newWeapon) {
        if (secondaryWeapon == null) {
            secondaryWeapon = newWeapon;
            currentWeapon = secondaryWeapon;
        } else {
            if (currentWeapon.equals(primaryWeapon)) {
                primaryWeapon = newWeapon;
            } else {
                secondaryWeapon = newWeapon;
            }
            currentWeapon = newWeapon;
        }
        System.out.println("Added weapon: " + newWeapon.getName());
    }

    public void upgradeWeapon(Weapon weapon) {
        String weaponName = weapon.getName().toLowerCase();
        weapon.setTexture(AssetLoader.getTexture(weaponName + "_pap"));
        weapon.setDamage((int) Settings.WEAPON_STATS.get(weaponName + "_pap").get("damage"));
        weapon.setFireRate((float) Settings.WEAPON_STATS.get(weaponName + "_pap").get("fireRate"));
        weapon.setBallSpeed((float) Settings.WEAPON_STATS.get(weaponName + "_pap").get("ballSpeed"));
    }

    private Weapon createWeapon(String weaponName) {
        switch (weaponName.toLowerCase()) {
            case "50bmg_normal":
                return new Bmg(this, enemies);
            case "winchester_normal":
                return new Winchester(this, enemies);
            case "ar_normal":
                return new Ar(this, enemies);
            case "mauser_normal":
                return new Mauser(this, enemies);
            case "vector_normal":
                return new Vector(this, enemies);
            default:
                throw new IllegalArgumentException("Unknown weapon: " + weaponName);
        }
    }

    private void handleWeaponSwitch() {
        // Implement weapon switch logic
    }

    public void switchWeapon() {
        if (secondaryWeapon != null) {
            currentWeapon = (currentWeapon.equals(primaryWeapon)) ? secondaryWeapon : primaryWeapon;
            System.out.println("Switched to " + currentWeapon.getName());
        }
    }

    public void incrementKillCount() {
        killCount++;
        roundKillCount++;
    }

    public int getKillCount() {
        return killCount;
    }

    public int getRoundKillCount() {
        return roundKillCount;
    }

    public void resetRoundKillCount() {
        roundKillCount = 0;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY != 0) {
            switchWeapon();
        }
        return true;
    }

    // InputProcessor methods
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void dispose() {
        meleeNormalSound.dispose();
        meleeTouchedSound.dispose();
    }

    public Weapon getPrimaryWeapon() {
        return primaryWeapon;
    }

    public Weapon getSecondaryWeapon() {
        return secondaryWeapon;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public XpManager getXpManager() {
        return xpManager;
    }

    public void levelUp() {
        float increasePerLevel = 1.0f / Settings.LEVEL_STAT;

        if (speedIncreasePercentage < 1.0f) {
            speedIncreasePercentage += increasePerLevel;
            this.speed = baseSpeed * (1 + speedIncreasePercentage);
        }
    
        if (meleeDamageIncreasePercentage < 1.0f) {
            meleeDamageIncreasePercentage += increasePerLevel;
            this.meleeDamage = (int) (baseMeleeDamage * (1 + meleeDamageIncreasePercentage));
        }
    
        System.out.println("Niveau augmenté ! HP: " + this.hp + ", Vitesse: " + this.speed + ", Dégâts de mêlée: " + this.meleeDamage);
    }
}


