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

    public static boolean isValidDirection(Vector2 dir) {
        Vector2 dirCopy = dir.cpy();
        return !Path.isZero(dirCopy) && Path.getType(dirCopy.x, dirCopy.y) != null;
    }

    public static Direction getType(float x, float y) {

        Vector2 vector = new Vector2(x, y);

        if (Path.compare(vector, EAST)) {
            return Direction.RIGHT;
        } else if (Path.compare(vector, WEST)) {
            return Direction.LEFT;
        } else if (Path.compare(vector, NORTH)) {
            return Direction.UP;
        } else if (Path.compare(vector, SOUTH)) {
            return Direction.DOWN;
        } else {
            return null;
        }
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
        if (dir == null || Path.isZero(dir)) return Vector2.Zero;

        Vector2 vector = dir.cpy();

        if (Path.compare(vector, EAST)) {
            return WEST;
        } else if (Path.compare(vector, WEST)) {
            return EAST;
        } else if (Path.compare(vector, NORTH)) {
            return SOUTH;
        } else if (Path.compare(vector, SOUTH)) {
            return NORTH;
        } else {
            return Vector2.Zero;
        }
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

    public static boolean isZero(Vector2 vector) {
        Vector2 vector2Copy = vector.cpy();
        return Path.compare(vector2Copy, Vector2.Zero);
    }

    public enum Direction {
        LEFT, UP, DOWN, RIGHT
    }
}
