package ua.gram.controller.state.tower;

import ua.gram.DDGame;
import ua.gram.controller.state.State;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.utils.Log;

/**
 * Representation of Tower at the moment of time.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class TowerState extends State<Tower> {

    protected final TowerStateManager manager;

    public TowerState(DDGame game, TowerStateManager manager) {
        super(game);
        this.manager = manager;
        Log.info("Tower " + name() + " state is OK");
    }

    @Override
    public void preManage(Tower actor) {
        super.preManage(actor);
        manager.getAnimationChanger().update(actor, getType(),
                Types.getTowerLevelType(actor.getProperty().getTowerLevel()));
    }

    protected abstract Types.TowerState getType();
}
