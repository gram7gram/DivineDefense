package ua.gram.view.stage.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.ProgressBar;
import ua.gram.model.actor.Range;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.view.stage.GameBattleStage;

import javax.print.attribute.IntegerSyntax;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends Group {

    private final Tower tower;
    private final Weapon weapon;
    private final Range range;
    private final TowerControlsGroup controls;

    public TowerGroup(Tower tower, Weapon weapon, Range range, TowerControlsGroup controls, ProgressBar bar) {
        this.tower = tower;
        this.weapon = weapon;
        this.range = range;
        this.controls = controls;
        this.addActor(tower);
        this.addActor(weapon);
        this.addActor(range);
        this.addActor(controls);
        this.addActor(bar);
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + tower + " is OK");
    }

    public void toggleControls() {
        controls.toFront();
        controls.setVisible(!controls.isVisible());
        range.toBack();
        range.setVisible(controls.isVisible());
        Gdx.app.log("INFO", "Tower controls are " + (controls.isVisible() ? "" : "in") + "visible");
    }

    public Tower getTower() {
        return tower;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Range getRange() {
        return range;
    }

    public TowerControlsGroup getControls() {
        return controls;
    }
}
