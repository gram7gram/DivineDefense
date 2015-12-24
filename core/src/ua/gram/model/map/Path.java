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
        DIRECTIONS = new ArrayList<Vector2>();
        DIRECTIONS.add(NORTH);
        DIRECTIONS.add(SOUTH);
        DIRECTIONS.add(EAST);
        DIRECTIONS.add(WEST);
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
        }
        return null;
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
                return null;
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
        }
        return null;
    }

    /**
     * Compare two Vector2s by casting their coordinates to integers
     *
     * @return are they "equal"
     */
    public static boolean compare(Vector2 vec1, Vector2 vec2) {
        int vecX1 = (int) vec1.x;
        int vecY1 = (int) vec1.y;
        int vecX2 = (int) vec2.x;
        int vecY2 = (int) vec2.y;
        return vecX1 == vecX2 && vecY1 == vecY2;
    }

    /**
     * Compare two Vector2s by rounding their coordinates to integers
     *
     * @return are they "equal"
     */
    public static boolean rcompare(Vector2 vec1, Vector2 vec2) {
        int vecX1 = Math.round(vec1.x);
        int vecY1 = Math.round(vec1.y);
        int vecX2 = Math.round(vec2.x);
        int vecY2 = Math.round(vec2.y);
        return vecX1 == vecX2 && vecY1 == vecY2;
    }

    public static boolean equal(Vector2 vec1, Vector2 vec2) {
        return Float.compare(vec1.x, vec2.x) == 0
                && Float.compare(vec1.y, vec2.y) == 0;
    }

    public static Vector2 direction(Vector2 vec1, Vector2 vec2) {
        int vecX1 = (int) vec1.x;
        int vecY1 = (int) vec1.y;
        int vecX2 = (int) vec2.x;
        int vecY2 = (int) vec2.y;
        if (vecX1 == vecX2 && vecY1 == vecY2) {
            return Vector2.Zero;
        } else if (vecX1 == vecX2 && vecY1 > vecY2) {
            return Path.NORTH;
        } else if (vecX1 == vecX2 && vecY1 < vecY2) {
            return Path.SOUTH;
        } else if (vecX1 > vecX2 && vecY1 == vecY2) {
            return Path.EAST;
        } else if (vecX1 < vecX2 && vecY1 == vecY2) {
            return Path.WEST;
        } else return null;
    }

    public static String toString(Vector2 pos) {
        return pos == null ? null : Path.toString(pos.x, pos.y);
    }

    public static String toStringRound(Vector2 pos) {
        return Path.toString(Math.round(pos.x), Math.round(pos.y));
    }

    public static String toString(float x, float y) {
        return "[" + x + ":" + y + "]";
    }

    public static Vector2 clone(Vector2 vector) {
        return new Vector2((int) vector.x, (int) vector.y);
    }

    public enum Types {
        LEFT, UP, DOWN, RIGHT
    }
}
