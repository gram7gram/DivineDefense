package ua.gram.model.state.enemy.level3;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
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

    public AbilityState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        check(enemy);
        initAnimation(enemy, Animator.Types.ABILITY);
        manager = enemy.getSpawner().getStateManager();
        executed = false;
        abilityDurationCount = 0;
        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    @Override
    public void manage(Enemy enemy, float delta) throws IllegalArgumentException {
        check(enemy);
        float duration = ((AbilityUserInterface) enemy).getAbilityDuration();
        if (abilityDurationCount >= duration) {
            abilityDurationCount = 0;
            manager.swapLevel2State(enemy, manager.getWalkingState(enemy));
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
        check(enemy);
        executed = false;
        abilityDurationCount = 0;
    }

    private void check(Enemy enemy) {
        if (!(enemy instanceof AbilityUserInterface))
            throw new IllegalArgumentException(enemy + " should not have access to ability");

    }
}
