package io.github.some_example_name.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.FirstScreen;

/**
 * Abstract class representing a character in the game.
 * This class handles common character properties and behaviors such as health, position, animation, and rendering.
 */
public abstract class Character extends Sprite {
    // Attributes
    protected int hp;
    protected int maxHp;
    protected float speed;
    protected int walkFrameCols;
    private float x;
    private float y;
    private boolean walking;
    private float scale = 0.6f;
    private float stateTime = 0f;
    protected Animation<TextureRegion> walkAnimation;
    private TextureRegion currentFrame;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Constructor for the Character class.
     * @param walkFrameCols number of columns in the walk animation sprite sheet
     * @param x initial x position
     * @param y initial y position
     * @param hp initial health points
     * @param speed movement speed
     * @param walkSheet texture for the walk animation
     */
    public Character(int walkFrameCols, float x, float y, int hp, float speed, Texture walkSheet) {
        super(walkSheet);
        this.hp = hp;
        this.maxHp = hp;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.walkFrameCols = walkFrameCols;
        this.walkAnimation = sheetSplitter(walkSheet, walkFrameCols, 1, 0.1f);
    }

    // Abstract methods
    public abstract void attack();
    public abstract void die();

    // Public methods
    public void update(float delta) {
        updateAnimation(delta);
        collisionBorder(FirstScreen.getTiledMapWidth(), FirstScreen.getTiledMapHeight());
    }

    public void render(SpriteBatch batch) {
        if (isWalking()) {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = walkAnimation.getKeyFrame(0); // Render the first frame for idle
        }
    
        float aspectRatio = (float) getCurrentFrame().getRegionWidth() / getCurrentFrame().getRegionHeight();
        float width = getWidth() * scale;
        float height = width / aspectRatio;
        batch.draw(getCurrentFrame(), getX() - width / 2, getY() - height / 2, width, height);
        drawHealthBar(batch);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            die();
        }
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) {
            this.hp = maxHp;
        }
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public void dispose() {
        walkAnimation.getKeyFrames()[0].getTexture().dispose();
        shapeRenderer.dispose();
    }

    // Protected methods
    protected void updateAnimation(float deltaTime) {
        stateTime += deltaTime;
    }

    protected void drawHealthBar(SpriteBatch batch) {
        if (!isDead()) {
            float barWidth = 50;
            float barHeight = 5;
            float barX = getX() - barWidth / 2;
            float barY = getY() + getHeight() / 2 + 10;

            float healthPercentage = (float) hp / maxHp;
            float healthBarWidth = barWidth * healthPercentage;

            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(barX, barY, healthBarWidth, barHeight);
            shapeRenderer.end();
            batch.begin();
        }
    }

    protected void collisionBorder(float mapWidth, float mapHeight) {
        int playerWidth = getWalkSprites()[0].getRegionWidth();
        int playerHeight = getWalkSprites()[0].getRegionHeight();
        float borderThickness = FirstScreen.getBorderThickness();

        // Prevent the player from going out of the map boundaries
        if  (x < borderThickness) x = borderThickness;
        if (x > mapWidth - playerWidth - borderThickness) x = mapWidth - playerWidth - borderThickness;
        if (y < borderThickness) y = borderThickness;
        if (y > mapHeight - playerHeight - borderThickness) y = mapHeight - playerHeight - borderThickness;
    }

    // Private methods
    protected Animation<TextureRegion> sheetSplitter(Texture texture, int frameCols, int frameRols , float frameDuration) {
        // Split the sprite sheet into individual frames
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / frameCols, texture.getHeight() / frameRols);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRols];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(frameDuration, frames);
    }

    // Getters and Setters
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public int getWalkFrameCols() {
        return walkFrameCols;
    }

    public void setWalkFrameCols(int walkFrameCols) {
        this.walkFrameCols = walkFrameCols;
    }

    public TextureRegion[] getWalkSprites() {
        return walkAnimation.getKeyFrames();
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public static ShapeRenderer getShapeRender() {
        return shapeRenderer;
    }

    public boolean isAnimationFinished() {
        return walkAnimation.isAnimationFinished(stateTime);
    }
}