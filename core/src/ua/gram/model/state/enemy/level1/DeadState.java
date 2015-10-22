package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.InactiveState;

import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        super.preManage(enemy);
        Gdx.app.log("INFO", enemy + " is dead");
    }

    @Override
    public void manage(Enemy enemy,float delta) {
        getGame().getPlayer().addCoins(enemy.reward);
        float value = new Random().nextFloat();

        //10% chance to get a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Gdx.app.log("INFO", "Player got 1 gem");
        }

    }

    @Override
    public void postManage(Enemy enemy) {

    }
}
