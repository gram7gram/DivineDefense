package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.controller.state.StateManager;
import ua.gram.model.Animator;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.Resetable;
import ua.gram.model.prototype.GameActorPrototype;

public abstract class GameActor<T1, T2, M extends StateManager>
        extends Actor
        implements Resetable {

    protected final Animator<T1, T2> animator;
    protected final float animationWidth;
    protected final float animationHeight;
    protected int updateIterationCount;
    private float stateTime;

    public GameActor(GameActorPrototype prototype) {
        this.setName(prototype.name);
        this.animationWidth = prototype.width;
        this.animationHeight = prototype.height;
        this.setSize(prototype.width, prototype.height);
        animator = new Animator<T1, T2>();
    }

    public abstract M getStateManager();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if ((!DDGame.PAUSE || !animator.hasCurrentFrame()) && animator.getAnimation() != null) {
            animator.setCurrentFrame(animator.getAnimation().getKeyFrame(stateTime, true));
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (animator.hasCurrentFrame()) batch.draw(animator.getCurrentFrame(), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) this.setDebug(DDGame.DEBUG);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + this.hashCode();
    }

    @Override
    public void resetObject() {
        stateTime = 0;
        animator.setCurrentFrame(null);
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

    public Animator<T1, T2> getAnimator() {
        return animator;
    }

    public PoolableAnimation getPoolableAnimation() {
        return animator.getPoolable();
    }
}