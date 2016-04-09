package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WeakestStrategy implements TowerStrategy {

    private final EnemyHealthComparator healthComparator;
    private final Object lock = new Object();

    public WeakestStrategy(EnemyHealthComparator comparator) {
        this.healthComparator = comparator;
    }

    @Override
    public List<Enemy> chooseVictims(Tower tower, List<Enemy> targets) {

        if (targets.size() == 0) throw new NullPointerException("Nothing to compare");

        if (targets.size() == 1) return targets;

        healthComparator.setType(EnemyHealthComparator.MIN);

        synchronized (lock) {

            Collections.sort(targets, healthComparator);

//            NOTE Use this to attack multiple targets
//            int lvl = tower.getProperty().getTowerLevel();
//            return targets.subList(0, lvl);

            return targets.subList(0, 1);
        }
    }

}