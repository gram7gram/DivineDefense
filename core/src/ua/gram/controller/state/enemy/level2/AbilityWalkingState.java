package ua.gram.controller.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.controller.animation.enemy.EnemyAnimationChanger;
import ua.gram.controller.state.StateSwapper;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityWalkingState extends WalkingState {

    public AbilityWalkingState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    private AbilityUser convert(Enemy enemy) {
        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " is not AbilityUser. It should not have access to such method");

        return (AbilityUser) enemy;
    }

    @Override
    protected void move(final Enemy enemy, float delta, int x, int y) {

        final AbilityUser user = convert(enemy);

        if (!user.isAbilityActive()) {
            EnemyAnimationChanger animationChanger = getManager().getAnimationChanger();
            StateSwapper<Enemy> stateSwapper = getManager().getStateSwapper();
            if (user.isAbilityPossible()) {
                final Vector2 dir = user.getPath().nextDirection();

                animationChanger.update(user, dir, Types.EnemyState.ABILITY);

                stateSwapper.update(user,
                        user.getStateHolder().getCurrentLevel3State(),
                        manager.getAbilityState(), 3);

                user.addAction(
                        Actions.sequence(
                                Actions.run(stateSwapper),
                                Actions.delay(user.getAbilityDuration(),
                                        Actions.sequence(
                                                Actions.run(animationChanger.updateValues(user, dir, getType())),
                                                moveBy(user, dir)
                                        ))
                        )
                );
            } else if (!user.isAbilityExecuted()) {
                final Vector2 dir = user.getPath().nextDirection();
                user.addAction(
                        Actions.sequence(
                                Actions.run(animationChanger.updateValues(user, dir, getType())),
                                moveBy(user, dir)
                        ));
            } else {
                final Vector2 dir = user.getPath().peekNextDirection();
                user.addAction(
                        Actions.run(animationChanger.updateValues(user, dir, getType()))
                );
            }
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        super.postManage(enemy);

        AbilityUser user = convert(enemy);
        user.setAbilityDurationCount(0);
        user.setAbilityExecuted(false);
    }
}
