package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class NearestStrategy implements TowerStrategy {

    private final EnemyDistanceComparator distanceComparator;

    public NearestStrategy(TowerStrategyManager manager) {
        this.distanceComparator = manager.getDistanceComparator();
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {
        distanceComparator.update(tower);

        Collections.sort(victims, distanceComparator);

        return victims.size() > 0 ? victims.subList(0, tower.getTowerLevel()) : null;
    }

}
