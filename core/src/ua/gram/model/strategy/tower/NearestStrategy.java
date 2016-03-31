package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class NearestStrategy implements TowerStrategy {

    private final EnemyDistanceComparator distanceComparator;

    public NearestStrategy(EnemyDistanceComparator comparator) {
        this.distanceComparator = comparator;
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {

        if (victims.size() == 1) return victims;

        distanceComparator.update(tower);

        Collections.sort(victims, distanceComparator);

        int lvl = tower.getProperty().getTowerLevel();

        return victims.subList(0, lvl);
    }


}
