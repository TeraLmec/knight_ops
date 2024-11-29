package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraManager {
    private OrthographicCamera camera;
    private float tiledMapWidth;
    private float tiledMapHeight;
    private float mouseX;
    private float mouseY;

    public CameraManager(float tiledMapWidth, float tiledMapHeight) {
        this.tiledMapWidth = tiledMapWidth;
        this.tiledMapHeight = tiledMapHeight;
        initializeCamera();
    }

    //This method initializes the camera with the viewport width and height set to the minimum of the tiled map width and height and the screen width and height.
    private void initializeCamera() {
        camera = new OrthographicCamera();
        float viewportWidth = Math.min(tiledMapWidth, Gdx.graphics.getWidth());
        float viewportHeight = Math.min(tiledMapHeight, Gdx.graphics.getHeight());
        camera.setToOrtho(false, viewportWidth, viewportHeight);
    }

    //This method is called every frame to update the camera position
    public void updatePosition(Vector2 playerPosition) {
        Vector2 cameraOffset = calculateCameraOffset(playerPosition);
        Vector2 targetPosition = calculateTargetPosition(playerPosition, cameraOffset);
        interpolateCameraPosition(targetPosition);
        camera.update();
        updateMouseCoordinates();
    }

    //This method calculates the camera offset based on the player position and the mouse position.
    private Vector2 calculateCameraOffset(Vector2 playerPosition) {
        float cameraOffsetX = (mouseX >= playerPosition.x) ? Math.min((mouseX - playerPosition.x) / 2, 275) : Math.max((mouseX - playerPosition.x) / 2, -275);
        float cameraOffsetY = (mouseY >= playerPosition.y) ? Math.min((mouseY - playerPosition.y) / 2, 275) : Math.max((mouseY - playerPosition.y) / 2, -275);
        return new Vector2(cameraOffsetX, cameraOffsetY);
    }

    //This method calculates the target position of the camera based on the player position and the camera offset.
    private Vector2 calculateTargetPosition(Vector2 playerPosition, Vector2 cameraOffset) {
        float targetX = Math.max(camera.viewportWidth / 2, Math.min(playerPosition.x + cameraOffset.x, tiledMapWidth - camera.viewportWidth / 2));
        float targetY = Math.max(camera.viewportHeight / 2, Math.min(playerPosition.y + cameraOffset.y, tiledMapHeight - camera.viewportHeight / 2));
        return new Vector2(targetX, targetY);
    }

    //This method interpolates the camera position towards the target position.
    private void interpolateCameraPosition(Vector2 targetPosition) {
        float interpolationFactor = 0.05f;
        camera.position.x += (targetPosition.x - camera.position.x) * interpolationFactor;
        camera.position.y += (targetPosition.y - camera.position.y) * interpolationFactor;
    }

    //This method updates the mouse coordinates based on the screen coordinates.
    private void updateMouseCoordinates() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        mouseX = mousePos.x;
        mouseY = mousePos.y;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public Vector3 unproject(Vector3 screenCoords) {
        return camera.unproject(screenCoords);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
}
