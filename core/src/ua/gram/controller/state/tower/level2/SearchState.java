package ua.gram.controller.state.tower.level2;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.ActiveTarget;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.group.EnemyGroup;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SearchState extends IdleState {

    public SearchState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        Log.info(tower + " searches for targets...");
    }

    @Override
    public void manage(Tower tower, float delta) {
        Counters counters = tower.getCounters();
        float count = counters.get("searchCount");
        if (count >= .1) {
            counters.set("searchCount", 0);
            int limit = 5;

            List<Enemy> targets = new ArrayList<Enemy>(limit);
            for (EnemyGroup group : tower.getStage().getEnemyGroupsOnMap()) {
                Enemy enemy = group.getRootActor();

                if (enemy.isAlive() && tower.isInRange(enemy)) {
                    targets.add(enemy);
                    if (targets.size() == limit) break;
                }
            }

            Log.info(tower + " has found " + targets.size() + " targets in range");

            if (!targets.isEmpty()) {
                List<Enemy> filteredTargets = tower.getCurrentTowerStrategy().chooseVictims(tower, targets);
                Log.info(tower + " aims at " + filteredTargets.size()
                        + (filteredTargets.size() == 1 ? " target" : " targets"));
                if (!filteredTargets.isEmpty()) {
                    for (Enemy enemy : filteredTargets) {
                        Weapon weapon = tower.getWeaponPool().obtain();
                        ActiveTarget target = new ActiveTarget(tower, weapon, enemy);
                        tower.addTarget(target);
                    }
                    TowerStateManager manager = tower.getTowerShop().getStateManager();
                    manager.swap(tower, manager.getAttackState());
                } else tower.resetVictims();
            } else tower.resetVictims();

        } else {
            counters.set("searchCount", count + delta);
        }
    }
}
