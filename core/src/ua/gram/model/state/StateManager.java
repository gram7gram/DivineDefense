package ua.gram.model.state;

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

    public StateManager(DDGame game, A actor) {
        this.game = game;
        this.actor = actor;
    }

    public abstract void init(A actor);

    public abstract void update(A actor, float delta);

    /**
     * Swap states. Executes postManage on before and preManage on after.
     *
     * @param before current state; nullable
     * @param after  new state; nullable
     */
    public abstract void swap(A actor, State before, State after);

    /**
     * Save actor-specific state
     *
     * @param actor
     * @param newState
     */
    public abstract void persist(A actor, State newState);

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }
}
