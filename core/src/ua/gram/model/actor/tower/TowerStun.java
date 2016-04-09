package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.strategy.tower.TowerStrategy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerStun extends Tower implements Cloneable {

    public TowerStun(DDGame game, TowerShop towerShop, TowerPrototype prototype) {
        super(game, towerShop, prototype);
    }

    @Override
    public TowerStrategy getDefaultStrategy() {
        return towerShop != null ? towerShop.getStrategyManager().getAllStrategy() : null;
    }

    @Override
    public void update(float delta) {
        setOrigin(getX() + animationWidth / 2f, getY() + 28);
    }

    @Override
    public TowerStun clone() throws CloneNotSupportedException {
        return (TowerStun) super.clone();
    }

}
