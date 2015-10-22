package ua.gram.controller.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.EnemyAnimation;
import ua.gram.controller.pool.animation.EnemyDirectionAnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.Player;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationProvider {

    public static float DELAY = 1/10f;
    private final Skin skin;
    private final EnemyAnimation soldierAnimation;
    private final EnemyAnimation soldierArmoredAnimation;
    private final EnemyAnimation runnerAnimation;
    private final EnemyAnimation summomerAnimation;
    private final EnemyAnimation warriorAnimation;
    private Enemy enemy;

    public EnemyAnimationProvider(Skin skin) throws NullPointerException {
        this.skin = skin;
        soldierAnimation = new EnemyAnimation(GameActor.Types.SOLDIER, this);
        soldierArmoredAnimation = new EnemyAnimation(GameActor.Types.SOLDIER_ARMORED, this);
        runnerAnimation = new EnemyAnimation(GameActor.Types.RUNNER, this);
        warriorAnimation = new EnemyAnimation(GameActor.Types.WARRIOR,this);
        summomerAnimation = new EnemyAnimation(GameActor.Types.SUMMONER, this);
    }

    public void init(Enemy enemy) {
        //subclass first
        if (enemy instanceof EnemySoldierArmored && !soldierArmoredAnimation.initialized) soldierArmoredAnimation.init(enemy);
        //now concrete classes
        else if (enemy instanceof EnemySoldier && !soldierAnimation.initialized) soldierAnimation.init(enemy);
        else if (enemy instanceof EnemyRunner && !runnerAnimation.initialized) runnerAnimation.init(enemy);
        else if (enemy instanceof EnemySummoner && !summomerAnimation.initialized) summomerAnimation.init(enemy);
        else if (enemy instanceof EnemyWarrior && !warriorAnimation.initialized) warriorAnimation.init(enemy);
    }

    public void setActor(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Skin getSkin() {
        return skin;
    }

    public AnimationPool get(GameActor.Types origin, Animator.Types type, Path.Types direction) throws IllegalArgumentException{
        return this.get(origin).get(type).get(direction);
    }

    public EnemyAnimation get(GameActor.Types origin) {
        switch (origin) {
            case WARRIOR: return warriorAnimation;
            case SOLDIER: return soldierAnimation;
            case SOLDIER_ARMORED:return soldierArmoredAnimation;
            case SUMMONER: return summomerAnimation;
            case RUNNER: return runnerAnimation;
            default: throw new IllegalArgumentException("Unknown Enemy type: " + origin);
        }
    }

}
