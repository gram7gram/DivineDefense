package ua.gram.model.state.enemy.level3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.AbilityUser;
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
        manager = enemy.getSpawner().getStateManager();
        manager.swapLevel2State(enemy, manager.getIdleState());
        EnemyAnimationProvider provider = enemy.getSpawner().getAnimationProvider();
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                Animator.Types.ABILITY,
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        executed = false;
        abilityDurationCount = 0;
        Gdx.app.log("INFO", enemy + " performs ability");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        if (enemy instanceof AbilityUser) {
            if (abilityDurationCount >= ((AbilityUser) enemy).getAbilityDuration()) {
                abilityDurationCount = 0;
                manager.swapLevel2State(enemy, manager.getWalkingState());
                manager.swapLevel3State(enemy, null);
                Gdx.app.log("INFO", "Ability is off!");
            } else {
                abilityDurationCount += delta;
                if (!executed) {
                    executed = true;
                    try {
                        Vector2 posToSpawn = enemy.getPosition();
                        Vector2 next = enemy.getPath().getNextPosition(posToSpawn);
                        enemy.getSpawner().spawn("EnemySoldier", next);
                        Gdx.app.log("INFO", "Ability!");
                    } catch (Exception e) {
                        Gdx.app.log("WARN", "Could not execute ability for Summoner!");
                    }
                }
            }
        }
    }

    @Override
    public void postManage(Enemy enemy) {
        executed = false;
        abilityDurationCount = 0;
    }
}
