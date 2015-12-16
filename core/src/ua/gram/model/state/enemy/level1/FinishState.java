package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.Player;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FinishState extends InactiveState {

    public FinishState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);
        Gdx.app.log("INFO", enemy + " reaches the Base");
    }

    @Override
    public void manage(Enemy enemy, float delta) {

    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);

        enemy.getAnimationProvider().get(enemy).free(enemy);
        Gdx.app.log("INFO", enemy + " frees animation");

        //NOTE Recursion exception on free????

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();

        enemy.clearActions();
        spawner.free(enemy);
        group.clear();
        group.remove();

        Player player = this.getGame().getPlayer();
        player.decreaseHealth();
    }
}
