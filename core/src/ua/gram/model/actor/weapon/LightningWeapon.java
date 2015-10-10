package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import ua.gram.controller.Resources;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;

import java.util.LinkedList;
import java.util.Random;

/**
 * Weapon for TowerPrimary.
 * <p/>
 * Must have:
 * different glowing
 * pale yellow, blue, red color
 *
 * @author Gram <gram7gram@gmail.com>
 */
public final class LightningWeapon extends Weapon {

    private final Sprite start_back;
    private final Sprite start_over;
    private final Sprite node_back;
    private final Sprite node_over;
    private final Sprite end_over;
    private final Sprite end_back;
    private Vector2[] nodes;
    private float counter = 0;

    public LightningWeapon(Resources resources, Tower tower, Enemy target) {
        super(tower, target);
        start_back = new Sprite(resources.getAtlasRegion("weapon-start-back"));
        start_over = new Sprite(resources.getAtlasRegion("weapon-start-over"));
        node_back = new Sprite(resources.getAtlasRegion("weapon-middle-back"));
        node_over = new Sprite(resources.getAtlasRegion("weapon-middle-over"));
        end_back = new Sprite(resources.getAtlasRegion("weapon-end-back"));
        end_over = new Sprite(resources.getAtlasRegion("weapon-end-over"));
    }

    @Override
    public void update(float delta) {
        if (counter >= .5f) {//Should not follow the target. Once it attacked for .5s - hide it.
            counter = 0;
            target = null;
        } else {
            counter += delta;
            if (nodes == null) nodes = generateNodes(5);


        }
    }

    @Override
    public void render(Batch batch) {

    }

    /**
     * Generation of lightning's nodes.
     *
     * @param max_generation amount of generations. Preferable value 4-6.
     * @return array of Vectors for node points
     */
    private Vector2[] generateNodes(int max_generation) {
        LinkedList<Vector2> nodes = new LinkedList<Vector2>();
        nodes.addFirst(tower.getCenterPoint());
        nodes.addLast(target.getCenterPoint());
        Random rand = new Random();
        for (int generation = 1; generation <= max_generation; generation++) {
            for (int i = 0; i < nodes.size(); i += 2) {
                nodes.add(i / 2 + 1, split(nodes.get(i), nodes.get(i + 1), .85f));
            }
        }
        return (Vector2[]) nodes.toArray();
    }

    /**
     * @param start
     * @param end
     * @param diff
     * @return
     */
    private Vector2 split(Vector2 start, Vector2 end, float diff) {
        if (diff < 0 || diff > 1) throw new IllegalArgumentException("Lightning generation aborted! Diff value should be in 0 to 1 range.");
        float x = start.x + (end.x - start.x) * diff;
        float y = start.y + (end.y - start.y) * diff;
        return new Vector2(x, y);
    }


}
