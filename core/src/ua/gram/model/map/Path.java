package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;

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
     * TODO Return right direction
     *
     * @param posToSpawn
     * @return
     */
    public Vector2 getNextDirection(Vector2 posToSpawn) {
        for (Vector2 pos : path) {
            if (Float.compare(pos.x, posToSpawn.x) == 0
                    && Float.compare(pos.y, posToSpawn.y) == 0) {
                return NORTH;
            }
        }
        return EAST;
    }
}
