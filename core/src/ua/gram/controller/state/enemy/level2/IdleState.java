package ua.gram.controller.state.enemy.level2;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.IDLE;
    }

    @Override
    public void preManage(Enemy enemy) {
        manager.getAnimationChanger()
                .update(enemy, getType());
        super.preManage(enemy);
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        enemy.speed = 0;
    }

    @Override
    public void postManage(Enemy enemy) {
        enemy.speed = enemy.defaultSpeed;
    }
}
