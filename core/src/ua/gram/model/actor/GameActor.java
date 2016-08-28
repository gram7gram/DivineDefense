package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.Flags;
import ua.gram.controller.state.StateManager;
import ua.gram.model.Animator;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.prototype.GameActorPrototype;

public abstract class GameActor<T1, T2, M extends StateManager>
        extends Actor implements Disposable {

    protected final Animator<T1, T2> animator;
    protected final float animationWidth;
    protected final float animationHeight;
    protected final Counters counters;
    protected final Flags flags;
    private float stateTime;

    public GameActor(GameActorPrototype prototype) {
        animationWidth = prototype.width;
        animationHeight = prototype.height;
        setSize(prototype.width, prototype.height);
        setName(prototype.name);
        animator = new Animator<T1, T2>();
        counters = new Counters();
        flags = new Flags();
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
        if (!DDGame.PAUSE) {
            setDebug(DDGame.DEBUG);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + this.hashCode();
    }

    @Override
    public void dispose() {
        stateTime = 0;
        animator.setCurrentFrame(null);
    }

    public Counters getCounters() {
        return counters;
    }

    public Animator<T1, T2> getAnimator() {
        return animator;
    }

    public PoolableAnimation getPoolableAnimation() {
        return animator.getPoolable();
    }

    public Flags getFlags() {
        return flags;
    }
}