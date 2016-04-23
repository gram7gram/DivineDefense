package ua.gram.controller.state.tower.level2;

import ua.gram.DDGame;
import ua.gram.controller.state.tower.TowerState;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level2State extends TowerState {

    public Level2State(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Tower actor) {
        initAnimation(actor);
    }

}
