package ua.gram.controller.state.tower.level1;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import ua.gram.DDGame;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BuildingState extends InactiveState {

    public BuildingState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.BUILD;
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        Log.info(tower + " is being built...");

        game.getPlayer().chargeCoins(tower.getProperties().getCost());

        tower.setOrigin(tower.getX() + 20, tower.getY() + 42);
        tower.setDefaultStrategy();

        BattleStage battleStage = tower.getTowerShop().getBattleStage();

        TowerGroup towerGroup = tower.getParent();
        towerGroup.setVisible(true);

        battleStage.updateZIndexes(towerGroup);
        battleStage.addTowerPosition(tower);

        towerGroup.getBar().setVisible(true);
        towerGroup.getBar().setDuration(tower.getPrototype().buildDelay);
    }

    @Override
    public void manage(Tower tower, float delta) {
        super.manage(tower, delta);
        if (tower.buildCount >= tower.getPrototype().buildDelay) {
            tower.buildCount = 0;
            tower.setTouchable(Touchable.enabled);

            Log.info(tower + " is built");

            TowerStateManager manager = tower.getTowerShop().getStateManager();
            manager.swap(tower, manager.getActiveState());
            manager.swap(tower, manager.getSearchState());
        } else {
            tower.setTouchable(Touchable.disabled);
            tower.buildCount += delta;
            tower.getParent().getBar().setProgress(tower.buildCount);
        }
    }

    @Override
    public void postManage(Tower tower) {
        super.postManage(tower);
        tower.buildCount = 0;
        tower.getParent().getBar().setVisible(false);
    }
}
