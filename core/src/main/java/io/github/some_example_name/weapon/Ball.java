package io.github.some_example_name.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.ScoreManager;
import io.github.some_example_name.character.Enemy;
import java.util.Iterator;
import java.util.List;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.Hitbox;
import io.github.some_example_name.AssetLoader;
import java.util.Random;

public class Ball extends Sprite {
    private float speed;
    private Vector2 direction;
    private Vector2 position;
    private Animation<TextureRegion> explosionAnimation;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Sound hitSound;
    private float volume = 0.4f;
    private Random random = new Random();
    
    public Ball(Texture ballTexture, Vector2 startPosition, Vector2 direction, Texture explosionTexture, int explosionFrameCols, int explosionFrameRows, float speed, float scale) {
        super(ballTexture);
        this.position = new Vector2(startPosition);
        this.direction = new Vector2(direction).nor();
        this.explosionAnimation = createAnimation(explosionTexture, explosionFrameCols, explosionFrameRows, 0.05f);
        this.speed = speed;
        this.hitSound = AssetLoader.getSound("hit_marker"); // Change this line
        setScale(scale); // Set the scale of the ball
    }
    
    private Animation<TextureRegion> createAnimation(Texture spriteTexture, int frameCols, int frameRows, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(spriteTexture, spriteTexture.getWidth() / frameCols, spriteTexture.getHeight() / frameRows);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(frameDuration, frames);
    }

    public Animation<TextureRegion> getExplosionAnimation() {
        return explosionAnimation;
    }

    public void update(float delta, List<Enemy> enemies, Iterator<Ball> ballIterator) {
        position.mulAdd(direction, speed * delta);
        setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        float rotation = direction.angleDeg() - 90;
        setRotation(rotation);
        draw(batch);
        renderHitbox(batch);
    }

    private void renderHitbox(SpriteBatch batch) {
        Rectangle hitbox = getBoundingRectangle();
        Hitbox.renderHitbox(batch, hitbox, Color.RED);
    }

    public boolean isOutOfBounds(float mapWidth, float mapHeight) {
        return position.x < 0 || position.x > mapWidth || position.y < 0 || position.y > mapHeight;
    }

    public void updateBoundingRectangle() {
        setBounds(position.x, position.y, getWidth(), getHeight());
    }

    public Rectangle getBoundingRectangle() {
        float aspectRatio = (float) getRegionWidth() / getRegionHeight();
        float hitboxWidth = getRegionWidth() * getScaleX() * 2;
        float hitboxHeight = getRegionHeight() * getScaleY() * aspectRatio * 2;
        float hitboxX = getX() - hitboxWidth / 2 + getRegionWidth() / 2;
        float hitboxY = getY() - hitboxHeight / 2 + getRegionHeight() / 2;
        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void checkCollisions(List<Enemy> enemies, Iterator<Ball> ballIterator, int damage, List<Explosion> explosions) {
        if (enemies == null) {
            return;
        }
        updateBoundingRectangle(); // Ensure bounding rectangle is updated
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.updateBoundingRectangle(); // Ensure bounding rectangle is updated
            if (getBoundingRectangle().overlaps(enemy.getBoundingRectangle())) {
                ScoreManager.getInstance().addPoints(10);
                ballIterator.remove();
                enemy.takeDamage(damage); // Use existing method to reduce enemy health
                explosions.add(new Explosion(getPosition(), getExplosionAnimation())); // Add explosion
                float pitch = 0.8f + random.nextFloat() * 0.4f;
                hitSound.play(volume, pitch, 0);
                break;
            }
        }
    }

}