package ua.gram.model.state.enemy;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.State;
import ua.gram.model.state.StateManager;
import ua.gram.model.state.enemy.level1.*;
import ua.gram.model.state.enemy.level2.*;
import ua.gram.model.state.enemy.level3.*;
import ua.gram.model.state.enemy.level4.*;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemyStateManager extends StateManager<Enemy> {

    //Level 1
    private final DeathState deathState;
    private final FinishState finishState;
    private final SpawnState spawnState;
    private final ActiveState activeState;
    private final InactiveState inactiveState;
    //Level 2
    private final IdleState idleState;
    private final WalkingState walkingState;
    //Level 3
    private final AbilityState abilityState;
    //Level 4
    private final StunState stunState;

    public EnemyStateManager(DDGame game, Enemy enemy) {
        super(game, enemy);
        activeState = new ActiveState(game, enemy);
        walkingState = new WalkingState(game, enemy);
        spawnState = new SpawnState(game, enemy);
        stunState = new StunState(game, enemy);
        idleState = new IdleState(game, enemy);
        finishState = new FinishState(game, enemy);
        deathState = new DeathState(game, enemy);
        abilityState = new AbilityState(game, enemy);
        inactiveState = new InactiveState(game, enemy);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {
        if (actor.getCurrentLevel1State() != null) actor.getCurrentLevel1State().manage();
        if (actor.getCurrentLevel2State() != null) actor.getCurrentLevel2State().manage();
        if (actor.getCurrentLevel3State() != null) actor.getCurrentLevel3State().manage();
        if (actor.getCurrentLevel4State() != null) actor.getCurrentLevel4State().manage();
    }

    @Override
    public void swap(State current, State after){
        if (current == null && after == null)
            throw new IllegalArgumentException("Could not swap both empty states");
        if (current != null)
            current.postManage();
        if (after != null) {
            persist(actor, after);
            after.preManage();
        }
    }

    public void swapLevel1State(Level1State state) {
        swap(actor.getCurrentLevel1State(), state);
    }

    public void swapLevel2State(Level2State state) {
        swap(actor.getCurrentLevel2State(), state);
    }

    public void swapLevel3State(Level3State state) {
        swap(actor.getCurrentLevel3State(), state);
    }

    public void swapLevel4State(Level4State state) {
        swap(actor.getCurrentLevel4State(), state);
    }

    @Override
    public void persist(Enemy actor, State newState) {
        if (newState == null) return;
        if (newState instanceof Level1State) {
            actor.setCurrentLevel1State((Level1State) newState);
        } else if (newState instanceof Level2State) {
            actor.setCurrentLevel2State((Level2State) newState);
        } else if (newState instanceof Level3State) {
            actor.setCurrentLevel3State((Level3State) newState);
        } else if (newState instanceof Level4State) {
            actor.setCurrentLevel4State((Level4State) newState);
        }
    }

    public DeathState getDeathState() {
        return deathState;
    }

    public FinishState getFinishState() {
        return finishState;
    }

    public IdleState getIdleState() {
        return idleState;
    }

    public StunState getStunState() {
        return stunState;
    }

    public SpawnState getSpawnState() {
        return spawnState;
    }

    public WalkingState getWalkingState() {
        return walkingState;
    }

    public ActiveState getActiveState() {
        return activeState;
    }

    public InactiveState getInactiveState() {
        return inactiveState;
    }

    public AbilityState getAbilityState() {
        return abilityState;
    }
}
