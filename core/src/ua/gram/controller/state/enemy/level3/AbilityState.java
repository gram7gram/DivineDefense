package ua.gram.controller.state.enemy.level3;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityState extends Level3State {

    public AbilityState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.ABILITY;
    }

    @Override
    public void preManage(Enemy enemy) {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        manager.swapLevel2State(user, manager.getIdleState());
        getManager().getAnimationChanger()
                .update(user, getType());
        super.preManage(user);
        user.setAbilityExecuted(false);
        user.setAbilityDurationCount(0);
    }

    @Override
    public void manage(Enemy enemy, float delta) throws IllegalArgumentException {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        float duration = user.getAbilityDuration();
        if (user.getAbilityDurationCount() >= duration || user.getAbilityDurationCount() < 0) {
            user.setAbilityDurationCount(0);
            manager.swapLevel2State(user, manager.getWalkingState(user));
            manager.swapLevel3State(user, null);
        } else {
            user.addAbilityDurationCount(delta);
            if (!user.isAbilityExecuted() && !user.isInterrupted()) {
                try {
                    user.setAbilityExecuted(user.ability());
                } catch (Exception e) {
                    Log.exc("Could not execute ability for " + user, e);
                    manager.swapLevel2State(user, manager.getWalkingState(user));
                    manager.swapLevel3State(user, null);
                }
            }
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        check(enemy);
        AbilityUser user = (AbilityUser) enemy;
        user.setAbilityExecuted(false);
        user.setAbilityDurationCount(0);
        manager.swapLevel2State(user, manager.getWalkingState(user));
    }

    private void check(Enemy enemy) {
        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " should not have access to ability");
    }
}
