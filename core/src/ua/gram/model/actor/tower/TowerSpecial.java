package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.Color;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;
import ua.gram.model.actor.weapon.Laser;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerSpecial extends Tower implements Cloneable {

    public TowerSpecial(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void pre_attack(Enemy victim) {

    }

    @Override
    public void attack(Enemy victim) {

    }

    @Override
    public void post_attack(Enemy victim) {

    }

    @Override
    public TowerSpecial clone() throws CloneNotSupportedException {
        return (TowerSpecial) super.clone();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon);
        ((Laser) weapon).setBackColor(Color.BLUE);
    }

}
