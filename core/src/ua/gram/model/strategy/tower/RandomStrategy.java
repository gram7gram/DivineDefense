package ua.gram.model.strategy.tower;

import ua.gram.model.group.EnemyGroup;
import ua.gram.model.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class RandomStrategy implements Strategy {

    @Override
    public List<EnemyGroup> chooseVictims(List<EnemyGroup> victims) {
        List<EnemyGroup> enemyTargets = new ArrayList<>();

        int index = victims.size() > 1 ? (int) (Math.random() * (victims.size())) : 0;

        enemyTargets.add(victims.get(index));

        return enemyTargets;
    }

    @Override
    public void update() {

    }

}
