package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.Player;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FinishState extends InactiveState {

    public FinishState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        super.preManage(enemy);
        Gdx.app.log("INFO", enemy + " reaches the Base");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();
        EnemyStateManager manager = enemy.getSpawner().getStateManager();

        manager.swapLevel1State(enemy, null);

        enemy.clearActions();

        spawner.free(enemy);

        enemy.remove();
        group.clear();
        group.remove();
    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);
        Player player = this.getGame().getPlayer();
        player.decreaseHealth();
    }
}
