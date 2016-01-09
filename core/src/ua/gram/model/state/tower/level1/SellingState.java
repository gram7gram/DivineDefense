package ua.gram.model.state.tower.level1;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SellingState extends InactiveState {

    public SellingState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        GameBattleStage stage = tower.getTowerShop().getStageBattle();
        stage.removeTowerPosition(tower);
        int revenue = (int) (tower.getCost() * Tower.SELL_RATIO);
        game.getPlayer().addCoins(revenue);
        tower.getParent().remove();
        tower.getTowerShop().refund(tower);
        Log.info(tower + " is sold for: " + revenue + " coins");
    }
}
