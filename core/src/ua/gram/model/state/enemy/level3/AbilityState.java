package ua.gram.model.state.enemy.level3;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.AbilityUserInterface;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityState extends Level3State {

    private float abilityDurationCount = 0;
    private EnemyStateManager manager;
    private boolean executed;
    private boolean fixedPosition;

    public AbilityState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        if (!(enemy instanceof AbilityUserInterface))
            throw new IllegalArgumentException(enemy + " should not have access to ability");
        enemy.setCurrentLevel3StateType(Animator.Types.ABILITY);
        manager = enemy.getSpawner().getStateManager();
        manager.swapLevel2State(enemy, manager.getIdleState());
        EnemyAnimationProvider provider = enemy.getSpawner().getAnimationProvider();
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                enemy.getCurrentLevel3StateType(),
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        executed = false;
        abilityDurationCount = 0;
        fixedPosition = false;
        Gdx.app.log("INFO", enemy + " state: " + enemy.getCurrentLevel3StateType());
    }

    @Override
    public void manage(Enemy enemy, float delta) throws IllegalArgumentException {
        if (!(enemy instanceof AbilityUserInterface))
            throw new IllegalArgumentException(enemy + " should not have access to ability");
        float duration = ((AbilityUserInterface) enemy).getAbilityDuration();
        if (abilityDurationCount >= duration) {
            abilityDurationCount = 0;
            manager.swapLevel2State(enemy, manager.getWalkingState());
            manager.swapLevel3State(enemy, null);
        } else {
            abilityDurationCount += delta;
            if (!executed) {
                executed = true;
                ((AbilityUserInterface) enemy).ability();
            }
        }

    }

    @Override
    public void postManage(Enemy enemy) {
        if (!(enemy instanceof AbilityUserInterface))
            throw new IllegalArgumentException(enemy + " should not have access to ability");
        fixedPosition = false;
        executed = false;
        abilityDurationCount = 0;
    }
}
