package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerPrimary extends Tower implements Cloneable {

    public TowerPrimary(DDGame game, TowerShop towerShop, TowerPrototype prototype) {
        super(game, towerShop, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 70);
    }

    @Override
    public TowerPrimary clone() throws CloneNotSupportedException {
        return (TowerPrimary) super.clone();
    }

}
