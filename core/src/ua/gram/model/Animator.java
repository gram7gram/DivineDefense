package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Animator {

    private PollableAnimation pollableAnimation;
    private Path.Types direction;
    private Types type;

    public PollableAnimation getPoolable() {
        return pollableAnimation;
    }

    public Animation getAnimation() {
        return pollableAnimation.getAnimation();
    }

    public void setPollable(PollableAnimation pollable) {
        this.pollableAnimation = pollable;
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
        WALKING, STUN, ABILITY, DEAD, FINISH, SPAWN,
    }
}
