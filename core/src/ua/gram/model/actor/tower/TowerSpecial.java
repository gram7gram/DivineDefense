package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerSpecial extends Tower implements Cloneable {

    public TowerSpecial(DDGame game, TowerShop towerShop, TowerPrototype prototype) {
        super(game, towerShop, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 78);
    }

    @Override
    public TowerSpecial clone() throws CloneNotSupportedException {
        return (TowerSpecial) super.clone();
    }
}
