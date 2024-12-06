package io.github.some_example_name.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Random;
import io.github.some_example_name.ScoreManager;
import io.github.some_example_name.Settings;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.AssetLoader;
import io.github.some_example_name.Hitbox;
import io.github.some_example_name.MessageManager;
import com.badlogic.gdx.audio.Sound;

public abstract class Enemy extends Character {
    private int dmg;
    private Player player;
    private float range; 
    private long lastAttackTime;
    private long attackCooldown;
    private int idleCurrentSpriteIndex = 0;
    private float idleAnimationTimer = 0f;
    private int attackCurrentSpriteIndex = 0;
    private float attackAnimationTimer = 0f;
    private boolean attacking;
    private boolean hurt = false;
    private boolean isDead = false;
    private float hurtAnimationTimer = 0f;
    private float deathAnimationTimer = 0f;
    private float originalSpeed;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> hurtAnimation;
    private Animation<TextureRegion> deathAnimation;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float hitboxScaleMult = 1.5f;
    private static final float HURT_SPEED_MULT = Settings.HURT_SPEED_MULT;
    private int points;
    private Vector2 knockbackVelocity = new Vector2();
    private float knockbackDuration = Settings.KNOCKBACK_DURATION;
    private float knockbackTime = 0;
    private int xpReward;
    private long playerInRangeStartTime = -1;
    private boolean attackTriggered = false;
    private long attackTriggerTime = 0;
    private MessageManager messageManager;
    private static final Sound dodgedSound = AssetLoader.getSound("dodged");
    private static final Sound enemyDamageSound = AssetLoader.getSound("enemy_attack");
       
    public Enemy(Player player, float x, float y, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture, int attackFrameCols, int hurtFrameCols, int deathFrameCols, int hp, int[] speed, float range, int dmg, long attackCooldown, int points, int xpReward) {
        super(8, x, y, hp, getRandomSpeed(speed), walkTexture);
        initialize(dmg, range, attackCooldown, player, attackTexture, attackFrameCols, hurtTexture, hurtFrameCols, deathTexture, deathFrameCols, points);
        this.xpReward = xpReward;
        this.messageManager = new MessageManager(player);
    }

    public Enemy(int walkFrameCols, Player player, float x, float y, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture, int attackFrameCols, int hurtFrameCols, int deathFrameCols, int hp, int[] speed, float range, int dmg, long attackCooldown, int points,int xpReward) {
        super(walkFrameCols, x, y, hp, getRandomSpeed(speed), walkTexture);
        initialize(dmg, range, attackCooldown, player, attackTexture, attackFrameCols, hurtTexture, hurtFrameCols, deathTexture, deathFrameCols, points);
        this.xpReward = xpReward;
        this.messageManager = new MessageManager(player);
    }

    private void initialize(int dmg, float range, long attackCooldown, Player player, Texture attackTexture, int attackFrameCols, Texture hurtTexture, int hurtFrameCols, Texture deathTexture, int deathFrameCols, int points) {
        this.dmg = dmg;
        this.player = player;
        this.attackCooldown = attackCooldown;
        this.attackAnimation = sheetSplitter(attackTexture, attackFrameCols, 1, 0.1f);
        this.hurtAnimation = sheetSplitter(hurtTexture, hurtFrameCols, 1, 0.1f);
        this.deathAnimation = sheetSplitter(deathTexture, deathFrameCols, 1, 0.1f);
        this.originalSpeed = getSpeed();
        this.points = points;
        this.range = range;
    }
    
    public float getHitboxScaleMult() {
        return hitboxScaleMult;
    }
    public void setHitboxScaleMult(float hitboxScaleMult) {
            this.hitboxScaleMult = hitboxScaleMult;
        }
    private static int getRandomSpeed(int[] speedRange) {
        return speedRange[0] + new Random().nextInt(speedRange[1] - speedRange[0] + 1);
    }
    
    public Player getPlayer() {
        return player;
    }

    protected float calculateDistanceToPlayer() {
        if (player == null) {
            return Float.MAX_VALUE;
        }
        return (float) Math.sqrt(
            Math.pow(player.getX() - this.getX(), 2) +
            Math.pow(player.getY() - this.getY(), 2)
        );
    }

    public void setKnockbackVelocity(Vector2 knockbackVelocity) {
        this.knockbackVelocity.set(knockbackVelocity);
        this.knockbackTime = 0;
    }

    public void move(float deltaTime) {
        if (player == null) {
            System.out.println("Player is null, cannot move.");
            return;
        }

        if (knockbackTime < knockbackDuration) {
            // Apply knockback velocity
            setX(getX() + knockbackVelocity.x * deltaTime);
            setY(getY() + knockbackVelocity.y * deltaTime);
            knockbackTime += deltaTime;
        } else {
            // Normal movement
            float dx = player.getX() - this.getX();
            float dy = player.getY() - this.getY();
            float distance = calculateDistanceToPlayer();

            if (distance >= range) {
                setX(getX() + (dx / distance) * getSpeed() * deltaTime);
                setY(getY() + (dy / distance) * getSpeed() * deltaTime);
            } else {
                attack();
            }
        }
    }    

    @Override
    public void attack() {
        long currentTime = TimeUtils.millis();
        float distanceToPlayer = calculateDistanceToPlayer();
        if (distanceToPlayer <= range) {
            if (!attackTriggered && currentTime - lastAttackTime >= attackCooldown) {
                attackTriggered = true;
                attackTriggerTime = currentTime;
                attacking = true;
                setStateTime(0f);
            }
        }
    }

    private void checkDodge() {
        long currentTime = TimeUtils.millis();
        float distanceToPlayer = calculateDistanceToPlayer();
        if (attackTriggered && currentTime - attackTriggerTime >= Settings.DODGE_WINDOW) {
            if (distanceToPlayer <= range) {
                player.takeDamage(this.dmg);
                lastAttackTime = currentTime;
                float pitch = Settings.MIN_PITCH + new Random().nextFloat() * (Settings.MAX_PITCH - Settings.MIN_PITCH);
                enemyDamageSound.play(Settings.ENEMY_DAMAGE_VOLUME, pitch, 0); // Play the enemy_damage sound with volume from Settings
            } else {
                messageManager.setMessage("DODGED!", Settings.DEFAULT);
                float pitch = Settings.MIN_PITCH + new Random().nextFloat() * (Settings.MAX_PITCH - Settings.MIN_PITCH);
                dodgedSound.play(Settings.DODGED_VOLUME, pitch, 0); // Play the dodged sound with volume from Settings
            }
            attackTriggered = false;
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (isDead) {
            return;
        }
        super.takeDamage(damage);
        if (getHp() > 0) {
            hurt = true;
            hurtAnimationTimer = 0;
            setSpeed(originalSpeed * HURT_SPEED_MULT); // Reduce speed
        } else {
            /* die(); */
        }
    }

    @Override
    public void die() {
        if (!isDead) {
            isDead = true;
            deathAnimationTimer = 0;
            setStateTime(0);
            ScoreManager.getInstance().addPoints(points);
            player.gainXp(xpReward);
            player.incrementKillCount(); // Increment the player's kill count
        }
    }

    @Override
    public void update(float deltaTime) {
        setBounds(getX(), getY(), getWidth(), getHeight());
        if (isDead) {
            deathAnimationTimer += deltaTime;
        } else if (attacking) {
            if (attackAnimation.isAnimationFinished(getStateTime())) {
                attacking = false;
                setStateTime(0f);
            }
        } else if (hurt) {
            hurtAnimationTimer += deltaTime;
            if (hurtAnimation.isAnimationFinished(hurtAnimationTimer)) {
                hurt = false;
                setSpeed(originalSpeed);
            }
            move(deltaTime); // Continuer à se déplacer même lorsqu'il est blessé
        } else {
            move(deltaTime);
        }
        updateAnimation(deltaTime);
        attack(); // Ensure attack logic is checked every frame
        checkDodge(); // Ensure dodge logic is checked every frame
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        flipFrameIfNeeded(currentFrame);
        drawCurrentFrame(batch, currentFrame);
        drawHealthBar(batch);
        renderHitbox(batch);
        messageManager.render(batch); // Render messages
    }

    @Override
    public TextureRegion getCurrentFrame() {
        if (isDead) {
            return deathAnimation.getKeyFrame(deathAnimationTimer, false);
        } else if (hurt) {
            return hurtAnimation.getKeyFrame(hurtAnimationTimer, false);
        } else if (attacking) {
            return attackAnimation.getKeyFrame(getStateTime(), false);
        } else {
            return getWalkAnimation().getKeyFrame(getStateTime(), true);
        }
    }

    private void flipFrameIfNeeded(TextureRegion currentFrame) {
        if ((player.getX() < this.getX() && !currentFrame.isFlipX()) || (player.getX() >= this.getX() && currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }
    }

    private void drawCurrentFrame(SpriteBatch batch, TextureRegion currentFrame) {
        float aspectRatio = (float) currentFrame.getRegionWidth() / currentFrame.getRegionHeight();
        float width = getWidth() * getScale();
        float height = width / aspectRatio;
        batch.draw(currentFrame, getX() - width / 2, getY() - height / 2, width, height);
    }

    private void renderHitbox(SpriteBatch batch) {
        Rectangle hitbox = getBoundingRectangle();
        Hitbox.renderHitbox(batch, hitbox, Color.BLUE);
    }

    // Getter and Setter methods
    public int getAttackCurrentSpriteIndex() {
        return attackCurrentSpriteIndex;
    }

    public void setAttackCurrentSpriteIndex(int attackCurrentSpriteIndex) {
        this.attackCurrentSpriteIndex = attackCurrentSpriteIndex;
    }

    public int getIdleCurrentSpriteIndex() {
        return idleCurrentSpriteIndex;
    }

    public void setIdleCurrentSpriteIndex(int idleCurrentSpriteIndex) {
        this.idleCurrentSpriteIndex = idleCurrentSpriteIndex;
    }

    public float getAttackAnimationTimer() {
        return attackAnimationTimer;
    }

    public void setAttackAnimationTimer(float attackAnimationTimer) {
        this.attackAnimationTimer = attackAnimationTimer;
    }

    public float getIdleAnimationTimer() {
        return idleAnimationTimer;
    }

    public void setIdleAnimationTimer(float idleAnimationTimer) {
        this.idleAnimationTimer = idleAnimationTimer;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public boolean getAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(long attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void updateBoundingRectangle() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public boolean isAnimationFinished() {
        return isDead && deathAnimation.isAnimationFinished(deathAnimationTimer);
    }

    @Override
    public Rectangle getBoundingRectangle() {
        if (isDead) return new Rectangle(0, 0, 0, 0);
        TextureRegion currentFrame = getCurrentFrame();
        float hitboxWidth = currentFrame.getRegionWidth() * getScale() * hitboxScaleMult;
        float hitboxHeight = currentFrame.getRegionHeight() * getScale() * hitboxScaleMult;
        float hitboxX = getX() - hitboxWidth / 2;
        float hitboxY = getY() - hitboxHeight / 2 + 20 * getScale();
        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public int getPoints() {
        return points;
    }
}
