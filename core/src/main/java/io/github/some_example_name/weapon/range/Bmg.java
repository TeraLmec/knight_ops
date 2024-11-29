package io.github.some_example_name.weapon.range;

import io.github.some_example_name.character.Player;
import io.github.some_example_name.weapon.Weapon;
import io.github.some_example_name.character.Enemy;
import io.github.some_example_name.Settings;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;

public class Bmg extends Weapon {
    private boolean drawHitscan = false;
    private float hitscanTimer = 0;
    private Vector2 hitscanStart = new Vector2();
    private Vector2 hitscanEnd = new Vector2();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Bmg(Player player, List<Enemy> enemies) {
        super(player, enemies,
              (String) Settings.WEAPON_STATS.get("bmg").get("name"), 
              (Texture) Settings.WEAPON_STATS.get("bmg").get("texture"), 
              (int) Settings.WEAPON_STATS.get("bmg").get("damage"), 
              (float) Settings.WEAPON_STATS.get("bmg").get("fireRate"), 5000f,
              (String) Settings.WEAPON_STATS.get("bmg").get("sniper_shot")
        );
    }

    public void startHitscan(Vector2 playerCenter, Vector2 direction) {
        drawHitscan = true;
        hitscanTimer = 0;
        hitscanStart.set(playerCenter);
        hitscanEnd.set(playerCenter).add(direction.scl(5000));
        hitscan();
    }

    @Override
    protected void hitscan() {
        Vector2 start = new Vector2(hitscanStart);
        Vector2 end = new Vector2(hitscanEnd);

        for (Enemy enemy : getEnemies()) {
            if (enemy.getBoundingRectangle().contains(start) || enemy.getBoundingRectangle().contains(end) ||
                Intersector.intersectSegmentRectangle(start, end, enemy.getBoundingRectangle())) {
                enemy.takeDamage(getDamage());
            }
        }
    }

    @Override
    protected String getPapShootSoundPath() {
        return (String) Settings.WEAPON_STATS.get("bmg_pap").get("sniper_shot");
    }

    public void updateHitscan(float delta) {
        if (drawHitscan) {
            hitscanTimer += delta;
            if (hitscanTimer >= 1) {
                drawHitscan = false;
                hitscanTimer = 0;
            }
        }
    }

    public void renderHitscanLine(Matrix4 projectionMatrix) {
        if (!drawHitscan) return;
        shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Set blend function
        float alpha = 1 - (hitscanTimer / 1.0f); // Calculate alpha based on the timer
        shapeRenderer.setColor(new Color(1, 0, 0, alpha)); // Set color with alpha
        shapeRenderer.rectLine(hitscanStart, hitscanEnd, 10);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND); // Disable blending
    }
}