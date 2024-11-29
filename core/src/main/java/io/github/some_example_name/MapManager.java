
package io.github.some_example_name;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapManager {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float unitScale = Settings.UNIT_SCALE;
    private static float tiledMapWidth;
    private static float tiledMapHeight;
    private static float borderThickness;

    public void loadMap() {
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load(Settings.MAP_PATH);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        tiledMapWidth = Settings.MAP_WIDTH_IN_TILES * Settings.TILE_WIDTH * unitScale;
        tiledMapHeight = Settings.MAP_HEIGHT_IN_TILES * Settings.TILE_HEIGHT * unitScale;

        // Calculate the thickness of the border of the arena
        MapObject borderRef = tiledMap.getLayers().get("rectangles").getObjects().get(0);
        if (borderRef instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) borderRef).getRectangle();
            borderThickness = rect.height * unitScale;
        }
    }

    public void reloadMap() {
        dispose(); // Dispose of the current map and renderer
        loadMap(); // Reload the map
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void resize(OrthographicCamera camera) {
        mapRenderer.setView(camera);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public float getUnitScale() {
        return unitScale;
    }

    public static float getTiledMapWidth() {
        return tiledMapWidth;
    }

    public static float getTiledMapHeight() {
        return tiledMapHeight;
    }

    public float getBorderThickness() {
        return borderThickness;
    }

    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}