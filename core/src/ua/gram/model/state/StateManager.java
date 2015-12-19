package ua.gram.model.state;

import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class StateManager<A extends GameActor> {

    protected final DDGame game;
    protected A actor;

    public StateManager(DDGame game) {
        this.game = game;
    }

    public abstract void init(A actor);

    public abstract void update(A actor, float delta);

    /**
     * Swap states. Executes StateInterface::postManage on {@param before}
     * and StateInterface::preManage on {@param after}.
     *
     * @param actor the actor which will be managed
     * @param before current state; nullable
     * @param after new state; nullable
     * @param level integer represetation of the state level, aka 1-4
     */
    public abstract void swap(A actor, StateInterface before, StateInterface after, int level);

    /**
     * Save actor-specific state
     *  @param actor
     * @param newState
     */
    public abstract void persist(A actor, StateInterface newState, int level) throws NullPointerException, GdxRuntimeException;

    /**
     * Reset all states for the Actor
     *
     * @param actor
     */
    public abstract void reset(A actor);

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }

}
