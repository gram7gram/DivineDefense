package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * TODO Make layers with different objects.
 * https://cdn.tutsplus.com/gamedev/authors/daniel-schuller/jrpg-using-tilemap-layers.png
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Map {

    private Spawn spawn;
    private Base base;
    private TiledMap tiledMap;
    private TiledMapTileLayer layer;
    private Path path;

    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Terrain");
        parseMap();
        normalizePath();
        Gdx.app.log("INFO", "Map is OK");
    }

    /**
     * Parses whole map grid, looking for 'spawn' property of the tile.
     * If found one, new Spawn object is created and search is aborted.
     */
    private void parseMap() {
        MapProperties properties;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                properties = layer.getCell(x, y).getTile().getProperties();
                if (properties.containsKey("spawn")) {
                    spawn = new Spawn(new Vector2(x, y));
                    return;
                }
            }
        }
    }

    /**
     * Searches the map for 'walkable' property, starting from 'spawn' tile.
     * If found one and it is not the previous, it is added to array.
     * if the added one contains 'base' property, new Base object is
     * created and search is aborted.
     *
     * NOTE: Kind of A* path finding.
     *
     * NOTE: It is necessary to look through the map twice, because you have to make
     * sure that you start scanning from 'spawn' and finish in 'base' to avoid random
     * 'walkable' tile adding to the path.
     */
    private void normalizePath() {
        path = new Path();
        MapProperties properties;
        Vector2 lastDir = new Vector2();
        Vector2 position = new Vector2((int) spawn.getPosition().x, (int) spawn.getPosition().y);
        while (true) {
            for (Vector2 direction : path.DIRECTIONS) {
                if (!direction.equals(lastDir)) {
                    if ((direction.equals(Path.WEST) && position.x > 0)
                            || (direction.equals(Path.SOUTH) && position.y > 0)
                            || (direction.equals(Path.EAST) && position.x < layer.getWidth() - 1)
                            || (direction.equals(Path.NORTH) && position.y < layer.getHeight() - 1)) {
                        properties = layer.getCell(
                                (int) (position.x + direction.x),
                                (int) (position.y + direction.y))
                                .getTile().getProperties();
                        if (properties.containsKey("walkable")) {
                            path.addPath(direction);
                            position.add(direction);
                            lastDir = new Vector2((int) -direction.x, (int) -direction.y);
                            if (properties.containsKey("base")) {
                                base = new Base(position);
                                Gdx.app.log("INFO", "Path is OK");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public Base getBase() {
        return base;
    }

    public Path getPath() {
        return path;
    }

    public ArrayList<Vector2> getPathArray() {
        return path.getPath();
    }
}
