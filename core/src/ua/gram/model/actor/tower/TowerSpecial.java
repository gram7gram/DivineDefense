package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.Color;
import ua.gram.DDGame;
import ua.gram.model.actor.weapon.LaserWeapon;
import ua.gram.model.prototype.LaserWeaponPrototype;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerSpecial extends Tower implements Cloneable {

    public TowerSpecial(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 78);
    }

    @Override
    public TowerSpecial clone() throws CloneNotSupportedException {
        return (TowerSpecial) super.clone();
    }

    @Override
    public LaserWeaponPrototype getWeaponPrototype() {
        return (LaserWeaponPrototype) prototype.weapon;
    }

    @Override
    public LaserWeapon getWeapon() {
        if (weapon == null) {
            weapon = new LaserWeapon(game.getResources(), this.getParent(), null);
            ((LaserWeapon) weapon).setBackColor(Color.BLUE);
        }
        return (LaserWeapon) weapon;
    }
}
