package ua.gram.controller.state;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import ua.gram.DDGame;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class State<A extends Actor> implements StateInterface<A>, Disposable {

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
    }

    @Override
    public void manage(A actor, float delta) {

    }

    @Override
    public void postManage(A actor) {

    }

    @Override
    public void dispose() {

    }
}
