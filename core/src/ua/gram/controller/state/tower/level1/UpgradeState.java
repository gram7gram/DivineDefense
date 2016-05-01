package ua.gram.controller.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.actor.misc.ProgressBar;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerProperties;
import ua.gram.model.prototype.tower.TowerPropertyPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UpgradeState extends InactiveState {

    public UpgradeState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    public void preManage(Tower actor) {
        super.preManage(actor);
        ProgressBar bar = actor.getParent().getBar();
        bar.setProgress(0);
        bar.setDuration(actor.getPrototype().buildDelay);
        bar.setVisible(true);

        TowerProperties property = actor.getProperties();
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
            manager.swap(actor, manager.getSearchState());
            manager.swap(actor, manager.getActiveState());
        }
    }

    @Override
    public void postManage(Tower actor) {
        super.postManage(actor);
        actor.getParent().getBar().setVisible(false);

        TowerProperties property = actor.getProperties();
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
