package ua.gram.model.state.tower.level1;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;

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
        tower.remove();
        game.getPlayer().chargeCoins(tower.getCost());
        tower.setOrigin(tower.getX() + 20, tower.getY() + 42);
        tower.isBuilding = true;
        tower.setDefaultStrategy();
        GameBattleStage battleStage = tower.getTowerShop().getStageBattle();
        TowerGroup towerGroup = new TowerGroup(game, tower);
        towerGroup.setVisible(true);
        battleStage.updateZIndexes(towerGroup);
        battleStage.addTowerPosition(tower);

        Log.info(tower + " is building...");
    }

    @Override
    public void manage(Tower tower, float delta) {
        super.manage(tower, delta);
        tower.setTouchable(Touchable.disabled);
        tower.isBuilding = true;

    }

    @Override
    public void postManage(Tower tower) {
        super.postManage(tower);
        tower.isBuilding = false;
    }
}
