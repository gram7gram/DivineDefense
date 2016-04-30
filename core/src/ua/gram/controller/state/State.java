package ua.gram.controller.state;

import ua.gram.DDGame;
import ua.gram.model.Resetable;
import ua.gram.model.actor.GameActor;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class State<A extends GameActor> implements StateInterface<A>, Resetable {

    protected final DDGame game;

    public State(DDGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return name();
    }

    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public void preManage(A actor) {
        Log.info(actor + " state: " + name());
        Log.info(actor + " animation: " + actor.getAnimator().getPrimaryType());
    }

    @Override
    public void manage(A actor, float delta) {

    }

    @Override
    public void postManage(A actor) {

    }

    @Override
    public void resetObject() {

    }
}
