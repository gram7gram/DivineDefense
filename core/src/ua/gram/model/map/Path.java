package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Path {

    public static final Vector2 NORTH = new Vector2(0, 1);
    public static final Vector2 SOUTH = new Vector2(0, -1);
    public static final Vector2 EAST = new Vector2(1, 0);
    public static final Vector2 WEST = new Vector2(-1, 0);
    public static List<Vector2> DIRECTIONS;

    public Path() {
        DIRECTIONS = new ArrayList<Vector2>(4);
        DIRECTIONS.add(NORTH);
        DIRECTIONS.add(SOUTH);
        DIRECTIONS.add(EAST);
        DIRECTIONS.add(WEST);
    }

    public static Direction getType(float x, float y) {
        if (Float.compare(x, EAST.x) == 0 && Float.compare(y, EAST.y) == 0) {
            return Direction.RIGHT;
        } else if (Float.compare(x, WEST.x) == 0 && Float.compare(y, WEST.y) == 0) {
            return Direction.LEFT;
        } else if (Float.compare(x, NORTH.x) == 0 && Float.compare(y, NORTH.y) == 0) {
            return Direction.UP;
        } else if (Float.compare(x, SOUTH.x) == 0 && Float.compare(y, SOUTH.y) == 0) {
            return Direction.DOWN;
        } else
            return null;
    }

    public static Vector2 getVector(Direction type) {
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
                return null;
        }
    }

    public static Vector2 opposite(Vector2 dir) {
        if (dir == null || dir == Vector2.Zero) return Vector2.Zero;

        Vector2 dirCopy = dir.cpy();

        if (dirCopy.equals(EAST)) {
            return WEST;
        } else if (dirCopy.equals(WEST)) {
            return EAST;
        } else if (dirCopy.equals(NORTH)) {
            return SOUTH;
        } else if (dirCopy.equals(SOUTH)) {
            return NORTH;
        }
        return Vector2.Zero;
    }

    public static Vector2 opposite(float x, float y) {
        if (Float.compare(x, EAST.x) == 0 && Float.compare(y, EAST.y) == 0) {
            return WEST;
        } else if (Float.compare(x, WEST.x) == 0 && Float.compare(y, WEST.y) == 0) {
            return EAST;
        } else if (Float.compare(x, NORTH.x) == 0 && Float.compare(y, NORTH.y) == 0) {
            return SOUTH;
        } else if (Float.compare(x, SOUTH.x) == 0 && Float.compare(y, SOUTH.y) == 0) {
            return NORTH;
        } else
            return Vector2.Zero;
    }

    public static boolean compare(Vector2 vec1, Vector2 vec2) {
        Vector2 vec1Copy = vec1.cpy();
        Vector2 vec2Copy = vec2.cpy();
        int vecX1 = (int) vec1Copy.x;
        int vecY1 = (int) vec1Copy.y;
        int vecX2 = (int) vec2Copy.x;
        int vecY2 = (int) vec2Copy.y;
        return vecX1 == vecX2 && vecY1 == vecY2;
    }

    public static boolean equal(Vector2 vec1, Vector2 vec2) {
        Vector2 vec1Copy = vec1.cpy();
        Vector2 vec2Copy = vec2.cpy();
        return Float.compare(vec1Copy.x, vec2Copy.x) == 0
                && Float.compare(vec1Copy.y, vec2Copy.y) == 0;
    }

    public static String toString(Vector2 pos) {
        Vector2 copyPos = pos != null ? pos.cpy() : null;
        return copyPos == null ? null : Path.toString(copyPos.x, copyPos.y);
    }

    public static String toString(float x, float y) {
        return "[" + x + ":" + y + "]";
    }

    public enum Direction {
        LEFT, UP, DOWN, RIGHT
    }
}
