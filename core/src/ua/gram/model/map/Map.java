package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.EnemyPath;
import ua.gram.model.prototype.MapPrototype;

import java.util.HashMap;

/**
 * NOTE: https://cdn.tutsplus.com/gamedev/authors/daniel-schuller/jrpg-using-tilemap-layers.png
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Map {

    private final Spawn spawn;
    private final TiledMap tiledMap;
    private final TiledMapTileLayer layer;
    private final Path path;
    private final MapPrototype prototype;
    private final int parseLimit;
    private final Base base;
    private MapProperties properties;
    private int recursion = 0;

    public Map(DDGame game, MapPrototype prototype) {
        this.prototype = prototype;
        this.tiledMap = game.getResources().getMap(prototype.name);
        parseLimit = 3 * DDGame.MAX_ENTITIES;
        path = new Path();
        layer = (TiledMapTileLayer) tiledMap.getLayers().get(prototype.layer);
        HashMap<String, Vector2> points = findMapPoints();
        spawn = new Spawn(points.get(prototype.spawnProperty));
        base = new Base(points.get(prototype.baseProperty));
        Gdx.app.log("INFO", "Map is OK");
    }

    /**
     * Parses whole map grid, looking for 'spawn' property of the tile.
     * If found one, new Spawn object is created and search is aborted.
     */
    private HashMap<String, Vector2> findMapPoints() {
        HashMap<String, Vector2> map = new HashMap<String, Vector2>(2);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                properties = layer.getCell(x, y).getTile().getProperties();
                if (properties.containsKey(prototype.spawnProperty)) {
                    map.put(prototype.spawnProperty, new Vector2(x, y));
                } else if (properties.containsKey(prototype.baseProperty)) {
                    map.put(prototype.baseProperty, new Vector2(x, y));
                    break;
                }
            }
        }
        properties = null;
        return map;
    }

    /**
     * Converts path to array of directions, which Actor should turn to.
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
    public EnemyPath normalizePath(Vector2 lastDir, Vector2 start) {
        if (lastDir == null || start == null) throw new NullPointerException("Path normalization impossible");
        EnemyPath path = new EnemyPath();
        Vector2 position = Path.clone(start);
        boolean isFound = false;
        int count = 0;
        while (!isFound && count < parseLimit) {
            for (Vector2 direction : Path.DIRECTIONS) {
                if (!direction.equals(lastDir)) {
                    if ((direction.equals(Path.WEST) && position.x > 0)
                            || (direction.equals(Path.SOUTH) && position.y > 0)
                            || (direction.equals(Path.EAST) && position.x < layer.getWidth() - 1)
                            || (direction.equals(Path.NORTH) && position.y < layer.getHeight() - 1)) {
                        int currentX = (int) (position.x + direction.x);
                        int currentY = (int) (position.y + direction.y);
                        properties = layer.getCell(currentX, currentY)
                                .getTile().getProperties();
                        if (properties.containsKey(prototype.walkableProperty)) {
                            path.addDirection(direction);
                            path.addPath(new Vector2(currentX, currentY));
                            position.add(direction);
                            lastDir = Path.opposite(direction);
                            if (properties.containsKey(prototype.baseProperty)) {
                                isFound = true;
                                break;
                            } else if (properties.containsKey(prototype.spawnProperty)) {
                                ++recursion;
                                if (recursion < 2) {
                                    Log.warn("Path normalization was reversed: search was in the wrong direction");
                                    return normalizePath(Path.opposite(lastDir), start);
                                } else
                                    throw new GdxRuntimeException("Path normalization error: parsing in wrong direction");
                            }
                        }
                        ++count;
                    }
                }
            }
        }
        if (!isFound && count == parseLimit)
            throw new GdxRuntimeException("Path normalization error: parse limit reached");
        if (!isFound)
            throw new GdxRuntimeException("Path normalization error: no Base found");
        Gdx.app.log("INFO", "Path is OK");
        properties = null;
        recursion = 0;
        return path;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
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

    public MapPrototype getPrototype() {
        return prototype;
    }

    public TiledMapTileLayer getActiveLayer() {
        return layer;
    }
}
