package ua.gram.model.strategy.tower;

import java.util.List;

import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AllStrategy extends AOEStrategy {

    public AllStrategy(TowerStrategyManager manager) {
        super(manager);
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {
        return victims;
    }
}
