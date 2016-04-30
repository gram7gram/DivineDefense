package ua.gram.controller.state.tower.level2;

import ua.gram.DDGame;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

}
