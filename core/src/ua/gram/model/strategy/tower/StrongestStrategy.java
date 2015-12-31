package ua.gram.model.strategy.tower;

import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.StrategyManager;

import java.util.Collections;
import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StrongestStrategy implements TowerStrategy {

    private final EnemyHealthComparator healthComparator;

    public StrongestStrategy(StrategyManager manager) {
        this.healthComparator = manager.getHealthComparator();
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {

        healthComparator.setType(EnemyHealthComparator.MAX);

        Collections.sort(victims, healthComparator);

        return victims.size() > 0 ? victims.subList(0, tower.getTowerLevel()) : null;
    }

}
