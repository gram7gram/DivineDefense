package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Animator {

    private Animation animation;
    private Path.Types direction;
    private Types type;

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Path.Types getDirection() {
        return direction;
    }

    public void setDirection(Path.Types direction) {
        this.direction = direction;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    /**
     * The states of Actors in the game,
     * including Enemy and Tower.
     */
    public enum Types {

        //For Tower
        IDLE, SHOOT, BUILD, SELL, LAND, AIR, LANDAIR,
        //For Enemy
        WALKING, STUN, ABILITY, DEAD, FINISH,
        LEFT_WALKING,
        RIGHT_WALKING,
        DOWN_WALKING,
        UP_WALKING,
        LEFT_DEAD,
        RIGHT_DEAD,
        UP_DEAD,
        DOWN_DEAD,
        LEFT_SPAWN,
        RIGHT_SPAWN,
        UP_SPAWN,
        DOWN_SPAWN,
        LEFT_ABILITY,
        RIGHT_ABILITY,
        UP_ABILITY,
        DOWN_ABILITY,
        LEFT_FINISH,
        RIGHT_FINISH,
        UP_FINISH,
        DOWN_FINISH,
        DOWN_IDLE,
        UP_IDLE,
        LEFT_IDLE,
        RIGHT_IDLE,
        UP_STUN,
        DOWN_STUN,
        LEFT_STUN,
        RIGHT_STUN, SPAWN,
    }
}
