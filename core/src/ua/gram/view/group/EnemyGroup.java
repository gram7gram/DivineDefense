package ua.gram.view.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.HealthBar;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends Group {

    private final Enemy enemy;
    private final HealthBar bar;

    public EnemyGroup(Enemy enemy, HealthBar bar) {
        this.enemy = enemy;
        this.bar = bar;
        this.addActor(enemy);
        this.addActor(bar);
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + enemy + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            for (Actor actor : this.getChildren()) {
                actor.setZIndex(enemy.getZIndex());
            }
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public HealthBar getBar() {
        return bar;
    }
}
