package ua.gram.model.strategy.tower;

import java.util.List;

import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AllStrategy extends AOEStrategy {

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {
        return victims;
    }
}
