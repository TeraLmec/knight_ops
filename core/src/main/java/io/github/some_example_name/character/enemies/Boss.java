package io.github.some_example_name.character.enemies;
import io.github.some_example_name.character.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.some_example_name.character.Enemy;

public class Boss extends Enemy {
    public Boss(float x, float y, Player player, Texture walkTexture, Texture attackTexture, Texture hurtTexture, Texture deathTexture, int hp, int[] speedRange, float range, int dmg, long attackCooldown, int attackFrameCols, int points, int xpReward) {
        super(player, x, y, walkTexture, attackTexture, hurtTexture, deathTexture, attackFrameCols, 2, 4, hp, speedRange, range, dmg, attackCooldown, points, xpReward);
        setScale(1.1f);
        setHitboxScaleMult(getHitboxScaleMult() * 1.75f);
    }

    @Override
    public void drawHealthBar(SpriteBatch batch) {
        float scale = getScale() * 2;
        if (!isDead()) {
            float barWidth = 50 * scale;
            float barHeight = 5 * scale;
            float barX = getX() - barWidth / 2;
            float barY = getY() + getHeight() / 2 + 135;

            float healthPercentage = (float) getHp() / getMaxHp();
            float healthBarWidth = barWidth * healthPercentage;

            batch.end();
            getShapeRender().setProjectionMatrix(batch.getProjectionMatrix());
            getShapeRender().begin(ShapeRenderer.ShapeType.Filled);
            getShapeRender().setColor(Color.RED);
            getShapeRender().rect(barX, barY, barWidth, barHeight);
            getShapeRender().setColor(Color.GREEN);
            getShapeRender().rect(barX, barY, healthBarWidth, barHeight);
            getShapeRender().end();
            batch.begin();
        }
    }
}