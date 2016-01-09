package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityWalkingState extends WalkingState {

    public AbilityWalkingState(DDGame game) {
        super(game);
    }

    @Override
    protected synchronized void move(final Enemy enemy, float delta, int x, int y) {

        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " is not AbilityUser. It should not have access to such method");

        AbilityUser user = (AbilityUser) enemy;

        if (!user.isAbilityActive()) {
            if (user.isAbilityPossible()) {
                final Vector2 dir = user.getPath().nextDirection();
                EnemyStateManager manager = user.getSpawner().getStateManager();
                user.addAction(
                        Actions.sequence(
                                Actions.run(animationChanger.update(user, dir, Types.EnemyState.ABILITY)),
                                Actions.run(stateSwapper.update(enemy,
                                        user.getStateHolder().getCurrentLevel3State(),
                                        manager.getAbilityState(), 3)),
                                Actions.delay(user.getAbilityDuration(), Actions.sequence(
                                        Actions.run(animationChanger.update(user, dir, getType())),
                                        moveBy(user, dir)
                                ))
                        )
                );
            } else if (!user.isAbilityExecuted()) {
                final Vector2 dir = user.getPath().nextDirection();
                user.addAction(Actions.sequence(
                        Actions.run(animationChanger.update(user, dir, getType())),
                        moveBy(user, dir)
                ));
            } else {
                final Vector2 dir = user.getPath().peekNextDirection();
                user.addAction(
                        Actions.run(animationChanger.update(user, dir, getType()))
                );
            }
        }
    }

}
