package ua.gram.model.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;
import ua.gram.model.prototype.GameActorPrototype;

public class GameActor extends Actor {

    protected final float animationWidth;
    protected final float animationHeight;
    private final Types originType;
    protected int updateIterationCount;
    protected Vector2 currentPosition;

    public GameActor(GameActorPrototype prototype) {
        this.animationHeight = prototype.height;
        this.animationWidth = prototype.width;
        this.setSize(prototype.width, prototype.height);
        this.setBounds(this.getX(), this.getY(), prototype.width, prototype.height);

        switch (prototype.name) {
            case "EnemyWarrior":
                originType = Types.WARRIOR;
                break;
            case "EnemySummoner":
                originType = Types.SUMMONER;
                break;
            case "EnemySoldier":
                originType = Types.SOLDIER;
                break;
            case "EnemySoldierArmored":
                originType = Types.SOLDIER_ARMORED;
                break;
            case "EnemyRunner":
                originType = Types.RUNNER;
                break;
            case "TowerPrimary":
                originType = Types.PRIMARY;
                break;
            case "TowerSpecial":
                originType = Types.SPECIAL;
                break;
            case "TowerSecondary":
                originType = Types.SECONDARY;
                break;
            case "TowerStun":
                originType = Types.STUN;
                break;
            default:
                throw new IllegalArgumentException("Unknown GameActor origin: " + prototype.name);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            this.setDebug(DDGame.DEBUG);
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

    public Vector2 getCurrentPosition() {
        if (currentPosition == null) {
            currentPosition = new Vector2(this.getX(), this.getY());
        }
        currentPosition.set(this.getX(), this.getY());
        return currentPosition;
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getUpdateIterationCount() {
        return updateIterationCount;
    }

    public void setUpdateIterationCount(int updateIterationCount) {
        this.updateIterationCount = updateIterationCount;
    }

    public void addUpdateIterationCount(int updateIterationCount) {
        this.updateIterationCount += updateIterationCount;
    }

    public enum Types  {
        WARRIOR, SUMMONER, SOLDIER, SOLDIER_ARMORED, RUNNER,
        STUN,SECONDARY,PRIMARY,SPECIAL
    }
}