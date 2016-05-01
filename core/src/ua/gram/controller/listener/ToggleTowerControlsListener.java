package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.controller.stage.StageHolder;
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
public class ToggleTowerControlsListener extends ClickListener {

    private StageHolder holder;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        if (!holder.getBattleStage().hasTowersOnMap()) return;

        TowerControls controls = holder.getUiStage().getTowerControls();

        TowerGroup tower = isClickedOnTower(x, y);

        if (tower != null && tower.getRootActor().isActiveState()) {
            controls.setTower(tower);
            controls.showControls();
            Log.info(tower.getRootActor() + " controls are "
                    + (controls.isVisible() ? "" : "in") + "visible");
            return;
        }

        if (controls.isVisible()) {
            if (event.getRelatedActor() != controls.getUpgradeBut()
                    && event.getRelatedActor() != controls.getSellBut()) {
                controls.hideControls();
                Log.info("Controls are hidden by stage");
            }
        }

    }

    private TowerGroup isClickedOnTower(float x, float y) {
        for (TowerGroup towerGroup : holder.getBattleStage().getTowersOnMap()) {
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

    public void setStageHolder(StageHolder stageHolder) {
        if (stageHolder == null)
            throw new NullPointerException("Missing StageHolder for listener");

        this.holder = stageHolder;
    }
}
