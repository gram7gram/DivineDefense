package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.controller.event.TowerControlsToggleEvent;
import ua.gram.model.group.TowerControls;
import ua.gram.model.group.TowerGroup;
import ua.gram.utils.Log;

/**
 * Handles towerGroup controls: if they are visible and player
 * clicks outside the towerGroup and towerGroup contols,
 * then towerGroup controls are hidden.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerControlsToggleListener implements EventListener {

    private final TowerControls controls;

    public TowerControlsToggleListener(TowerControls controls) {
        this.controls = controls;
    }

    @Override
    public boolean handle(Event e) {
        if (!(e instanceof TowerControlsToggleEvent)) return false;

        TowerControlsToggleEvent event = (TowerControlsToggleEvent) e;

        controls.hideControls();

        switch (event.getState()) {
            case VISIBLE:
                TowerGroup towerGroup = event.getTargetGroup();
                controls.setTower(towerGroup);
                controls.showControls();
                break;
        }

        Log.info("Controls are "
                + (controls.isVisible() ? "" : "in") + "visible");

        return true;
    }
}
