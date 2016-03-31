package ua.gram.model.strategy;

import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.strategy.tower.AOEStrategy;
import ua.gram.model.strategy.tower.AllStrategy;
import ua.gram.model.strategy.tower.NearestStrategy;
import ua.gram.model.strategy.tower.RandomStrategy;
import ua.gram.model.strategy.tower.StrongestStrategy;
import ua.gram.model.strategy.tower.TowerStrategy;
import ua.gram.model.strategy.tower.WeakestStrategy;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStrategyManager {

    private final TowerStrategy strongestTowerStrategy;
    private final TowerStrategy weakestTowerStrategy;
    private final TowerStrategy nearestTowerStrategy;
    private final TowerStrategy randomTowerStrategy;
    private final TowerStrategy allTowerStrategy;
    private final TowerStrategy aoeStrategy;

    public TowerStrategyManager() {
        EnemyDistanceComparator distanceComparator = new EnemyDistanceComparator();
        EnemyHealthComparator healthComparator = new EnemyHealthComparator();
        allTowerStrategy = new AllStrategy();
        randomTowerStrategy = new RandomStrategy();
        nearestTowerStrategy = new NearestStrategy(distanceComparator);
        weakestTowerStrategy = new WeakestStrategy(healthComparator);
        strongestTowerStrategy = new StrongestStrategy(healthComparator);
        aoeStrategy = new AOEStrategy();
    }

    public TowerStrategy getAllStrategy() {
        return allTowerStrategy;
    }

    public TowerStrategy getStrongestStrategy() {
        return strongestTowerStrategy;
    }

    public TowerStrategy getWeakestStrategy() {
        return weakestTowerStrategy;
    }

    public TowerStrategy getNearestStrategy() {
        return nearestTowerStrategy;
    }

    public TowerStrategy getRandomStrategy() {
        return randomTowerStrategy;
    }

    public TowerStrategy getDefault() {
        return getWeakestStrategy();
    }

    public TowerStrategy getAoeStrategy() {
        return aoeStrategy;
    }
}