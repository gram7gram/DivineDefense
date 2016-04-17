package ua.gram.model.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.model.Animator;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.GameActorPrototype;
import ua.gram.model.state.StateManager;

public abstract class GameActor<T1, T2, M extends StateManager> extends Actor {

    protected final float animationWidth;
    protected final float animationHeight;
    protected final Animator<T1, T2> animator;
    protected int updateIterationCount;
    protected Vector2 currentPosition;
    protected Vector2 currentDirection;
    protected Vector2 previousDirection;
    protected Path.Types currentDirectionType;
    protected Path.Types previousDirectionType;

    public GameActor(GameActorPrototype prototype) {
        this.setName(prototype.name);
        this.animationWidth = prototype.width;
        this.animationHeight = prototype.height;
        this.setSize(prototype.width, prototype.height);
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        currentPosition = new Vector2(getX(), getY());
        animator = new Animator<T1, T2>();
    }

    public abstract M getStateManager();

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) this.setDebug(DDGame.DEBUG);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + this.hashCode();
    }

    public float getAnimationWidth() {
        return animationWidth;
    }

    public float getAnimationHeight() {
        return animationHeight;
    }

    public Vector2 getCurrentPosition() {
        if (currentPosition == null) {
            currentPosition = new Vector2(this.getX(), this.getY());
        }
        currentPosition.set(this.getX(), this.getY());
        return currentPosition;
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getUpdateIterationCount() {
        return updateIterationCount;
    }

    public void setUpdateIterationCount(int updateIterationCount) {
        this.updateIterationCount = updateIterationCount;
    }

    public void addUpdateIterationCount(int updateIterationCount) {
        this.updateIterationCount += updateIterationCount;
    }

    public Path.Types getCurrentDirectionType() {
        return currentDirectionType;
    }

    public Vector2 getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Vector2 currentDirection) {
        this.currentDirection = currentDirection;
        previousDirection = Path.opposite(currentDirection);
        currentDirectionType = Path.getType(currentDirection);
        previousDirectionType = Path.getType(previousDirection);
    }

    public void setPreviousDirection(Vector2 previousDirection) {
        this.previousDirection = previousDirection;
        currentDirection = Path.opposite(previousDirection);
        previousDirectionType = Path.getType(previousDirection);
        currentDirectionType = Path.getType(currentDirection);
    }

    public Animator<T1, T2> getAnimator() {
        return animator;
    }
}