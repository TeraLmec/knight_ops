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
    protected int hp;
    protected int maxHp;
    private float x;
    private float y;
    protected float speed;
    private boolean walking;
    private float scale = 0.6f;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime = 0f;
    protected int walkFrameCols;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private TextureRegion currentFrame;

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
        this.walkAnimation = sheetSplitter(walkSheet, walkFrameCols, 0.1f);
    }

    /**
     * Splits the sprite sheet into individual frames for the walk animation.
     * @param texture the sprite sheet texture
     * @param frameCols number of columns in the sprite sheet
     * @param frameDuration duration of each frame in the animation
     * @return the walk animation
     */
    public Animation<TextureRegion> sheetSplitter(Texture texture, int frameCols, float frameDuration) {
        // Split the sprite sheet into individual frames
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / frameCols, texture.getHeight() / 1);
        TextureRegion[] frames = new TextureRegion[frameCols * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(frameDuration, frames);
    }

    /**
     * Updates the animation state time.
     * @param deltaTime time elapsed since the last frame
     */
    public void updateAnimation(float deltaTime) {
        stateTime += deltaTime;
    }

    /**
     * Checks if the walk animation is finished.
     * @return true if the animation is finished, false otherwise
     */
    public boolean isAnimationFinished() {
        return walkAnimation.isAnimationFinished(stateTime);
    }

    public static ShapeRenderer getShapeRender() {
        return shapeRenderer;
    }

    /**
     * Renders the character and its health bar.
     * @param batch the sprite batch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (isWalking()) {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = walkAnimation.getKeyFrame(0); // Render the first frame for idle
        }
    
        float aspectRatio = (float) currentFrame.getRegionWidth() / currentFrame.getRegionHeight();
        float width = getWidth() * scale;
        float height = width / aspectRatio;
        batch.draw(currentFrame, getX() - width / 2, getY() - height / 2, width, height);
        drawHealthBar(batch);
    }

    /**
     * Draws the health bar above the character.
     * @param batch the sprite batch used for rendering
     */
    public void drawHealthBar(SpriteBatch batch) {
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

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Handles collision with the map borders.
     * @param mapWidth width of the map
     * @param mapHeight height of the map
     */
    public void collisionBorder(float mapWidth, float mapHeight) {
        int playerWidth = getWalkSprites()[0].getRegionWidth();
        int playerHeight = getWalkSprites()[0].getRegionHeight();
        float borderThickness = FirstScreen.getBorderThickness();

        // Prevent the player from going out of the map boundaries
        if  (x < borderThickness) x = borderThickness;
        if (x > mapWidth - playerWidth - borderThickness) x = mapWidth - playerWidth - borderThickness;
        if (y < borderThickness) y = borderThickness;
        if (y > mapHeight - playerHeight - borderThickness) y = mapHeight - playerHeight - borderThickness;
    }

    /**
     * Updates the character's state.
     * @param delta time elapsed since the last frame
     */
    public void update(float delta) {
        updateAnimation(delta);
        collisionBorder(FirstScreen.getTiledMapWidth(), FirstScreen.getTiledMapHeight());
    }

    /**
     * Gets the current health points of the character.
     * @return the current health points
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the health points of the character.
     * @param hp the new health points
     */
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    /**
     * Gets the maximum health points of the character.
     * @return the maximum health points
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Sets the maximum health points of the character.
     * @param maxHp the new maximum health points
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Reduces the character's health points by the specified damage amount.
     * @param damage the amount of damage taken
     */
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            die();
        }
    }

    /**
     * Increases the character's health points by the specified amount.
     * @param amount the amount of health restored
     */
    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > maxHp) {
            this.hp = maxHp;
        }
    }

    /**
     * Checks if the character is dead.
     * @return true if the character is dead, false otherwise
     */
    public boolean isDead() {
        return this.hp <= 0;
    }

    /**
     * Abstract method for character attack behavior.
     */
    public abstract void attack();

    /**
     * Abstract method for character death behavior.
     */
    public abstract void die();

    /**
     * Gets the x position of the character.
     * @return the x position
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x position of the character.
     * @param x the new x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y position of the character.
     * @return the y position
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y position of the character.
     * @param y the new y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Sets the position of the character.
     * @param x the new x position
     * @param y the new y position
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the movement speed of the character.
     * @return the movement speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed of the character.
     * @param speed the new movement speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Checks if the character is walking.
     * @return true if the character is walking, false otherwise
     */
    public boolean isWalking() {
        return walking;
    }

    /**
     * Sets the walking state of the character.
     * @param walking the new walking state
     */
    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    /**
     * Gets the current state time of the animation.
     * @return the current state time
     */
    public float getStateTime() {
        return stateTime;
    }

    /**
     * Sets the state time of the animation.
     * @param stateTime the new state time
     */
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    /**
     * Gets the scale of the character.
     * @return the scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * Sets the scale of the character.
     * @param scale the new scale
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Gets the walk animation of the character.
     * @return the walk animation
     */
    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    /**
     * Sets the walk animation of the character.
     * @param walkAnimation the new walk animation
     */
    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    /**
     * Gets the number of columns in the walk animation sprite sheet.
     * @return the number of columns
     */
    public int getWalkFrameCols() {
        return walkFrameCols;
    }

    /**
     * Sets the number of columns in the walk animation sprite sheet.
     * @param walkFrameCols the new number of columns
     */
    public void setWalkFrameCols(int walkFrameCols) {
        this.walkFrameCols = walkFrameCols;
    }

    /**
     * Gets the walk sprites of the character.
     * @return the walk sprites
     */
    public TextureRegion[] getWalkSprites() {
        return walkAnimation.getKeyFrames();
    }

    /**
     * Disposes of the character's resources.
     */
    public void dispose() {
        walkAnimation.getKeyFrames()[0].getTexture().dispose();
        shapeRenderer.dispose();
    }
}