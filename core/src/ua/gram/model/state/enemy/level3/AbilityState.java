package ua.gram.model.state.enemy.level3;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityState extends Level3State {

    public AbilityState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        initAnimation(enemy, Animator.Types.ABILITY);
        user.setAbilityExecuted(false);
        user.setAbilityDurationCount(0);
        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    @Override
    public void manage(Enemy enemy, float delta) throws IllegalArgumentException {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        float duration = user.getAbilityDuration();
        EnemyStateManager manager = user.getSpawner().getStateManager();
        if (user.getAbilityDurationCount() >= duration) {
            user.setAbilityDurationCount(0);
            manager.swapLevel2State(user, manager.getWalkingState(user));
            manager.swapLevel3State(user, null);
        } else {
            user.addAbilityDurationCount(delta);
            if (!user.isAbilityExecuted() && !user.isInterrupted()) {
                user.setAbilityExecuted(user.ability());
            }
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        user.setAbilityExecuted(false);
        user.setAbilityDurationCount(0);
    }

    private void check(Enemy enemy) {
        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " should not have access to ability");
    }
}
