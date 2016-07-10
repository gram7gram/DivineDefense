package ua.gram.controller.event;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerControlsToggleEvent extends Event {

    private final Types.TowerControls state;
    private final TowerGroup target;

    public TowerControlsToggleEvent(TowerGroup target, Types.TowerControls state) {
        this.state = state;
        this.target = target;
    }

    public Types.TowerControls getState() {
        return state;
    }

    public TowerGroup getTargetGroup() {
        return target;
    }
}
