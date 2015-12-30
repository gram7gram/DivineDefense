package ua.gram.model.strategy.tower;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.Strategy;
import ua.gram.model.strategy.StrategyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class NearestStrategy implements Strategy {

    private final Tower tower;
    private final EnemyDistanceComparator distanceComparator;
    private int towerLevel;

    public NearestStrategy(Tower tower, StrategyManager manager) {
        this.tower = tower;
        this.distanceComparator = manager.getDistanceComparator();
    }

    @Override
    public List<EnemyGroup> chooseVictims(List<EnemyGroup> victims) {
        List<EnemyGroup> enemyTargets = new ArrayList<>();

        Collections.sort(victims, distanceComparator);

        if (towerLevel > 1 && victims.size() > 1) {
            enemyTargets = victims.subList(0, victims.size() > towerLevel ?
                    towerLevel : victims.size());
        } else {
            enemyTargets.add(victims.get(0));
        }
        return enemyTargets;
    }

    @Override
    public void update() {
        this.towerLevel = tower.getTowerLevel();
    }

}
