package ua.gram.model.state.enemy.level4;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StunState extends Level4State {

    public StunState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        enemy.isAffected = true;
        Gdx.app.log("INFO", enemy + " is stunned");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
//        enemy.decreaseSpeed();
    }

    @Override
    public void postManage(Enemy enemy) {
        enemy.isAffected = false;
        enemy.resetSpeed();
    }
}
