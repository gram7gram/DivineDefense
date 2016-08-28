package ua.gram.controller.state.enemy.level1;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Random;

import ua.gram.DDGame;
import ua.gram.controller.event.CoinEarnedEvent;
import ua.gram.controller.event.GemEarnedEvent;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.DEAD;
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        manager.getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        enemy.remove();
    }

    @Override
    public void postManage(Enemy enemy) {
        StageHolder stageHolder = enemy.getStage().getStageHolder();

        stageHolder.fire(new CoinEarnedEvent(enemy.reward));

        if (canReceiveGem()) {
            stageHolder.fire(new GemEarnedEvent(1));
        }
    }

    protected boolean canReceiveGem() {
        float value = new Random().nextFloat();
        return value >= .05f && value < .10f;
    }
}
