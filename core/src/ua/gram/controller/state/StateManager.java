package ua.gram.controller.state;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.model.Initializer;
import ua.gram.model.actor.GameActor;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class StateManager<A extends GameActor> implements Initializer {

    protected final DDGame game;

    public StateManager(DDGame game) {
        this.game = game;
    }

    public abstract void update(A actor, float delta);

    /**
     * Swap states. Executes <b>StateInterface::postManage</b> on <b>before</b>
     * and <b>StateInterface::preManage</b> on <b>after</b>.
     *
     * @param actor  the actor which will be managed
     * @param before current state; nullable
     * @param after  new state; nullable
     * @param level  integer represetation of the state levelConfig, aka 1-4
     */
    public synchronized void swap(A actor, StateInterface<A> before, StateInterface<A> after, int level) {
        if (actor == null) return;

        if (before == null && after == null)
            throw new NullPointerException("Could not swap both empty states");

        if (before != null) {
            try {
                before.postManage(actor);
            } catch (Exception e) {
                Log.exc("Could not execute postManage() on "
                        + actor + "'s state " + before, e);
            }
        }

        if (before == after) {
            Log.warn("Ignored swap " + before + " to " + after + " on " + actor);
            return;
        }

        try {
            persist(actor, after, level);
        } catch (Exception e) {
            Log.exc("Could not execute persist() on "
                    + actor + "'s state " + before, e);
        }

        if (after != null) {
            try {
                after.preManage(actor);
            } catch (Exception e) {
                Log.exc("Could not execute preManage() on "
                        + actor + "'s state " + before, e);
            }
        }
    }

    /**
     * Save actor-specific state
     *
     * @param actor    the actor which will be managed
     * @param newState new state of the actor
     * @param level    levelConfig of the state
     */
    public abstract void persist(A actor, StateInterface<A> newState, int level) throws NullPointerException, GdxRuntimeException;

    /**
     * Reset all states for the Actor
     *
     * @param actor the actor which will be managed
     */
    public abstract void reset(A actor);

}
