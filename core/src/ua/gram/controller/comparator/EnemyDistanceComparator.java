package ua.gram.controller.comparator;

import com.badlogic.gdx.math.Vector2;

import java.util.Comparator;

import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDistanceComparator implements Comparator<Enemy> {

    private Tower tower;

    public void update(Tower tower) {
        this.tower = tower;
    }

    @Override
    public int compare(Enemy enemy1, Enemy enemy2) {
        Vector2 pos1 = enemy1.getOrigin();
        Vector2 pos2 = enemy2.getOrigin();
        Vector2 posTower = tower.getCenterPoint();
        float dist1 = pos1.dst(posTower);
        float dist2 = pos2.dst(posTower);
        return (int) (dist1 - dist2);
    }
}
