package ua.gram.controller.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.state.tower.TowerState;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level1State extends TowerState {

    public Level1State(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Tower actor) {
        initAnimation(actor);
    }

}
