package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Random;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.DEAD;
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);

//        enemy.getAnimationProvider().get(enemy.getPrototype()).free(enemy);
//        Log.info(enemy + " frees animation");

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();

        enemy.clearActions();
        spawner.free(enemy);
        group.clear();
        group.remove();
        Log.info(enemy + " is dead");
    }

    @Override
    public void postManage(Enemy enemy) {
        getGame().getPlayer().addCoins(enemy.reward);
        float value = new Random().nextFloat();

        //10% chance to getPool a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Log.info("Player got 1 gem");
        }
    }
}
