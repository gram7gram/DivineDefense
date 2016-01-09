package ua.gram.model.strategy;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.strategy.tower.AllStrategy;
import ua.gram.model.strategy.tower.NearestStrategy;
import ua.gram.model.strategy.tower.RandomStrategy;
import ua.gram.model.strategy.tower.StrongestStrategy;
import ua.gram.model.strategy.tower.TowerStrategy;
import ua.gram.model.strategy.tower.WeakestStrategy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStrategyManager {

    private final EnemyDistanceComparator distanceComparator;
    private final EnemyHealthComparator healthComparator;

    private final TowerStrategy strongestTowerStrategy;
    private final TowerStrategy weakestTowerStrategy;
    private final TowerStrategy nearestTowerStrategy;
    private final TowerStrategy randomTowerStrategy;
    private final TowerStrategy allTowerStrategy;

    public TowerStrategyManager() {
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