package ua.gram.model.state.tower;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.StateInterface;
import ua.gram.model.state.StateManager;
import ua.gram.model.state.tower.level1.ActiveState;
import ua.gram.model.state.tower.level1.BuildingState;
import ua.gram.model.state.tower.level1.InactiveState;
import ua.gram.model.state.tower.level1.Level1State;
import ua.gram.model.state.tower.level1.PreorderState;
import ua.gram.model.state.tower.level1.SellingState;
import ua.gram.model.state.tower.level1.UpgradeState;
import ua.gram.model.state.tower.level2.AttackState;
import ua.gram.model.state.tower.level2.IdleState;
import ua.gram.model.state.tower.level2.Level2State;
import ua.gram.model.state.tower.level2.SearchState;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStateManager extends StateManager<Tower> {

    private TowerState activeState;
    private TowerState inactiveState;
    private TowerState buildingState;
    private TowerState sellingState;
    private TowerState attackState;
    private TowerState searchState;
    private TowerState idleState;
    private TowerState preorderState;
    private TowerState upgradeState;

    public TowerStateManager(DDGame game) {
        super(game);
    }

    @Override
    public void init(Tower actor) {
        if (activeState == null) activeState = new ActiveState(game);
        if (inactiveState == null) inactiveState = new InactiveState(game);
        if (buildingState == null) buildingState = new BuildingState(game);
        if (sellingState == null) sellingState = new SellingState(game);
        if (attackState == null) attackState = new AttackState(game);
        if (searchState == null) searchState = new SearchState(game);
        if (idleState == null) idleState = new IdleState(game);
        if (preorderState == null) preorderState = new PreorderState(game);
        if (upgradeState == null) upgradeState = new UpgradeState(game);
    }

    @Override
    public void update(Tower tower, float delta) {
        if (tower == null) return;

        TowerStateHolder holder = tower.getStateHolder();

        if (holder.getCurrentLevel1State() != null) try {
            holder.getCurrentLevel1State().manage(tower, delta);
        } catch (Exception e) {
            Log.exc("Could not manage Level1State on " + tower, e);
        }
        if (holder.getCurrentLevel2State() != null) try {
            holder.getCurrentLevel2State().manage(tower, delta);
        } catch (Exception e) {
            Log.exc("Could not manage Level2State on " + tower, e);
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

    public TowerState getActiveState() {
        return activeState;
    }

    public TowerState getInactiveState() {
        return inactiveState;
    }

    public TowerState getBuildingState() {
        return buildingState;
    }

    public TowerState getSellingState() {
        return sellingState;
    }

    public TowerState getAttackState() {
        return attackState;
    }

    public TowerState getSearchState() {
        return searchState;
    }

    public TowerState getIdleState() {
        return idleState;
    }

    public TowerState getPreorderState() {
        return preorderState;
    }

    public TowerState getUpgradeState() {
        return upgradeState;
    }
}
