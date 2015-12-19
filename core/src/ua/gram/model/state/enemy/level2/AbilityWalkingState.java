package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationChanger;
import ua.gram.model.Animator;
import ua.gram.model.EnemyPath;
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
        walkingAnimationChanger = new EnemyAnimationChanger(Animator.Types.ABILITY);
    }

    @Override
    protected void move(Enemy enemy, float delta, int x, int y) {

        if (!(enemy instanceof AbilityUser))
            throw new IllegalArgumentException(enemy + " is not AbilityUser. It should not have access to such method");

        if (Path.compare(enemy.getCurrentPosition(), basePosition)) {
            Gdx.app.log("INFO", enemy + " position equals to Base. Removing enemy");
            remove(enemy);
            return;
        }

        EnemyPath path = enemy.getPath();
        if (path == null) throw new NullPointerException("Missing path for " + enemy);

        Vector2 current = enemy.getCurrentDirection();
        Vector2 dir = path.nextDirection();

        boolean isSameDirection = Path.compare(dir, current);

        if (!isSameDirection) {
            walkingAnimationChanger.update(enemy, dir);
            enemy.addAction(
                    Actions.parallel(
                            Actions.run(walkingAnimationChanger),
                            moveBy(enemy, dir)
                    )
            );
        } else if (((AbilityUser) enemy).isAbilityPossible(1)) {
            EnemyStateManager manager = enemy.getSpawner().getStateManager();
            stateSwapper.update(enemy, enemy.getCurrentLevel3State(), manager.getAbilityState(), 3);
            walkingAnimationChanger.update(enemy);
            float time = ((AbilityUser) enemy).getAbilityDuration();
            enemy.addAction(
                    Actions.sequence(
                            Actions.parallel(
                                    Actions.run(stateSwapper),
                                    Actions.run(walkingAnimationChanger)
                            ),
                            Actions.delay(time, moveBy(enemy, dir))
                    ));
        } else {
            enemy.setCurrentDirection(dir);
            enemy.addAction(moveBy(enemy, dir));
        }
    }

}
