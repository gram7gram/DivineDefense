package ua.gram.model.state.tower.level1;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PreorderState extends InactiveState {

    public PreorderState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

    @Override
    public void preManage(Tower actor) {
        super.preManage(actor);
        actor.setVisible(true);
        actor.setTouchable(Touchable.disabled);
        Log.info(actor + " state: " + getType());
    }
}
