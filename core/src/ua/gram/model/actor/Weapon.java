package ua.gram.model.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor {

    protected Vector2 position1, position2;

    public Weapon(Vector2 position1, Vector2 position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) this.toFront();
    }

    public void updateTargetPosition(Vector2 targetPosition) {
        position2.set(
                targetPosition.x,
                targetPosition.y
        );
    }

    public Vector2 getTowerPosition() {
        return position1;
    }

    public Vector2 getTargetPosition() {
        return position2;
    }

    public void reset() {
        position1 = Vector2.Zero;
        position2 = Vector2.Zero;
    }
}
