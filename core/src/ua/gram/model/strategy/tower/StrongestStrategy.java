package ua.gram.model.strategy.tower;

import ua.gram.controller.comparator.EnemyHealthComparator;
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
public class StrongestStrategy implements Strategy {

    private final EnemyHealthComparator healthComparator;
    private final Tower tower;
    private int towerLevel;

    public StrongestStrategy(Tower tower, StrategyManager manager) {
        this.healthComparator = manager.getHealthComparator();
        this.tower = tower;
    }

    @Override
    public List<EnemyGroup> chooseVictims(List<EnemyGroup> victims) {
        List<EnemyGroup> enemyTargets = new ArrayList<>();

        healthComparator.setType(EnemyHealthComparator.MAX);

        Collections.sort(victims, healthComparator);

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
        towerLevel = tower.getTowerLevel();
    }

}
