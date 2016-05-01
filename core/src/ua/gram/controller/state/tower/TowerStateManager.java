package ua.gram.controller.state.tower;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.animation.tower.TowerAnimationChanger;
import ua.gram.controller.state.StateInterface;
import ua.gram.controller.state.StateManager;
import ua.gram.controller.state.StateSwapper;
import ua.gram.controller.state.tower.level1.ActiveState;
import ua.gram.controller.state.tower.level1.BuildingState;
import ua.gram.controller.state.tower.level1.InactiveState;
import ua.gram.controller.state.tower.level1.Level1State;
import ua.gram.controller.state.tower.level1.PreorderState;
import ua.gram.controller.state.tower.level1.SellingState;
import ua.gram.controller.state.tower.level1.UpgradeState;
import ua.gram.controller.state.tower.level2.AttackState;
import ua.gram.controller.state.tower.level2.IdleState;
import ua.gram.controller.state.tower.level2.Level2State;
import ua.gram.controller.state.tower.level2.SearchState;
import ua.gram.model.actor.tower.Tower;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStateManager extends StateManager<Tower> {

    protected final StateSwapper<Tower> stateSwapper;
    protected final TowerAnimationChanger animationChanger;

    private ActiveState activeState;
    private InactiveState inactiveState;
    private BuildingState buildingState;
    private SellingState sellingState;
    private AttackState attackState;
    private SearchState searchState;
    private IdleState idleState;
    private PreorderState preorderState;
    private UpgradeState upgradeState;

    public TowerStateManager(DDGame game) {
        super(game);
        stateSwapper = new StateSwapper<>();
        animationChanger = new TowerAnimationChanger();
    }

    @Override
    public void init() {
        activeState = new ActiveState(game, this);
        inactiveState = new InactiveState(game, this);
        buildingState = new BuildingState(game, this);
        sellingState = new SellingState(game, this);
        attackState = new AttackState(game, this);
        searchState = new SearchState(game, this);
        idleState = new IdleState(game, this);
        preorderState = new PreorderState(game, this);
        upgradeState = new UpgradeState(game, this);
    }

    @Override
    public void update(Tower tower, float delta) {
        if (tower == null)
            throw new NullPointerException("TowerStateManager cannot update NULL");

        TowerStateHolder holder = tower.getStateHolder();

        boolean hasManagedState = false;

        if (holder.getCurrentLevel1State() != null) {
            try {
                holder.getCurrentLevel1State().manage(tower, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level1State on " + tower, e);
            }
        }
        if (holder.getCurrentLevel2State() != null) {
            try {
                holder.getCurrentLevel2State().manage(tower, delta);
                hasManagedState = true;
            } catch (Exception e) {
                Log.exc("Could not manage Level2State on " + tower, e);
            }
        }

        if (!hasManagedState) {
            throw new GdxRuntimeException("Any state was managed by TowerStateManager");
        }
    }

    @Override
    public void persist(Tower tower, StateInterface<Tower> newState, int level) throws NullPointerException, GdxRuntimeException {
        TowerStateHolder holder = tower.getStateHolder();
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
                    throw new NullPointerException("Unknown " + tower + " level " + level + " state");
            }
            Log.warn(tower + " level " + level + " state is set to NULL");
        }
    }

    @Override
    public void reset(Tower tower) {
        TowerStateHolder holder = tower.getStateHolder();
        holder.setCurrentLevel1State(null);
        holder.setCurrentLevel2State(null);
        Log.info(tower + " states have been reset");
    }

    public ActiveState getActiveState() {
        return activeState;
    }

    public InactiveState getInactiveState() {
        return inactiveState;
    }

    public BuildingState getBuildingState() {
        return buildingState;
    }

    public SellingState getSellingState() {
        return sellingState;
    }

    public AttackState getAttackState() {
        return attackState;
    }

    public SearchState getSearchState() {
        return searchState;
    }

    public IdleState getIdleState() {
        return idleState;
    }

    public PreorderState getPreorderState() {
        return preorderState;
    }

    public UpgradeState getUpgradeState() {
        return upgradeState;
    }

    public StateSwapper<Tower> getStateSwapper() {
        return stateSwapper;
    }

    public TowerAnimationChanger getAnimationChanger() {
        return animationChanger;
    }

    public synchronized void swap(Tower actor, Level1State before, Level1State after) {
        this.swap(actor, before, after, 1);
    }

    public synchronized void swap(Tower actor, Level1State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel1State(), after, 1);
    }

    public synchronized void swap(Tower actor, Level2State before, Level2State after) {
        this.swap(actor, before, after, 2);
    }

    public synchronized void swap(Tower actor, Level2State after) {
        this.swap(actor, actor.getStateHolder().getCurrentLevel2State(), after, 2);
    }
}
