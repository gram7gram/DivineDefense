package ua.gram.controller.enemy;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.EnemyAnimation;
import ua.gram.model.Animator;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationProvider {

    private final Skin skin;
    private final EnemyAnimation soldierAnimation;
    private final EnemyAnimation soldierArmoredAnimation;
    private final EnemyAnimation runnerAnimation;
    private final EnemyAnimation summomerAnimation;
    private final EnemyAnimation warriorAnimation;

    public EnemyAnimationProvider(Skin skin) throws NullPointerException {
        this.skin = skin;
        runnerAnimation = new EnemyAnimation(this);
        warriorAnimation = new EnemyAnimation(this);
        soldierAnimation = new EnemyAnimation(this);
        summomerAnimation = new EnemyAnimation(this);
        soldierArmoredAnimation = new EnemyAnimation(this);
    }

    public void init(Enemy enemy) {
        //subclass first
        if (enemy instanceof EnemySoldierArmored && !soldierArmoredAnimation.initialized)
            soldierArmoredAnimation.init(enemy);

            //now concrete classes
        else if (enemy instanceof EnemySoldier && !soldierAnimation.initialized)
            soldierAnimation.init(enemy);
        else if (enemy instanceof EnemyRunner && !runnerAnimation.initialized)
            runnerAnimation.init(enemy);
        else if (enemy instanceof EnemySummoner && !summomerAnimation.initialized)
            summomerAnimation.init(enemy);
        else if (enemy instanceof EnemyWarrior && !warriorAnimation.initialized)
            warriorAnimation.init(enemy);
    }

    public Skin getSkin() {
        return skin;
    }

    public AnimationPool get(GameActor.Types origin,
                             Animator.Types type,
                             Path.Types direction) throws IllegalArgumentException {
        return this.get(origin).get(type).get(direction);
    }

    public AnimationPool get(Enemy enemy, Animator.Types type) throws IllegalArgumentException {
        return this.get(enemy.getOriginType())
                .get(type)
                .get(enemy.getCurrentDirectionType());
    }

    public AnimationPool getPool(Enemy enemy) throws IllegalArgumentException {
        return this.get(enemy.getOriginType())
                .get(enemy.getAnimator().getType())
                .get(enemy.getCurrentDirectionType());
    }

    public EnemyAnimation get(Enemy enemy) {
        return this.get(enemy.getOriginType());
    }

    public EnemyAnimation get(GameActor.Types origin) {
        switch (origin) {
            case WARRIOR:
                return warriorAnimation;
            case SOLDIER:
                return soldierAnimation;
            case SOLDIER_ARMORED:
                return soldierArmoredAnimation;
            case SUMMONER:
                return summomerAnimation;
            case RUNNER:
                return runnerAnimation;
            default:
                throw new IllegalArgumentException("Unknown Enemy type: " + origin);
        }
    }

}
