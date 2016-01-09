package ua.gram.model.state.tower;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.StateInterface;
import ua.gram.model.state.StateManager;
import ua.gram.model.state.tower.level1.ActiveState;
import ua.gram.model.state.tower.level1.BuildingState;
import ua.gram.model.state.tower.level1.InactiveState;
import ua.gram.model.state.tower.level1.PreorderState;
import ua.gram.model.state.tower.level1.SellingState;
import ua.gram.model.state.tower.level2.AttackState;
import ua.gram.model.state.tower.level2.IdleState;
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
    }

    @Override
    public void update(Tower actor, float delta) {

    }

    @Override
    public void persist(Tower actor, StateInterface newState, int level) throws NullPointerException, GdxRuntimeException {

    }

    @Override
    public void reset(Tower actor) {

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
}
