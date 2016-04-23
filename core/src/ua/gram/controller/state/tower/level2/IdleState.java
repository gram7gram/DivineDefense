package ua.gram.controller.state.tower.level2;

import ua.gram.DDGame;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

}
