package ua.gram.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import ua.gram.DDGame;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyPath {

    public ArrayList<Vector2> path;
    public ArrayList<Vector2> directions;
    public Stack<Vector2> directionStack;
    public Stack<Vector2> pathStack;
    private boolean isReversed = false;

    public EnemyPath() {
        path = new ArrayList<Vector2>();
        directions = new ArrayList<Vector2>();
        directionStack = new Stack<Vector2>();
        pathStack = new Stack<Vector2>();
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
        if (!isReversed) {
            Collections.reverse(directionStack);
            Collections.reverse(pathStack);
            isReversed = true;
        }
        return directionStack.pop();
    }

    public Vector2 nextPath() {
        if (!isReversed) {
            Collections.reverse(directionStack);
            Collections.reverse(pathStack);
            isReversed = true;
        }
        return pathStack.pop();
    }

    public Vector2 peekNextDirection() {
        return directionStack.peek();
    }

    public Vector2 peekNextPath() {
        return pathStack.peek();
    }

    /**
     * @param posToSpawn actual positionIndex of an Actor in pixels
     * @return next positionIndex in the path
     */
    public Vector2 getNextPosition(Vector2 posToSpawn) {
        int count = 0;
        posToSpawn.x = (int) (posToSpawn.x / DDGame.TILE_HEIGHT);
        posToSpawn.y = (int) (posToSpawn.y / DDGame.TILE_HEIGHT);
        for (Vector2 pos : path) {
            if (Path.equal(pos, posToSpawn)) {
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
}
