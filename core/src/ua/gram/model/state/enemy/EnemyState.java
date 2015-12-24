package ua.gram.model.state.enemy;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.enemy.EnemyStateSwapper;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.AbstractState;

/**
 * Representation of Actor at the moment of time.
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class EnemyState extends AbstractState<Enemy> {

    protected EnemyStateSwapper stateSwapper;

    public EnemyState(DDGame game) {
        super(game);
        stateSwapper = new EnemyStateSwapper();
    }

    public void initAnimation(Enemy enemy, Animator.Types type) {
        EnemySpawner spawner = enemy.getSpawner();
        EnemyAnimationProvider provider = spawner.getAnimationProvider();
        enemy.getAnimator().setType(type);
        AnimationPool pool = provider.get(enemy, enemy.getAnimator().getType());
        enemy.setAnimation(pool.obtain());
    }
}
