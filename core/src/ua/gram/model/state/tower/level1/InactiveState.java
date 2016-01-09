package ua.gram.model.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class InactiveState extends Level1State {

    public InactiveState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return null;
    }

}
