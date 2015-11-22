package ua.gram.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.model.prototype.MapPrototype;

import java.util.Stack;

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
    private Base base;
    private MapProperties properties;

    public Map(DDGame game, MapPrototype prototype) {
        this.prototype = prototype;
        this.tiledMap = game.getResources().getMap(prototype.name);
        path = new Path();
        layer = (TiledMapTileLayer) tiledMap.getLayers().get(prototype.layer);
        spawn = findSpawnPoint();
        Gdx.app.log("INFO", "Map is OK");
    }

    /**
     * Parses whole map grid, looking for 'spawn' property of the tile.
     * If found one, new Spawn object is created and search is aborted.
     */
    private Spawn findSpawnPoint() {
        Spawn spawnPoint = null;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                properties = layer.getCell(x, y).getTile().getProperties();
                if (properties.containsKey(prototype.spawnProperty)) {
                    spawnPoint = new Spawn(new Vector2(x, y));
                    break;
                }
            }
        }
        return spawnPoint;
    }

//    public ArrayList<Vector2> getDirectionsFrom(Vector2 start) {
//        if (path == null) path = normalizePath(spawn.getPosition());
//        ArrayList<Vector2> route = path.getPath();
//        int currentIndex = route.indexOf(start);
//        List<Vector2> list = path.getDirections().subList(currentIndex + 1, route.size());
//        return new ArrayList<Vector2>(list);
//    }

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
    public Path normalizePath(Vector2 start) {
        Path route = new Path();
        Vector2 lastDir = new Vector2();
        Vector2 position = new Vector2((int) start.x, (int) start.y);
        boolean isFound = false;
        while (!isFound) {
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
                            route.addDirection(direction);
                            route.addPath(new Vector2(currentX, currentY));
                            position.add(direction);
                            lastDir = new Vector2((int) -direction.x, (int) -direction.y);
                            if (properties.containsKey(prototype.baseProperty)) {
                                base = new Base(position);
                                isFound = true;
                            }
                        }
                    }
                }
            }
        }
        Gdx.app.log("INFO", "Path is OK");
        return route;
    }

    /**
     * Converts path to array of directions, which Actor should turn to.
     * Searches the map for 'walkable' property, starting from 'spawn' tile.
     * If found one and it is not the previous, it is added to array.
     * if the added one contains 'base' property, new Base object is
     * created and search is aborted.
     * <p/>
     * NOTE: Kind of A* path finding.
     * <p/>
     * NOTE: It is necessary to look through the map twice, because you have to make
     * sure that you start scanning from 'spawn' and finish in 'base' to avoid random
     * 'walkable' tile adding to the path.
     */
    public Stack<Vector2> normalizeDirections(Vector2 start) {
        Stack<Vector2> elements = new Stack<Vector2>();
        Vector2 lastDir = new Vector2();
        Vector2 position = new Vector2((int) start.x, (int) start.y);
        boolean isFound = false;
        while (!isFound) {
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
                            elements.push(direction);
                            position.add(direction);
                            lastDir = new Vector2((int) -direction.x, (int) -direction.y);
                            if (properties.containsKey(prototype.baseProperty)) {
                                isFound = true;
                            }
                        }
                    }
                }
            }
        }
        Gdx.app.log("INFO", "Path is OK");
        return elements;
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

}
