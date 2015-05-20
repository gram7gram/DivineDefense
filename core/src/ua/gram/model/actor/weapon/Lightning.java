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

    public Lightning(Resources resources, Tower tower, Enemy target) {
        super(tower, target);
        start_back = new Sprite(resources.getTexture(Resources.LIGHTNING_START_BACK));
        start_over = new Sprite(resources.getTexture(Resources.LIGHTNING_START_OVER));
        node_back = new Sprite(resources.getTexture(Resources.LIGHTNING_NODE_BACK));
        node_over = new Sprite(resources.getTexture(Resources.LIGHTNING_NODE_OVER));
        end_back = new Sprite(resources.getTexture(Resources.LIGHTNING_END_BACK));
        end_over = new Sprite(resources.getTexture(Resources.LIGHTNING_END_OVER));

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Batch batch) {

    }

    private ArrayList<Vector2> generateNodes() {

        return null;
    }
}
