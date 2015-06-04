package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.Color;
import ua.gram.DDGame;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;
import ua.gram.model.actor.weapon.Laser;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStun extends Tower implements Cloneable {

    public TowerStun(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public TowerStun clone() throws CloneNotSupportedException {
        return (TowerStun) super.clone();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon);
        ((Laser) weapon).setBackColor(Color.GREEN);
    }

}
