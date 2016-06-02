package ua.gram.controller.state.enemy.level4;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StunState extends Level4State {

    public StunState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.STUN;
    }

    @Override
    public void preManage(Enemy enemy) {
        getManager().getAnimationChanger()
                .update(enemy, enemy.getDirectionHolder().getCurrentDirection(), getType());
        super.preManage(enemy);
        enemy.isAffected = true;
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        super.manage(enemy, delta);
        enemy.getSpeed().decrease();
    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);
        enemy.isAffected = false;
        enemy.getSpeed().reset();
    }
}
