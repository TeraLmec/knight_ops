package io.github.some_example_name.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private Vector2 position;
    private Animation<TextureRegion> animation;
    private float stateTime;

    public Explosion(Vector2 position, Animation<TextureRegion> animation) {
        this.position = position;
        this.animation = animation;
        this.stateTime = 0f;
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        float width = currentFrame.getRegionWidth();
        float height = currentFrame.getRegionHeight();
        batch.draw(currentFrame, position.x - width / 2, position.y - height / 2, width, height);
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }
}