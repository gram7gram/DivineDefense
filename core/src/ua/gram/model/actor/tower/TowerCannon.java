package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.Color;
import ua.gram.DDGame;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;
import ua.gram.model.actor.weapon.Laser;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerCannon extends Tower implements Cloneable {

    public TowerCannon(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public TowerCannon clone() throws CloneNotSupportedException {
        return (TowerCannon) super.clone();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon);
        ((Laser) weapon).setBackColor(Color.ORANGE);
    }

}
