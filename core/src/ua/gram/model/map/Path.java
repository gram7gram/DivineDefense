package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Path {

    public static Vector2 NORTH;
    public static Vector2 SOUTH;
    public static Vector2 EAST;
    public static Vector2 WEST;
    public final List<Vector2> DIRECTIONS;
    public ArrayList<Vector2> path;

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
    }

    public synchronized void addPath(Vector2 dir) {
        path.add(dir);
    }

    public ArrayList<Vector2> getPath() {
        return path;
    }

}
