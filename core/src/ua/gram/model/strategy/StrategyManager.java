package ua.gram.model.strategy;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.strategy.tower.NearestStrategy;
import ua.gram.model.strategy.tower.RandomStrategy;
import ua.gram.model.strategy.tower.StrongestStrategy;
import ua.gram.model.strategy.tower.WeakestStrategy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StrategyManager {

    private final EnemyDistanceComparator distanceComparator;
    private final EnemyHealthComparator healthComparator;

    private final Strategy strongestStrategy;
    private final Strategy weakestStrategy;
    private final Strategy nearestStrategy;
    private final Strategy randomStrategy;

    public StrategyManager(Tower tower) {
        distanceComparator = new EnemyDistanceComparator(tower);
        healthComparator = new EnemyHealthComparator();

        randomStrategy = new RandomStrategy();
        nearestStrategy = new NearestStrategy(tower, this);
        weakestStrategy = new WeakestStrategy(tower, this);
        strongestStrategy = new StrongestStrategy(tower, this);
    }

    public Strategy getStrongestStrategy() {
        return strongestStrategy;
    }

    public Strategy getWeakestStrategy() {
        return weakestStrategy;
    }

    public Strategy getNearestStrategy() {
        return nearestStrategy;
    }

    public Strategy getRandomStrategy() {
        return randomStrategy;
    }

    public EnemyDistanceComparator getDistanceComparator() {
        return distanceComparator;
    }

    public EnemyHealthComparator getHealthComparator() {
        return healthComparator;
    }
}