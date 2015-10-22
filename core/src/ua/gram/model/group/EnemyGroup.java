package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.HealthBar;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends Group {

    private final Enemy enemy;
    private final HealthBar bar;
    private Actor dummy;
    private BitmapFont info;

    public EnemyGroup(Skin skin, Enemy enemy, HealthBar bar) {
        this.enemy = enemy;
        this.bar = bar;
        this.addActor(enemy);
        this.addActor(bar);
        if (DDGame.DEBUG) {
            dummy = new Actor();
            dummy.setSize(3, 3);
            dummy.setVisible(true);
            this.addActor(dummy);
            info = new BitmapFont();
            info.setColor(1, 1, 1, 1);
        }
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + enemy + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && DDGame.DEBUG) {
            dummy.setPosition(enemy.getOriginX() - 1, enemy.getOriginY() - 1);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (DDGame.DEBUG) {
            float x = enemy.getX() + enemy.getWidth() + 2;
            float y = enemy.getY() + 2;
            info.draw(batch, enemy.getCurrentLevel1State() + "", x, y + 12);
            info.draw(batch, enemy.getCurrentLevel2State() + "", x, y + 24);
            info.draw(batch, enemy.getCurrentLevel3State() + "", x, y + 36);
            info.draw(batch, enemy.getCurrentLevel4State() + "", x, y + 48);
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public HealthBar getHealthBar() {
        return bar;
    }
}
