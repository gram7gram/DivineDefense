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
    private final Vector2 originPosition;
    private final Vector2 currentPosition;
    private final Vector2 currentPositionIndex;
    private final Vector2 currentDirection;
    private final Vector2 previousPosition;
    private final Vector2 previousDirection;
    private Path.Direction currentDirectionType;
    private Path.Direction previousDirectionType;

    public DirectionHolder(GameActor actor) {
        this.actor = actor;
        currentPosition = Vector2.Zero.cpy();
        currentPositionIndex = Vector2.Zero.cpy();
        previousPosition = Vector2.Zero.cpy();
        originPosition = Vector2.Zero.cpy();
        currentDirection = Vector2.Zero.cpy();
        previousDirection = Vector2.Zero.cpy();
        validate();
    }

    public void validate() {
        Vector2 currentDirectionCopy = currentDirection.cpy();
        Vector2 previousDirectionCopy = previousDirection.cpy();

        if (Path.isZero(currentDirectionCopy) || Path.isZero(previousDirectionCopy)) return;

        if (Path.getType(currentDirectionCopy.x, currentDirectionCopy.y) == null) {
            throw new IllegalArgumentException("Not a valid current direction: "
                    + Path.toString(currentDirectionCopy));
        }
        if (Path.getType(previousDirectionCopy.x, previousDirectionCopy.y) == null) {
            throw new IllegalArgumentException("Not a valid previous direction: "
                    + Path.toString(previousDirectionCopy));
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
        synchronized (currentPosition) {
            currentPosition.set(x, y);
            validate();
        }

        Vector2 currentPositionCopy = getCurrentPosition();
        setCurrentPositionIndex(
                currentPositionCopy.x / DDGame.TILE_HEIGHT,
                currentPositionCopy.y / DDGame.TILE_HEIGHT
        );
    }

    private void setCurrentPositionIndex(float x, float y) {
        validate();

        synchronized (currentPositionIndex) {
            currentPositionIndex.set(Math.round(x), Math.round(y));
            validate();
        }
    }

    public Vector2 getCurrentPositionIndex() {
        validate();
        return currentPositionIndex.cpy();
    }

    public Vector2 getOriginPosition() {
        validate();
        synchronized (originPosition) {
            originPosition.set(
                    currentPosition.x + actor.getOriginX(),
                    currentPosition.y + actor.getOriginY());
            validate();
        }
        return originPosition.cpy();
    }

    public Path.Direction getCurrentDirectionType() {
        validate();
        return currentDirectionType;
    }

    @Override
    public void resetObject() {
        validate();
        setCurrentPosition(0, 0);
        setPreviousPosition(0, 0);
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

        synchronized (currentDirection) {
            currentDirection.set(x, y);
        }

        Vector2 opposite = Path.opposite(currentDirection);
        synchronized (previousDirection) {
            previousDirection.set(opposite.x, opposite.y);
        }

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
        synchronized (previousPosition) {
            previousPosition.set(x, y);
            validate();
        }
    }

    public void copy(DirectionHolder parentDirectionHolder) {
        Vector2 parentPosition = parentDirectionHolder.getCurrentPosition();
        setCurrentPosition(parentPosition.x, parentPosition.y);

        Vector2 parentDirection = parentDirectionHolder.getCurrentDirection();
        setCurrentDirection(parentDirection.x, parentDirection.y);
    }
}
