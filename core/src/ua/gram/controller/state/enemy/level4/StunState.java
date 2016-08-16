package ua.gram.controller.state.enemy.level4;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.controller.action.StunAction;
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
    public void preManage(final Enemy enemy) {
        getManager().getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        enemy.addAction(
                Actions.sequence(
                        Actions.run(new StunAction(enemy, true)),
                        Actions.delay(2),
                        Actions.run(new StunAction(enemy, false))
                )
        );
    }
}
