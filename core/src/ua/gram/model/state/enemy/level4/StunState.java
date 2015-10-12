package ua.gram.model.state.enemy.level4;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.level3.Level3State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class StunState extends Level4State {

    public StunState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " is stunned");
    }

    @Override
    public void manage() {
        getActor().isAffected = true;
        getActor().speed = getActor().defaultSpeed / 2f;

    }

    @Override
    public void postManage() {

    }
}
