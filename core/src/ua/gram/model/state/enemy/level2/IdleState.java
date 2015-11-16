package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        EnemyAnimationProvider provider = enemy.getSpawner().getAnimationProvider();
        if (provider != null) {
            AnimationPool pool = provider.get(
                    enemy.getOriginType(),
                    Animator.Types.IDLE,
                    enemy.getCurrentDirectionType());
            if (pool != null) {
                enemy.setAnimation(pool.obtain());
            }
        }
        Gdx.app.log("INFO", enemy + " state: " + Animator.Types.IDLE);
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        enemy.speed = 0;
//        Array<Action> actions = enemy.getActions();
//        boolean hasRemovedMove = false;
//        for (Action action : actions) {
//            if (action instanceof MoveByAction) {
//                actions.removeValue(action, false);
//                hasRemovedMove = true;
//            }
//        }
//        if (hasRemovedMove) {
//            Gdx.app.log("WARN", Animator.Types.IDLE + " state emptied MoveBy actions");
//        }
    }

    @Override
    public void postManage(Enemy enemy) {
        enemy.speed = enemy.defaultSpeed;
    }
}
