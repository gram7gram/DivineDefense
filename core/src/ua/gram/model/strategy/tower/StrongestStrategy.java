package ua.gram.model.strategy.tower;

import java.util.Collections;
import java.util.List;

import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StrongestStrategy implements TowerStrategy {

    private final EnemyHealthComparator healthComparator;

    public StrongestStrategy(EnemyHealthComparator healthComparator) {
        this.healthComparator = healthComparator;
    }

    @Override
    public List<EnemyGroup> chooseVictims(Tower tower, List<EnemyGroup> victims) {

        if (victims.size() == 1) return victims;

        healthComparator.setType(EnemyHealthComparator.MAX);

        Collections.sort(victims, healthComparator);

        int lvl = tower.getProperty().getTowerLevel();

        return victims.subList(0, lvl);
    }

}
