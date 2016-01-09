package ua.gram.model.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.tower.TowerState;

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
