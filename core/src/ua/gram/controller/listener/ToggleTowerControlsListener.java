package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.controller.Log;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerGroup;

/**
 * Handles towerGroup controls: if they are visible and player
 * clicks outside the towerGroup and towerGroup contols,
 * then towerGroup controls are hidden.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class ToggleTowerControlsListener extends ClickListener {

    private final BattleStage stage_battle;
    private final UIStage stage_ui;

    public ToggleTowerControlsListener(BattleStage stage, UIStage stage_ui) {
        this.stage_battle = stage;
        this.stage_ui = stage_ui;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        if (!stage_battle.hasTowersOnMap()) return;

        TowerControlsGroup controls = stage_ui.getTowerControls();

        TowerGroup tower = isClickedOnTower(x, y);

        if (tower != null && tower.getRootActor().isActiveState()) {
            controls.setGroup(tower);
            controls.setVisible(true);
            controls.toFront();
            Log.info(tower.getRootActor() + " controls are "
                    + (controls.isVisible() ? "" : "in") + "visible");
            return;
        }

        if (controls.isVisible()) {
            if (!contains(controls.getUpgradeBut(), x, y)
                    && !contains(controls.getSellBut(), x, y)) {
                controls.resetObject();
                Log.info("Controls are hidden by stage");
            }
        }

    }

    private TowerGroup isClickedOnTower(float x, float y) {
        for (TowerGroup towerGroup : stage_battle.getTowersOnMap()) {
            if (contains(towerGroup, x, y)) {
                return towerGroup;
            }
        }

        return null;
    }

    /**
     * Checks if poiter is in the actor's bounds.
     *
     * @param actor the actor to check
     * @param xCord x ccordinate of the pointer
     * @param yCord y coordinate of the pointer
     * @return true if (x,y) is in the actor's bounds
     */
    private boolean contains(Actor actor, float xCord, float yCord) {
        return (xCord > actor.getX()
                && xCord < actor.getX() + actor.getWidth()
                && yCord > actor.getY()
                && yCord < actor.getY() + actor.getHeight()
        );
    }
}
