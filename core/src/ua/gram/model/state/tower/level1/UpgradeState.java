package ua.gram.model.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.TowerProperty;
import ua.gram.model.actor.misc.ProgressBar;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.prototype.tower.TowerPropertyPrototype;
import ua.gram.model.state.tower.TowerStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UpgradeState extends InactiveState {

    public UpgradeState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Tower actor) {
        super.preManage(actor);
        ProgressBar bar = actor.getParent().getBar();
        bar.setProgress(0);
        bar.setDuration(actor.getPrototype().buildDelay);
        bar.setVisible(true);

        TowerProperty property = actor.getProperty();
        int price = property.getCost();

        int nextLevel = property.getTowerLevel() + 1;

        if (nextLevel > Tower.MAX_TOWER_LEVEL)
            throw new IllegalArgumentException("Cannot update to higher value, then expected");

        game.getPlayer().chargeCoins(price);

        Log.info(actor + " starts upgrading...");
    }

    @Override
    public void manage(Tower actor, float delta) {
        super.manage(actor, delta);
        ProgressBar bar = actor.getParent().getBar();
        bar.addProgress(delta);
        if (bar.isFinished()) {
            TowerStateManager manager = actor.getStateManager();
            manager.swap(actor,
                    actor.getStateHolder().getCurrentLevel2State(),
                    manager.getSearchState(), 2);
            manager.swap(actor,
                    actor.getStateHolder().getCurrentLevel1State(),
                    manager.getActiveState(), 1);
        }
    }

    @Override
    public void postManage(Tower actor) {
        super.postManage(actor);
        actor.getParent().getBar().setVisible(false);

        TowerProperty property = actor.getProperty();
        int nextLevel = property.getTowerLevel() + 1;
        try {
            TowerPropertyPrototype nextProperty = actor.getPrototype().getProperty(nextLevel);
            property.setPropertyPrototype(nextProperty);
            Log.info(actor + " is upgraded to " + nextLevel + " level");
        } catch (Exception e) {
            Log.exc("Could not upgrade " + actor + " to level " + nextLevel, e);
        }
    }
}
