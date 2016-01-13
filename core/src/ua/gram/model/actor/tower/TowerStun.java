package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.model.actor.weapon.FreezeWeapon;
import ua.gram.model.prototype.TowerPrototype;
import ua.gram.model.prototype.weapon.FreezeWeaponPrototype;
import ua.gram.model.strategy.tower.TowerStrategy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerStun extends Tower implements Cloneable {

    public TowerStun(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public TowerStrategy getDefaultStrategy() {
        return towerShop != null ? towerShop.getStrategyManager().getAllStrategy() : null;
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 28);
    }

    @Override
    public TowerStun clone() throws CloneNotSupportedException {
        return (TowerStun) super.clone();
    }

    @Override
    public FreezeWeaponPrototype getWeaponPrototype() {
        return (FreezeWeaponPrototype) prototype.weapon;
    }

    @Override
    public FreezeWeapon getWeapon() {
        if (weapon == null) {
            weapon = new FreezeWeapon(game.getResources(), this.getWeaponPrototype());
        }
        return (FreezeWeapon) weapon;
    }

}
