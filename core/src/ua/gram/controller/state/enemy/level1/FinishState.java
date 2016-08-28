package ua.gram.controller.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.event.PlayerDamagedEvent;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FinishState extends InactiveState {

    public FinishState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.FINISH;
    }

    @Override
    public void preManage(Enemy enemy) {
        getManager().getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        enemy.isRemoved = true;

        Log.info(enemy + " reaches the Base");

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();
        StageHolder stageHolder = enemy.getStage().getStageHolder();

        stageHolder.fire(new PlayerDamagedEvent(1));

        spawner.free(enemy);

        group.clear();
        group.remove();
    }

}
