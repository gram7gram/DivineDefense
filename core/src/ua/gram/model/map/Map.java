package ua.gram.model.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.EnemyPath;
import ua.gram.model.prototype.MapPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 * @see <a href="https://cdn.tutsplus.com/gamedev/authors/daniel-schuller/jrpg-using-tilemap-layers.png">TiledMap layers parsing</a>
 */
public class Map {

    private final Spawn spawn;
    private final TiledMap tiledMap;
    private final TiledMapTileLayer layer;
    private final Path path;
    private final MapPrototype prototype;
    private final int parseLimit;
    private final Base base;
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
        Log.info("Map is OK");
    }

    public boolean checkSpawnPosition(Vector2 pos) {
        TiledMapTileLayer layer = this.getActiveLayer();
        TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x, (int) pos.y);
        if (cell == null) return false;
        MapProperties prop = cell.getTile().getProperties();
        return isSpawn(prop);
    }

    public boolean checkPosition(Vector2 pos, String property) {
        TiledMapTileLayer layer = this.getActiveLayer();
        TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x, (int) pos.y);
        if (cell == null) return false;
        MapProperties properties = cell.getTile().getProperties();
        return properties.containsKey(property);
    }

    /**
     * Parses whole map grid, looking for 'spawn' property of the tile.
     * If found one, new Spawn object is created and search is aborted.
     */
    private HashMap<String, Vector2> findMapPoints() {
        HashMap<String, Vector2> map = new HashMap<>(2);
        MapProperties properties;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) continue;

                properties = cell.getTile().getProperties();

                if (!map.containsKey(prototype.spawnProperty) && isSpawn(properties)) {
                    map.put(prototype.spawnProperty, new Vector2(x, y));
                } else if (!map.containsKey(prototype.baseProperty) && isBase(properties)) {
                    map.put(prototype.baseProperty, new Vector2(x, y));
                }

                if (map.containsKey(prototype.baseProperty) && map.containsKey(prototype.spawnProperty))
                    break;
            }
        }
        return map;
    }

    /**
     * Converts path to array of directions, which Actor should turn to.
     * Searches the map for 'walkable' property, starting from 'spawn' tile.
     * If found one and it is not the previous, it is added to array.
     * If the added one contains 'base' property, search is aborted.
     * <p/>
     * NOTE: Kind of A* path finding.
     * <p/>
     * NOTE: It is necessary to look through the map twice, because you have to make
     * sure that you start scanning from 'spawn' and finish in 'base' to avoid random
     * 'walkable' tile adding to the path.
     */
    public EnemyPath normalizePath(Vector2 lastDir, Vector2 start) {
        if (lastDir == null || start == null)
            throw new NullPointerException("Path normalization impossible");

        EnemyPath path = new EnemyPath();

        if (Path.equal(start, base.getPosition())) {
            path.addDirection(Path.opposite(lastDir));
            return path;
        }

        Vector2 position = Path.clone(start);
        MapProperties properties;
        boolean isFound = false;
        int count = 0;

        while (!isFound && count < parseLimit) {
            for (Vector2 direction : Path.DIRECTIONS) {
                if (!direction.equals(lastDir) && isInMapBounds(direction, position)) {
                    int currentX = (int) (position.x + direction.x);
                    int currentY = (int) (position.y + direction.y);
                    TiledMapTileLayer.Cell cell = layer.getCell(currentX, currentY);
                    if (cell == null) continue;
                    properties = cell.getTile().getProperties();
                    if (isWalkable(properties)) {
                        path.addDirection(direction);
                        path.addPath(new Vector2(currentX, currentY));
                        position.add(direction);
                        lastDir = Path.opposite(direction);
                        if (isBase(properties)) {
                            isFound = true;
                            break;
                        } else if (isSpawn(properties)) {
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

        recursion = 0;

        if (!isFound) {
            if (count == parseLimit)
                throw new GdxRuntimeException("Path normalization error: parse limit reached");
            else
                throw new GdxRuntimeException("Path normalization error: no Base found");
        }

        Log.info("Path is OK");

        return path;
    }

    public boolean isWalkable(MapProperties properties) {
        return properties.containsKey(prototype.walkableProperty);
    }

    public boolean isSpawn(MapProperties properties) {
        return properties.containsKey(prototype.spawnProperty);
    }

    public boolean isBase(MapProperties properties) {
        return properties.containsKey(prototype.baseProperty);
    }

    public boolean isInMapBounds(Vector2 direction, Vector2 position) {
        return (direction.equals(Path.WEST) && position.x > 0)
                || (direction.equals(Path.SOUTH) && position.y > 0)
                || (direction.equals(Path.EAST) && position.x < layer.getWidth() - 1)
                || (direction.equals(Path.NORTH) && position.y < layer.getHeight() - 1);
    }

    public boolean isBlocked(MapProperties properties) {
        return properties.containsKey(prototype.blockedProperty);
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
