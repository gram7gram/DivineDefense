package ua.gram.model.actor.weapon;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by gram on 5/3/15.
 */
public abstract class Weapon extends Actor {

    protected Vector2 position1, position2;

    public Weapon(Vector2 position1, Vector2 position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    public void updatePosition(Vector2 towerPosition, Vector2 targetPosition) {
        position1 = towerPosition;
        position2 = targetPosition;
    }

    public Vector2 getTowerPosition() {
        return position1;
    }

    public Vector2 getTargetPosition() {
        return position2;
    }
}
