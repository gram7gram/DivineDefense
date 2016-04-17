package ua.gram.model.state.enemy.level2;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.state.enemy.EnemyStateManager;

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
        getManager().getAnimationChanger()
                .update(enemy, enemy.getCurrentDirection(), getType());
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
