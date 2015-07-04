package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.misc.HealthBar;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends Group {

    private final Enemy enemy;
    private final HealthBar bar;
    private Actor dummy;

    public EnemyGroup(Enemy enemy, HealthBar bar) {
        this.enemy = enemy;
        this.bar = bar;
        this.addActor(enemy);
        this.addActor(bar);
        if (DDGame.DEBUG) {
            dummy = new Actor();
            dummy.setSize(3, 3);
            dummy.setVisible(true);
            this.addActor(dummy);
        }
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + enemy + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) dummy.setPosition(enemy.getOriginX() - 1, enemy.getOriginY() - 1);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public HealthBar getHealthBar() {
        return bar;
    }
}
