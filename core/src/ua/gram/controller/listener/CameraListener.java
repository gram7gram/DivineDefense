package ua.gram.controller.listener;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

import ua.gram.DDGame;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CameraListener extends InputAdapter {

    private final Camera camera;
    private final Vector3 curr;
    private final Vector3 last;
    private final Vector3 delta;
    private final int mapWidth;
    private final int mapHeight;

    public CameraListener(Camera camera, TiledMapTileLayer layer) {
        this.camera = camera;

        curr = new Vector3();
        last = new Vector3(-1, -1, -1);
        delta = new Vector3();
        mapWidth = layer.getWidth() * DDGame.TILE_HEIGHT;
        mapHeight = layer.getHeight() * DDGame.TILE_HEIGHT;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        camera.unproject(curr.set(x, y, 0));

        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.translate(delta.x, delta.y, 0);
        }

        last.set(x, y, 0);

        putInMapBounds();

        return false;
    }

    private void putInMapBounds() {

        if (camera.position.x < camera.viewportWidth / 2f)
            camera.position.x = camera.viewportWidth / 2f;
        else if (camera.position.x > mapWidth - camera.viewportWidth / 2f)
            camera.position.x = mapWidth - camera.viewportWidth / 2f;

        if (camera.position.y < camera.viewportHeight / 2f)
            camera.position.y = camera.viewportHeight / 2f;
        else if (camera.position.y > mapHeight - camera.viewportHeight / 2f)
            camera.position.y = mapHeight - camera.viewportHeight / 2f;

    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        Log.info("Camera at " + camera.position.x + ":" + camera.position.y);
        return false;
    }
}
