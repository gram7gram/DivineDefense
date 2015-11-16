package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class InactiveState extends Level1State {

    public InactiveState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        Gdx.app.log("INFO", enemy + " is inactive");
    }

    @Override
    public void manage(Enemy enemy, float delta) {

    }

    @Override
    public void postManage(Enemy enemy) {

    }
}
