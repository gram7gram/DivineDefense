package ua.gram.controller.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ActiveState extends Level1State {

    public ActiveState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

}
