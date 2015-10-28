package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;

import java.util.*;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Path {

    public static Vector2 NORTH;
    public static Vector2 SOUTH;
    public static Vector2 EAST;
    public static Vector2 WEST;
    public final List<Vector2> DIRECTIONS;
    public final ArrayList<Vector2> path;
    public final ArrayList<Vector2> directions;
    public final Stack<Vector2> directionStack;
    public final Stack<Vector2> pathStack;
    private boolean isReverssed = false;

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
        directionStack = new Stack<Vector2>();
        pathStack = new Stack<Vector2>();
    }

    public static Types getType(Vector2 dir) {
        if (dir == null || dir == Vector2.Zero) return null;
        if (dir.equals(Path.EAST)) {
            return Types.RIGHT;
        } else if (dir.equals(Path.WEST)) {
            return Types.LEFT;
        } else if (dir.equals(Path.NORTH)) {
            return Types.UP;
        } else if (dir.equals(Path.SOUTH)) {
            return Types.DOWN;
        } else {
            throw new NullPointerException("Direction is not of the known values: [" + dir.x + ":" + dir.y + "]");
        }
    }

    public static Vector2 getVector(Types type) {
        switch (type) {
            case UP:
                return Path.NORTH;
            case RIGHT:
                return Path.EAST;
            case LEFT:
                return Path.WEST;
            case DOWN:
                return Path.SOUTH;
            default:
                throw new NullPointerException("Type is not of the know values: " + type);
        }
    }

    public static Vector2 opposite(Vector2 dir) {
        if (dir == null || dir == Vector2.Zero) return null;
        if (dir.equals(EAST)) {
            return WEST;
        } else if (dir.equals(WEST)) {
            return EAST;
        } else if (dir.equals(NORTH)) {
            return SOUTH;
        } else if (dir.equals(SOUTH)) {
            return NORTH;
        } else {
            throw new NullPointerException("Direction is not of the known values: [" + dir.x + ":" + dir.y + "]");
        }
    }

    public void addDirection(Vector2 dir) {
        directions.add(dir);
        directionStack.push(dir);
    }

    public void addPath(Vector2 position) {
        path.add(position);
        pathStack.push(position);
    }

    public ArrayList<Vector2> getPath() {
        return path;
    }

    public ArrayList<Vector2> getDirections() {
        return directions;
    }

    public Stack<Vector2> getDirectionStack() {
        return directionStack;
    }

    public Stack<Vector2> getPathStack() {
        return pathStack;
    }

    public Vector2 nextDirection() {
        if (!isReverssed) {
            Collections.reverse(directionStack);
            isReverssed = true;
        }
        return directionStack.pop();
    }

    public Vector2 nextPath() {
        return pathStack.pop();
    }

    public Vector2 peekNextDirection() {
        return directionStack.peek();
    }

    public Vector2 peekNextPath() {
        return pathStack.peek();
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

    public enum Types {
        LEFT, UP, DOWN, RIGHT
    }
}
