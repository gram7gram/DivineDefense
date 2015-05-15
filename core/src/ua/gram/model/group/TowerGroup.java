package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.ProgressBar;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends Group {

    private final Tower tower;
    private final Weapon weapon;

    public TowerGroup(Tower tower, Weapon weapon, ProgressBar bar) {
        this.tower = tower;
        this.weapon = weapon;
        this.addActor(tower);
        this.addActor(weapon);
        this.addActor(bar);
        tower.setWeapon(weapon);
        weapon.setVisible(false);
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + tower + " is OK");
    }

    public Tower getTower() {
        return tower;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
