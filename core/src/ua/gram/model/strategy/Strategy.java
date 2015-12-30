package ua.gram.model.strategy;

import ua.gram.model.group.EnemyGroup;

import java.util.List;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface Strategy {

    List<EnemyGroup> chooseVictims(List<EnemyGroup> victims);

    void update();
}
