package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.strategy.tower.TowerStrategy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerSecondary extends Tower implements Cloneable {

    public TowerSecondary(DDGame game, TowerShop towerShop, TowerPrototype prototype) {
        super(game, towerShop, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 78);
    }

    @Override
    public TowerSecondary clone() throws CloneNotSupportedException {
        return (TowerSecondary) super.clone();
    }

    @Override
    public TowerStrategy getDefaultStrategy() {
        return towerShop != null ? towerShop.getStrategyManager().getAoeStrategy() : null;
    }
}
