package ua.gram.model.state.tower.level2;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.state.tower.TowerStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SearchState extends IdleState {

    public SearchState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        Log.info(tower + " scannes for victims...");
    }

    @Override
    public void manage(Tower tower, float delta) {
        if (tower.attackCount >= tower.getProperty().getRate()) {
            tower.attackCount = 0;

            List<EnemyGroup> victims = new ArrayList<EnemyGroup>(5);
            for (EnemyGroup group : tower.getStage().getEnemyGroupsOnMap()) {
                if (tower.isInRange(group.getRootActor())
                        && group.getRootActor().health > 0) {
                    victims.add(group);
                }
            }

            if (victims.isEmpty()) tower.resetVictims();
            else {
                List<EnemyGroup> filteredVictims = tower.getCurrentTowerStrategy().chooseVictims(tower, victims);
                if (!filteredVictims.isEmpty()) {
                    tower.setVictims(filteredVictims);
                    TowerStateManager manager = tower.getTowerShop().getStateManager();
                    manager.swap(tower,
                            tower.getStateHolder().getCurrentLevel2State(),
                            manager.getAttackState(), 2);
                } else tower.resetVictims();
            }
        } else {
            tower.attackCount += delta;
        }
    }
}
