package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.model.actor.Tower;
import ua.gram.view.stage.GameBattleStage;
import ua.gram.view.stage.group.TowerControlsGroup;
import ua.gram.view.stage.group.TowerGroup;

/**
 * Handles tower controls: if they are visible and player
 * clicks outside the tower and tower contols,
 * then tower controls are hidden.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class ToggleTowerControlsListener extends ClickListener {

    private final GameBattleStage stage;

    public ToggleTowerControlsListener(GameBattleStage stage) {
        this.stage = stage;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (stage.hasTowersOnMap()) {
            for (Actor actor : stage.getTowers().getChildren()) {
                if (actor instanceof TowerGroup) {
                    TowerGroup group = ((TowerGroup) actor);
                    TowerControlsGroup controls = group.getControls();
                    Tower tower = group.getTower();
                    if (controls.isVisible()
                            && !contains(controls.getUpgradeBut(), x, y)
                            && !contains(controls.getSellBut(), x, y)
                            && !contains(tower, x, y)) {
                        controls.setVisible(false);
                        Gdx.app.log("INFO", "Controls are hidden by stage");
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
