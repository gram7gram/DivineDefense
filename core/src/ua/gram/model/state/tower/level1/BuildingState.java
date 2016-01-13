package ua.gram.model.state.tower.level1;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.state.tower.TowerStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class BuildingState extends ActiveState {

    public BuildingState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.BUILD;
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        Log.info(tower + " is being built...");
        game.getPlayer().chargeCoins(tower.getCost());
        tower.setOrigin(tower.getX() + 20, tower.getY() + 42);
        tower.setDefaultStrategy();
        GameBattleStage battleStage = tower.getTowerShop().getStageBattle();
        TowerGroup towerGroup = tower.getParent();
        towerGroup.setVisible(true);
        battleStage.updateZIndexes(towerGroup);
        battleStage.addTowerPosition(tower);
        Log.info(tower + " is built");
    }

    @Override
    public void manage(Tower tower, float delta) {
        super.manage(tower, delta);
        if (tower.buildCount >= tower.getPrototype().buildDelay) {
            tower.buildCount = 0;
            tower.setTouchable(Touchable.enabled);
            tower.getWeapon().setSource(tower.getParent());
            Log.info(tower + " is built");
            TowerStateManager manager = tower.getTowerShop().getStateManager();
            manager.swap(tower, tower.getStateHolder().getCurrentLevel1State(), manager.getActiveState(), 1);
            manager.swap(tower, tower.getStateHolder().getCurrentLevel2State(), manager.getSearchState(), 2);
        } else {
            tower.setTouchable(Touchable.disabled);
            tower.buildCount += delta;
        }

    }

    @Override
    public void postManage(Tower tower) {
        super.postManage(tower);
        tower.buildCount = 0;
    }
}
