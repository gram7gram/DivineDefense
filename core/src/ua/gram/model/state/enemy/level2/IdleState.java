package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.level2.Level2State;

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
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                Animator.Types.IDLE,
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        Gdx.app.log("INFO", enemy + " is idle");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        enemy.speed = 0;
    }

    @Override
    public void postManage(Enemy enemy) {

    }
}
