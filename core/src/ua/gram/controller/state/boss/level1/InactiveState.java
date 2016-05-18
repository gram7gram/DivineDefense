package ua.gram.controller.state.boss.level1;

import ua.gram.DDGame;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class InactiveState extends Level1State {

    public InactiveState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return null;
    }
}
