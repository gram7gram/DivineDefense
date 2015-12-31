package ua.gram.controller.comparator;

import com.badlogic.gdx.math.Vector2;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;

import java.util.Comparator;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDistanceComparator implements Comparator<EnemyGroup> {

    private Tower tower;

    public void update(Tower tower) {
        this.tower = tower;
    }

    @Override
    public int compare(EnemyGroup enemy1, EnemyGroup enemy2) {
        Vector2 pos1 = enemy1.getRootActor().getOrigin();
        Vector2 pos2 = enemy2.getRootActor().getOrigin();
        Vector2 posTower = tower.getCenterPoint();
        float dist1 = pos1.dst(posTower);
        float dist2 = pos2.dst(posTower);
        return (int) (dist1 - dist2);
    }


}
