package ua.gram.model.state.enemy;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.enemy.StateSwapper;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.state.AbstractState;

/**
 * Representation of Actor at the moment of time.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class EnemyState extends AbstractState<Enemy> {

    protected StateSwapper<Enemy> stateSwapper;

    public EnemyState(DDGame game) {
        super(game);
        stateSwapper = new StateSwapper<>();
    }

    public void initAnimation(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        EnemyAnimationProvider provider = spawner.getAnimationProvider();

        setUncheckedType(enemy);

        AnimationPool pool = provider.get(enemy.getPrototype(), getType(),
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
    }

    @SuppressWarnings("unchecked")
    private void setUncheckedType(Enemy enemy) {
        enemy.getAnimator().setPrimaryType(getType());
        enemy.getAnimator().setSecondaryType(enemy.getCurrentDirectionType());
    }

    protected abstract Types.EnemyState getType();
}
