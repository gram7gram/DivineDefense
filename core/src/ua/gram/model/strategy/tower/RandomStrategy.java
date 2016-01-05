package ua.gram.model.strategy.tower;

import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class RandomStrategy implements TowerStrategy {

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {
        throw new IllegalArgumentException("Not implemented yet");
    }

}