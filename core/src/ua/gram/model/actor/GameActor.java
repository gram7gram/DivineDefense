package ua.gram.model.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.model.prototype.GameActorPrototype;

import java.io.Serializable;

public class GameActor extends Actor {

    protected final float animationWidth;
    protected final float animationHeight;
    private final Types originType;

    public GameActor(GameActorPrototype prototype) {
        this.animationHeight = prototype.height;
        this.animationWidth = prototype.width;
        this.setSize(prototype.width, prototype.height);
        this.setBounds(this.getX(), this.getY(), prototype.width, prototype.height);

        if (prototype.name.equals("EnemyWarrior")) {
            originType = Types.WARRIOR;
        } else if (prototype.name.equals("EnemySummoner")) {
            originType = Types.SUMMONER;
        } else if (prototype.name.equals("EnemySoldier")) {
            originType = Types.SOLDIER;
        } else if (prototype.name.equals("EnemySoldierArmored")) {
            originType = Types.SOLDIER_ARMORED;
        } else if (prototype.name.equals("EnemyRunner")) {
            originType = Types.RUNNER;
        } else if (prototype.name.equals("TowerPrimary")) {
            originType = Types.PRIMARY;
        } else if (prototype.name.equals("TowerSpecial")) {
            originType = Types.SPECIAL;
        } else if (prototype.name.equals("TowerSecondary")) {
            originType = Types.SECONDARY;
        } else if (prototype.name.equals("TowerStun")) {
            originType = Types.STUN;
        } else {
            throw new IllegalArgumentException("Unknown GameActor origin: " + prototype.name);
        }
    }

    @Override public String toString() {
        return this.getClass().getSimpleName() + "#" + this.hashCode();
    }

    public float getAnimationWidth() {
        return animationWidth;
    }

    public float getAnimationHeight() {
        return animationHeight;
    }

    public Types getOriginType() {
        return originType;
    }

    public enum Types  {
        WARRIOR, SUMMONER, SOLDIER, SOLDIER_ARMORED, RUNNER,
        STUN,SECONDARY,PRIMARY,SPECIAL
    }
}