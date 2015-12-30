package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.Layer;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerGroup;

/**
 * Handles tower controls: if they are visible and player
 * clicks outside the tower and tower contols,
 * then tower controls are hidden.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class ToggleTowerControlsListener extends ClickListener {

    private final GameBattleStage stage_battle;
    private final GameUIStage stage_ui;

    public ToggleTowerControlsListener(GameBattleStage stage, GameUIStage stage_ui) {
        this.stage_battle = stage;
        this.stage_ui = stage_ui;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (stage_battle.hasTowersOnMap()) {
            for (Layer layer : stage_battle.getIndexes()) {
                if (layer.hasChildren()) {
                    for (Actor actor : layer.getChildren()) {
                        if (actor instanceof TowerGroup) {
                            TowerGroup towerGroup = ((TowerGroup) actor);
                            TowerControlsGroup controls = stage_ui.getTowerControls();
                            Tower tower = towerGroup.getRootActor();
                            if (controls.isVisible()
                                    && !contains(controls.getUpgradeBut(), x, y)
                                    && !contains(controls.getSellBut(), x, y)) {
                                controls.setVisible(false);
                                Gdx.app.log("INFO", "Controls are hidden by stage");
                                return;
                            } else if (!controls.isVisible()
                                    && contains(tower, x, y)
                                    && tower.isActive) {
                                controls.setGroup(towerGroup);
                                controls.setVisible(true);
                                controls.toFront();
                                Gdx.app.log("INFO", tower + " controls are "
                                        + (controls.isVisible() ? "" : "in") + "visible");
                                return;
                            }
                        }
                    }
                }
            }
        }
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
