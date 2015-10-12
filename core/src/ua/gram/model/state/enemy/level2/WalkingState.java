package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.level1.Level1State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class WalkingState extends Level2State {

    public WalkingState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " is walking");
    }

    @Override
    public void manage() {
        getActor().isAffected = false;
        getActor().speed = getActor().defaultSpeed;
    }

    @Override
    public void postManage() {

    }
}
