package ua.gram.model.state.enemy.level3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level2.Level2State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class AbilityState extends Level3State {

    private float abilityDurationCount = 0;
    private EnemyStateManager manager;

    public AbilityState(DDGame game, Enemy actor) {
        super(game, actor);
    }

    @Override
    public void preManage() {
        manager = getActor().getStateManager();
        manager.swapLevel2State(manager.getIdleState());
        Gdx.app.log("INFO", getActor().getClass().getSimpleName()
                + "#" + getActor().hashCode() + " performs ability");
    }

    @Override
    public void manage() {
        Enemy enemy = getActor();
        if (enemy instanceof AbilityUser) {
            if (abilityDurationCount >= ((AbilityUser) enemy).getAbilityDuration()) {
                abilityDurationCount = 0;
                manager.swapLevel2State(manager.getWalkingState());
                manager.swapLevel3State(null);
            } else {
                ++abilityDurationCount;
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

    @Override
    public void postManage() {

    }
}
