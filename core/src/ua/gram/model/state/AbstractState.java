package ua.gram.model.state;

import ua.gram.DDGame;
import ua.gram.model.actor.GameActor;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbstractState<A extends GameActor> implements StateInterface<A> {

    protected final DDGame game;

    public AbstractState(DDGame game) {
        this.game = game;
    }

    public DDGame getGame() {
        return game;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void preManage(A actor) {

    }

    @Override
    public void manage(A actor, float delta) {

    }

    @Override
    public void postManage(A actor) {

    }
}
