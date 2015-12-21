package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ua.gram.DDGame;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityWalkingState extends WalkingState {

    public AbilityWalkingState(DDGame game) {
        super(game);
    }

    @Override
    protected synchronized void move(final Enemy enemy, float delta, int x, int y, final Vector2 dir) {

        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " is not AbilityUser. It should not have access to such method");

        Vector2 current = enemy.getCurrentDirection();

        boolean isSameDirection = Path.compare(dir, current);

        if (((AbilityUser) enemy).isAbilityPossible()) {
            EnemyStateManager manager = enemy.getSpawner().getStateManager();

            float seconds = ((AbilityUser) enemy).getAbilityDuration();

            enemy.addAction(
                    Actions.sequence(
                            Actions.run(animationChanger.update(enemy, dir, Animator.Types.ABILITY)),
                            Actions.run(stateSwapper.update(enemy, enemy.getCurrentLevel3State(), manager.getAbilityState(), 3)),
                            Actions.delay(seconds, Actions.sequence(
                                    Actions.run(animationChanger.update(enemy, dir, Animator.Types.WALKING)),
                                    moveBy(enemy, dir)
                            ))
                    )
            );
        } else {//else if (enemy.hasReachedCheckpoint) {
            if (!isSameDirection) {
                enemy.addAction(
                        Actions.sequence(
                                Actions.run(animationChanger.update(enemy, dir, Animator.Types.WALKING)),
                                moveBy(enemy, dir)
                        )
                );
            } else {
                enemy.setCurrentDirection(dir);
                enemy.addAction(Actions.sequence(
                        Actions.run(animationChanger.update(enemy, dir, Animator.Types.WALKING)),
                        moveBy(enemy, dir)
                ));
            }
        }
    }

}
