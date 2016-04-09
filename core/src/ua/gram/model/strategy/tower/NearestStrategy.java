package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class NearestStrategy implements TowerStrategy {

    private final EnemyDistanceComparator distanceComparator;
    private final Object lock = new Object();

    public NearestStrategy(EnemyDistanceComparator comparator) {
        this.distanceComparator = comparator;
    }

    @Override
    public List<Enemy> chooseVictims(Tower tower, List<Enemy> targets) {

        if (targets.size() == 0) throw new NullPointerException("Nothing to compare");

        if (targets.size() == 1) return targets;

        distanceComparator.update(tower);

        synchronized (lock) {
            Collections.sort(targets, distanceComparator);

//            NOTE Use this to attack multiple targets
//            int lvl = tower.getProperty().getTowerLevel();
//            return victims.subList(0, lvl);

            return targets.subList(0, 1);
        }
    }


}
