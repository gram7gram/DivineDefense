package ua.gram.controller.state.boss;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.state.StateInterface;
import ua.gram.controller.state.StateManager;
import ua.gram.controller.state.boss.level1.ActiveState;
import ua.gram.controller.state.boss.level1.DefeatedState;
import ua.gram.controller.state.boss.level1.InactiveState;
import ua.gram.controller.state.boss.level1.Level1State;
import ua.gram.controller.state.boss.level2.CommandState;
import ua.gram.controller.state.boss.level2.ExclamationState;
import ua.gram.controller.state.boss.level2.IdleState;
import ua.gram.controller.state.boss.level2.Level2State;
import ua.gram.model.actor.boss.Boss;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossStateManager extends StateManager<Boss> {


    //Level 1 states
    private DefeatedState defeatedState;
    private ActiveState activeState;
    private InactiveState inactiveState;
    //Level 2 states
    private CommandState commandState;
    private ExclamationState exclamationState;
    private IdleState idleState;

    public BossStateManager(DDGame game) {
        super(game);
    }

    @Override
    public void init() {
        defeatedState = new DefeatedState(game, this);
        activeState = new ActiveState(game, this);
        inactiveState = new InactiveState(game, this);
        commandState = new CommandState(game, this);
        exclamationState = new ExclamationState(game, this);
        idleState = new IdleState(game, this);
    }

    @Override
    public void update(Boss actor, float delta) {
        if (actor == null)
            throw new NullPointerException("EnemyStateManager cannot update NULL");

        BossStateHolder holder = actor.getStateHolder();

        boolean hasManagedState = false;

        if (holder.getCurrentLevel1State() != null) {
            try {
                holder.getCurrentLevel1State().manage(actor, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level1State on " + actor, e);
            }
        }
        if (holder.getCurrentLevel2State() != null) {
            try {
                holder.getCurrentLevel2State().manage(actor, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level2State on " + actor, e);
            }
        }
        if (!hasManagedState) {
            throw new GdxRuntimeException("Any state was managed by TowerStateManager");
        }

    }

    @Override
    public void persist(Boss actor, StateInterface<Boss> newState, int level) throws NullPointerException, GdxRuntimeException {
        BossStateHolder holder = actor.getStateHolder();
        if (newState instanceof Level1State) {
            holder.setCurrentLevel1State((Level1State) newState);
        } else if (newState instanceof Level2State) {
            holder.setCurrentLevel2State((Level2State) newState);
        } else {
            switch (level) {
                case 1:
                    holder.setCurrentLevel1State(null);
                    break;
                case 2:
                    holder.setCurrentLevel2State(null);
                    break;
                default:
                    throw new NullPointerException("Unknown " + actor + " level " + level + " state");
            }
            Log.warn(actor + " level " + level + " state is set to NULL");
        }
    }

    @Override
    public void reset(Boss actor) {
        BossStateHolder holder = actor.getStateHolder();
        holder.setCurrentLevel1State(null);
        holder.setCurrentLevel2State(null);

        Log.info(actor + " states have been reset");
    }

    public void swapLevel1State(Boss actor, Level1State state) {
        swap(actor, actor.getStateHolder().getCurrentLevel1State(), state, 1);
    }

    public void swapLevel2State(Boss actor, Level2State state) {
        swap(actor, actor.getStateHolder().getCurrentLevel1State(), state, 2);
    }

    public DefeatedState getDefeatedState() {
        return defeatedState;
    }

    public ActiveState getActiveState() {
        return activeState;
    }

    public InactiveState getInactiveState() {
        return inactiveState;
    }

    public CommandState getCommandState() {
        return commandState;
    }

    public ExclamationState getExclamationState() {
        return exclamationState;
    }

    public IdleState getIdleState() {
        return idleState;
    }
}
