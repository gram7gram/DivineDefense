package ua.gram.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkablePath implements Disposable {

    private final ArrayList<Actor> debuggableDots;
    private ArrayList<Vector2> path;
    private Stack<Vector2> directionStack;
    private boolean isReversed = false;
    private boolean isDrawn = false;

    public WalkablePath() {
        path = new ArrayList<Vector2>();
        directionStack = new Stack<Vector2>();
        debuggableDots = new ArrayList<>(20);
    }

    public void addDirection(Vector2 dir) {
        directionStack.push(dir);
    }

    public void addPath(Vector2 position) {
        path.add(position);
    }

    public int size() {
        return path.size();
    }

    public int stepsBeforeFinishLength() {
        return directionStack.size();
    }

    public ArrayList<Vector2> getPath() {
        return path;
    }

    public void setPath(ArrayList<Vector2> path) {
        this.path = path;
    }

    public Vector2 nextDirection() {
        if (!isReversed) {
            Collections.reverse(directionStack);
            isReversed = true;
        }
        return directionStack.pop();
    }

    public Vector2 peekNextDirection() {
        return directionStack.peek();
    }

    public void draw(Stage stage) {
        if (DDGame.DEBUG && !isDrawn) {
            for (Vector2 p : path) {
                Actor dot = new Actor();
                dot.setSize(3, 3);
                dot.setPosition(
                        p.x * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - dot.getWidth()) / 2,
                        p.y * DDGame.TILE_HEIGHT + (DDGame.TILE_HEIGHT - dot.getHeight()) / 2);
                dot.setDebug(true);
                stage.addActor(dot);
                debuggableDots.add(dot);
            }
            isDrawn = true;
        }
    }

    @Override
    public void dispose() {
        for (Actor dot : debuggableDots) {
            dot.remove();
        }
        debuggableDots.clear();
    }
}
