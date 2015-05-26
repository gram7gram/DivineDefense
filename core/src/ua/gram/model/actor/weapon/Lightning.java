package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import ua.gram.controller.Resources;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;

import java.util.ArrayList;

/**
 * Must have:
 * different glowing
 * pale yellow, blue, red color
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Lightning extends Weapon {

    private final Sprite start_back;
    private final Sprite start_over;
    private final Sprite node_back;
    private final Sprite node_over;
    private final Sprite end_over;
    private final Sprite end_back;
    private ArrayList<Vector2> nodes;
    private float counter = 0;

    public Lightning(Resources resources, Tower tower, Enemy target) {
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
            nodes = generateNodes();

        }
    }

    @Override
    public void render(Batch batch) {

    }

    private ArrayList<Vector2> generateNodes() {
        byte max_generation = 5;
        ArrayList<Vector2> nodes = new ArrayList<Vector2>(19);
        for (int generation = 1; generation <= max_generation; generation++) {
            if (generation < 4) { //Add new nodes and split

            } else {//Do not add new nodes, but split

            }
        }
        return null;
    }
}
