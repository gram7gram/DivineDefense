package ua.gram.model.state.tower.level2;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.tower.TowerState;

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

    @Override
    public void manage(Tower actor, float delta) {

    }

    @Override
    public void postManage(Tower actor) {

    }

}
