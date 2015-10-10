package ua.gram.controller.comparator;

import ua.gram.model.actor.enemy.Enemy;

import java.util.Comparator;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyHealthComparator implements Comparator<Enemy> {

    public static final int MAX = 1;
    public static final int MIN = -1;
    private int type;

    public EnemyHealthComparator() {
        type = MAX;
    }

    @Override
    public int compare(Enemy enemy1, Enemy enemy2) {
        return (int) ((enemy1.health - enemy2.health) * type);
    }

    public void setType(int type) {
        this.type = type;
    }
}
