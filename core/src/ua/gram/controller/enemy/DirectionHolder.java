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
    private Path.Direction currentDirectionType;
    private Path.Direction previousDirectionType;

    public DirectionHolder(GameActor actor) {
        this.actor = actor;
        currentPosition = Vector2.Zero;
        previousPosition = Vector2.Zero;
        originPosition = Vector2.Zero;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        currentPositionIndex = Vector2.Zero;
        validate();
    }

    public void validate() {
        if (currentDirection == Vector2.Zero) return;
        if (previousDirection == Vector2.Zero) return;

        if (Path.getType(currentDirection.x, currentDirection.y) == null) {
            throw new IllegalArgumentException("Not a direction: [" + currentDirection.x + ":" + currentDirection.y + "]");
        }
        if (Path.getType(previousDirection.x, previousDirection.y) == null) {
            throw new IllegalArgumentException("Not a direction: [" + previousDirection.x + ":" + previousDirection.y + "]");
        }
        if (currentDirectionType == null) {
            throw new IllegalArgumentException("Current direction type should match direction");
        }
        if (previousDirectionType == null) {
            throw new IllegalArgumentException("Previous direction type should match direction");
        }
    }

    public Vector2 getCurrentPosition() {
        validate();
        return currentPosition.cpy();
    }

    public void setCurrentPosition(float x, float y) {
        validate();
        currentPosition.set(x, y);
        validate();
    }

    public Vector2 getCurrentPositionIndex() {
        validate();
        currentPositionIndex.set(
                Math.round(currentPosition.x / DDGame.TILE_HEIGHT),
                Math.round(currentPosition.y / DDGame.TILE_HEIGHT));
        validate();
        return currentPositionIndex.cpy();
    }

    public Vector2 getOriginPosition() {
        validate();
        originPosition.set(
                currentPosition.x + actor.getOriginX(),
                currentPosition.y + actor.getOriginY());
        validate();
        return originPosition.cpy();
    }

    public Path.Direction getCurrentDirectionType() {
        validate();
        return currentDirectionType;
    }

    @Override
    public void resetObject() {
        validate();
        currentDirection.set(0, 0);
        previousDirection.set(0, 0);
        originPosition.set(0, 0);
        previousPosition.set(0, 0);
        currentPositionIndex.set(0, 0);
        validate();
    }

    public Vector2 getCurrentDirection() {
        validate();
        return currentDirection.cpy();
    }

    public void setCurrentDirection(float x, float y) {
        validate();
        if (Path.getType(x, y) == null) {
            throw new IllegalArgumentException("Not a direction: [" + x + ":" + y + "]");
        }

        currentDirection.set(x, y);
        Vector2 opposite = Path.opposite(x, y);
        previousDirection.set(opposite.x, opposite.y);
        currentDirectionType = Path.getType(x, y);
        previousDirectionType = Path.getType(opposite.x, opposite.y);

        validate();
    }

    public Vector2 getPreviousDirection() {
        validate();
        return previousDirection.cpy();
    }

    public Vector2 getPreviousPosition() {
        validate();
        return previousPosition.cpy();
    }

    public void setPreviousPosition(float x, float y) {
        validate();
        previousPosition.set(x, y);
        validate();
    }

    public void copy(DirectionHolder parentDirectionHolder) {
        Vector2 parentPosition = parentDirectionHolder.getCurrentPosition();
        setCurrentPosition(parentPosition.x, parentPosition.y);

        Vector2 parentDirection = parentDirectionHolder.getCurrentDirection();
        setCurrentDirection(parentDirection.x, parentDirection.y);
    }
}
