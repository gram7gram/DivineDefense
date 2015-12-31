package ua.gram.model.strategy;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.strategy.tower.*;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StrategyManager {

    private final EnemyDistanceComparator distanceComparator;
    private final EnemyHealthComparator healthComparator;

    private final TowerStrategy strongestTowerStrategy;
    private final TowerStrategy weakestTowerStrategy;
    private final TowerStrategy nearestTowerStrategy;
    private final TowerStrategy randomTowerStrategy;
    private final TowerStrategy allTowerStrategy;

    public StrategyManager() {
        distanceComparator = new EnemyDistanceComparator();
        healthComparator = new EnemyHealthComparator();

        allTowerStrategy = new AllStrategy();
        randomTowerStrategy = new RandomStrategy();
        nearestTowerStrategy = new NearestStrategy(this);
        weakestTowerStrategy = new WeakestStrategy(this);
        strongestTowerStrategy = new StrongestStrategy(this);
    }

    public TowerStrategy getAllStrategy() {
        return allTowerStrategy;
    }

    public TowerStrategy getStrongestTowerStrategy() {
        return strongestTowerStrategy;
    }

    public TowerStrategy getWeakestTowerStrategy() {
        return weakestTowerStrategy;
    }

    public TowerStrategy getNearestTowerStrategy() {
        return nearestTowerStrategy;
    }

    public TowerStrategy getRandomStrategy() {
        return randomTowerStrategy;
    }

    public EnemyDistanceComparator getDistanceComparator() {
        return distanceComparator;
    }

    public EnemyHealthComparator getHealthComparator() {
        return healthComparator;
    }

    public TowerStrategy getDefault() {
        return strongestTowerStrategy;
    }
}