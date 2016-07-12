package ua.gram.controller.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.tower.TowerState;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SellingState extends InactiveState {

    public SellingState(DDGame game, TowerStateManager towerStateManager) {
        super(game, towerStateManager);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        TowerStateManager manager = tower.getTowerShop().getStateManager();
        TowerState state2 = tower.getStateHolder().getCurrentLevel2State();
        if (state2 != null) manager.swap(tower, state2, null, 2);
    }

    @Override
    public void manage(Tower tower, float delta) {
        super.manage(tower, delta);
        Counters counters = tower.getCounters();
        float count = counters.get("buildCount");
        if (count >= tower.getPrototype().buildDelay / 2) {
            counters.set("buildCount", 0);

            BattleStage stage = tower.getTowerShop().getBattleStage();
            stage.removeTowerPosition(tower);

            int revenue = (int) (tower.getProperties().getCost() * Tower.SELL_RATIO);
            game.getPlayer().addCoins(revenue);

            tower.getTowerShop().refund(tower.getParent());
            tower.getParent().remove();

            Log.info(tower + " is sold for: " + revenue + " coins");

            TowerStateManager manager = tower.getTowerShop().getStateManager();
            manager.swap(tower, tower.getStateHolder().getCurrentLevel1State(), null, 1);
        } else {
            counters.set("buildCount", count + delta);
        }
    }
}
