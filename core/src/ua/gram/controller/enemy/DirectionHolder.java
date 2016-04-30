package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;

import ua.gram.DDGame;
import ua.gram.model.Resetable;
import ua.gram.model.actor.GameActor;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DirectionHolder implements Resetable {

    private final GameActor actor;
    private final Vector2 previousPosition;
    private final Vector2 originPosition;
    private final Vector2 currentPosition;
    private final Vector2 currentDirection;
    private final Vector2 previousDirection;
    private final Vector2 currentPositionIndex;
    private Path.Types currentDirectionType;
    private Path.Types previousDirectionType;

    public DirectionHolder(GameActor actor) {
        this.actor = actor;
        currentPosition = Vector2.Zero;
        previousPosition = Vector2.Zero;
        originPosition = Vector2.Zero;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        currentPositionIndex = Vector2.Zero;
    }

    public Vector2 getCurrentPosition() {
        setCurrentPosition(actor.getX(), actor.getY());
        return currentPosition;
    }

    public void setCurrentPosition(float x, float y) {
        currentPosition.set(x, y);
    }

    public Vector2 getCurrentPositionIndex() {
        currentPositionIndex.set(
                Math.round(actor.getX()) / DDGame.TILE_HEIGHT,
                Math.round(actor.getY()) / DDGame.TILE_HEIGHT);
        return currentPositionIndex;
    }

    public Vector2 getOriginPosition() {
        originPosition.set(
                actor.getX() + actor.getOriginX(),
                actor.getY() + actor.getOriginY());
        return originPosition;
    }

    public Path.Types getCurrentDirectionType() {
        return currentDirectionType;
    }

    public Path.Types getPreviousDirectionType() {
        return previousDirectionType;
    }

    @Override
    public void resetObject() {
        currentDirection.set(0, 0);
        previousDirection.set(0, 0);
        originPosition.set(0, 0);
        previousPosition.set(0, 0);
        currentPositionIndex.set(0, 0);
    }

    public Vector2 getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Vector2 direction) {
        this.currentDirection.set(direction.x, direction.y);
        Vector2 opposite = Path.opposite(direction);
        previousDirection.set(opposite.x, opposite.y);
        currentDirectionType = Path.getType(direction);
        previousDirectionType = Path.getType(previousDirection);
    }

    public Vector2 getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(Vector2 direction) {
        this.previousDirection.set(direction.x, direction.y);
        Vector2 opposite = Path.opposite(direction);
        currentDirection.set(opposite.x, opposite.y);
        previousDirectionType = Path.getType(direction);
        currentDirectionType = Path.getType(currentDirection);
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(float x, float y) {
        previousPosition.set(x, y);
    }
}
