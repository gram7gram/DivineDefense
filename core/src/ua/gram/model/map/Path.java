package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;

import java.util.*;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Path {

    public static final Vector2 NORTH = new Vector2(0, 1);
    public static final Vector2 SOUTH = new Vector2(0, -1);
    public static final Vector2 EAST = new Vector2(1, 0);
    public static final Vector2 WEST = new Vector2(-1, 0);
    public static List<Vector2> DIRECTIONS;
    public ArrayList<Vector2> path;
    public ArrayList<Vector2> directions;
    public Stack<Vector2> directionStack;
    public Stack<Vector2> pathStack;
    private boolean isReverssed = false;

    public Path() {
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
        if (dir.equals(EAST)) {
            return Types.RIGHT;
        } else if (dir.equals(WEST)) {
            return Types.LEFT;
        } else if (dir.equals(NORTH)) {
            return Types.UP;
        } else if (dir.equals(SOUTH)) {
            return Types.DOWN;
        } else {
            throw new NullPointerException("Direction is not of the known values: ["
                    + dir.x + ":" + dir.y + "]");
        }
    }

    public static Vector2 getVector(Types type) {
        switch (type) {
            case UP:
                return NORTH;
            case RIGHT:
                return EAST;
            case LEFT:
                return WEST;
            case DOWN:
                return SOUTH;
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
            throw new NullPointerException("Direction is not of the known values: ["
                    + dir.x + ":" + dir.y + "]");
        }
    }

    public static boolean compare(Vector2 vec1, Vector2 vec2) {
        int vecX1 = (int) vec1.x;
        int vecY1 = (int) vec1.y;
        int vecX2 = (int) vec2.x;
        int vecY2 = (int) vec2.y;
        return vecX1 == vecX2 && vecY1 == vecY2;
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

    public void setPath(ArrayList<Vector2> path) {
        this.path = path;
    }

    public ArrayList<Vector2> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<Vector2> directions) {
        this.directions = directions;
    }

    public Stack<Vector2> getDirectionStack() {
        return directionStack;
    }

    public void setDirectionStack(Stack<Vector2> directionStack) {
        this.directionStack = directionStack;
    }

    public Stack<Vector2> getPathStack() {
        return pathStack;
    }

    public void setPathStack(Stack<Vector2> pathStack) {
        this.pathStack = pathStack;
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
    public Vector2 getNextPosition(Vector2 posToSpawn) {
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
        throw new GdxRuntimeException("Position not found");
    }

    public List<Vector2> findFrom(Vector2 current) {
        int index = path.indexOf(current);
        return directionStack.subList(index, path.size() - 1);
    }

    public enum Types {
        LEFT, UP, DOWN, RIGHT
    }
}
