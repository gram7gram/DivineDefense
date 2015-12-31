package ua.gram.model.strategy.tower;

import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface TowerStrategy {

    List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims);
}
