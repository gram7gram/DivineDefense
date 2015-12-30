package ua.gram.controller.comparator;

import ua.gram.model.group.EnemyGroup;

import java.util.Comparator;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyHealthComparator implements Comparator<EnemyGroup> {

    public static final int MAX = 1;
    public static final int MIN = -1;
    private int type;

    public EnemyHealthComparator() {
        type = MAX;
    }

    @Override
    public int compare(EnemyGroup enemy1, EnemyGroup enemy2) {
        return (int) ((enemy1.getRootActor().health - enemy2.getRootActor().health) * type);
    }

    public void setType(int type) {
        this.type = type;
    }
}
