package ua.gram.model.strategy.tower;

import java.util.List;

import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class RandomStrategy implements TowerStrategy {

    @Override
    public List<Enemy> chooseVictims(Tower tower, List<Enemy> targets) {
        throw new IllegalArgumentException("Not implemented yet");
    }

}
