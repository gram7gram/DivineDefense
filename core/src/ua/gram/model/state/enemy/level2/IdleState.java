package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class IdleState extends Level2State {

    public IdleState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " is idle");
        getActor().getActions();
    }

    @Override
    public void manage() {

    }

    @Override
    public void postManage() {

    }
}
