package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Path {

    public static Vector2 NORTH;
    public static Vector2 SOUTH;
    public static Vector2 EAST;
    public static Vector2 WEST;
    public final List<Vector2> DIRECTIONS;
    public ArrayList<Vector2> path;
    public ArrayList<Vector2> directions;

    public Path() {
        NORTH = new Vector2(0, 1);
        SOUTH = new Vector2(0, -1);
        EAST = new Vector2(1, 0);
        WEST = new Vector2(-1, 0);
        DIRECTIONS = new LinkedList<Vector2>();
        DIRECTIONS.add(NORTH);
        DIRECTIONS.add(SOUTH);
        DIRECTIONS.add(EAST);
        DIRECTIONS.add(WEST);
        path = new ArrayList<Vector2>();
        directions = new ArrayList<Vector2>();
    }

    public synchronized void addDirection(Vector2 dir) {
        directions.add(dir);
    }

    public synchronized void addPath(Vector2 position) {
        path.add(position);
    }

    public ArrayList<Vector2> getPath() {
        return path;
    }

    public ArrayList<Vector2> getDirections() {
        return directions;
    }

    /**
     *
     * @param posToSpawn actual position of an Actor in pixels
     * @return next position in the path
     */
    public Vector2 getNextPosition(Vector2 posToSpawn) throws Exception {
        int count = 0;
        posToSpawn.x = (int) (posToSpawn.x / DDGame.TILE_HEIGHT);
        posToSpawn.y = (int) (posToSpawn.y / DDGame.TILE_HEIGHT);
        for (Vector2 pos : path) {
            if (Float.compare(pos.x, posToSpawn.x) == 0
                    && Float.compare(pos.y, posToSpawn.y) == 0) {
                return path.get(count);
            }
            ++count;
        }
        throw new Exception("Position not found");
    }
}
