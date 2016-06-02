package ua.gram.controller.state.enemy;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.animation.enemy.EnemyAnimationChanger;
import ua.gram.controller.state.StateInterface;
import ua.gram.controller.state.StateManager;
import ua.gram.controller.state.StateSwapper;
import ua.gram.controller.state.enemy.level1.ActiveState;
import ua.gram.controller.state.enemy.level1.DeadState;
import ua.gram.controller.state.enemy.level1.FinishState;
import ua.gram.controller.state.enemy.level1.InactiveState;
import ua.gram.controller.state.enemy.level1.Level1State;
import ua.gram.controller.state.enemy.level1.SpawnState;
import ua.gram.controller.state.enemy.level2.AbilityWalkingState;
import ua.gram.controller.state.enemy.level2.IdleState;
import ua.gram.controller.state.enemy.level2.Level2State;
import ua.gram.controller.state.enemy.level2.WalkingState;
import ua.gram.controller.state.enemy.level3.AbilityState;
import ua.gram.controller.state.enemy.level3.Level3State;
import ua.gram.controller.state.enemy.level4.Level4State;
import ua.gram.controller.state.enemy.level4.StunState;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class EnemyStateManager extends StateManager<Enemy> {

    protected final StateSwapper<Enemy> stateSwapper;
    protected final EnemyAnimationChanger animationChanger;
    //Level 1
    private DeadState deadState;
    private FinishState finishState;
    private SpawnState spawnState;
    private ActiveState activeState;
    private InactiveState inactiveState;
    //Level 2
    private IdleState idleState;
    private WalkingState walkingState;
    private AbilityWalkingState abilityWalkingState;
    //Level 3
    private AbilityState abilityState;
    //Level 4
    private StunState stunState;

    public EnemyStateManager(DDGame game) {
        super(game);
        stateSwapper = new StateSwapper<Enemy>();
        animationChanger = new EnemyAnimationChanger();
    }

    @Override
    public void init() {
        stunState = new StunState(game, this);
        deadState = new DeadState(game, this);
        idleState = new IdleState(game, this);
        spawnState = new SpawnState(game, this);
        activeState = new ActiveState(game, this);
        finishState = new FinishState(game, this);
        walkingState = new WalkingState(game, this);
        abilityState = new AbilityState(game, this);
        inactiveState = new InactiveState(game, this);
        abilityWalkingState = new AbilityWalkingState(game, this);
    }

    @Override
    public void update(Enemy enemy, float delta) {
        if (enemy == null)
            throw new NullPointerException("EnemyStateManager cannot update NULL");

        EnemyStateHolder holder = enemy.getStateHolder();

        boolean hasManagedState = false;

        if (holder.getCurrentLevel1State() != null) {
            try {
                holder.getCurrentLevel1State().manage(enemy, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level1State on " + enemy, e);
            }
        }
        if (holder.getCurrentLevel2State() != null) {
            try {
                holder.getCurrentLevel2State().manage(enemy, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level2State on " + enemy, e);
            }
        }
        if (holder.getCurrentLevel3State() != null) {
            try {
                holder.getCurrentLevel3State().manage(enemy, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level3State on " + enemy, e);
            }
        }
        if (holder.getCurrentLevel4State() != null) {
            try {
                holder.getCurrentLevel4State().manage(enemy, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level4State on " + enemy, e);
            }
        }

        if (!hasManagedState) {
            throw new GdxRuntimeException("No state was managed by TowerStateManager");
        }
    }

    public void swapLevel1State(Enemy enemy, Level1State state) {
        swap(enemy, enemy.getStateHolder().getCurrentLevel1State(), state, 1);
    }

    public void swapLevel2State(Enemy enemy, Level2State state) {
        swap(enemy, enemy.getStateHolder().getCurrentLevel2State(), state, 2);
    }

    public void swapLevel3State(Enemy enemy, Level3State state) {
        swap(enemy, enemy.getStateHolder().getCurrentLevel3State(), state, 3);
    }

    public void swapLevel4State(Enemy enemy, Level4State state) {
        swap(enemy, enemy.getStateHolder().getCurrentLevel4State(), state, 4);
    }

    @Override
    public void persist(Enemy enemy, StateInterface<Enemy> newState, int level) throws NullPointerException, GdxRuntimeException {
        EnemyStateHolder holder = enemy.getStateHolder();
        if (newState instanceof Level1State) {
            holder.setCurrentLevel1State((Level1State) newState);
        } else if (newState instanceof Level2State) {
            holder.setCurrentLevel2State((Level2State) newState);
        } else if (newState instanceof Level3State) {
            holder.setCurrentLevel3State((Level3State) newState);
        } else if (newState instanceof Level4State) {
            holder.setCurrentLevel4State((Level4State) newState);
        } else {
            switch (level) {
                case 1:
                    holder.setCurrentLevel1State(null);
                    break;
                case 2:
                    holder.setCurrentLevel2State(null);
                    break;
                case 3:
                    holder.setCurrentLevel3State(null);
                    break;
                case 4:
                    holder.setCurrentLevel4State(null);
                    break;
                default:
                    throw new NullPointerException("Unknown " + enemy + " level " + level + " state");
            }
            Log.warn(enemy + " level " + level + " state is set to NULL");
        }
    }

    @Override
    public void reset(Enemy enemy) {
        EnemyStateHolder holder = enemy.getStateHolder();
        holder.setCurrentLevel1State(null);
        holder.setCurrentLevel2State(null);
        holder.setCurrentLevel3State(null);
        holder.setCurrentLevel4State(null);
        Log.info(enemy + " states have been reset");
    }

    public DeadState getDeadState() {
        return deadState;
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

    public WalkingState getWalkingState(Enemy enemy) {
        return enemy instanceof AbilityUser ? abilityWalkingState : walkingState;
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

    public synchronized void swap(Enemy actor, Level1State before, Level1State after) {
        this.swap(actor, before, after, 1);
    }

    public synchronized void swap(Enemy actor, Level1State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel1State(), after, 1);
    }

    public synchronized void swap(Enemy actor, Level2State before, Level2State after) {
        this.swap(actor, before, after, 2);
    }

    public synchronized void swap(Enemy actor, Level2State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel2State(), after, 2);
    }

    public synchronized void swap(Enemy actor, Level3State before, Level3State after) {
        this.swap(actor, before, after, 3);
    }

    public synchronized void swap(Enemy actor, Level3State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel3State(), after, 3);
    }

    public synchronized void swap(Enemy actor, Level4State before, Level4State after) {
        this.swap(actor, before, after, 4);
    }

    public synchronized void swap(Enemy actor, Level4State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel4State(), after, 4);
    }

    public StateSwapper<Enemy> getStateSwapper() {
        return stateSwapper;
    }

    public EnemyAnimationChanger getAnimationChanger() {
        return animationChanger;
    }
}
