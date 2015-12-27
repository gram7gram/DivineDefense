package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.misc.ProgressBar;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends Group {

    private final Tower tower;
    private final Weapon weapon;

    public TowerGroup(Tower tower, DDGame game) {
        this.tower = tower;
        this.weapon = tower.getWeapon();
        this.addActor(tower);
        this.addActor(weapon);
        this.addActor(new ProgressBar(game.getResources().getSkin(), tower));
        weapon.setVisible(false);
        weapon.toFront();
        if (DDGame.DEBUG) {
            Actor dummy = new Actor();
            dummy.setSize(3, 3);
            dummy.setPosition(tower.getOriginX() - 1, tower.getOriginY() - 1);
            dummy.setVisible(true);
            this.addActor(dummy);
        }
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
