package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.HealthBar;

import java.util.List;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends Group {

    private final DDGame game;
    private final Enemy enemy;
    private final HealthBar bar;
    private Actor origin;
    private Actor coordinates;
    private boolean test = true;

    public EnemyGroup(DDGame game, Enemy enemy, HealthBar bar) {
        this.game = game;
        this.enemy = enemy;
        this.bar = bar;
        this.addActor(enemy);
        this.addActor(bar);
        if (DDGame.DEBUG) {
            origin = new Actor();
            origin.setSize(3, 3);
            origin.setVisible(true);

            coordinates = new Actor();
            coordinates.setSize(3, 3);
            coordinates.setVisible(true);

            this.addActor(origin);
            this.addActor(coordinates);
        }
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "Group for " + enemy + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (enemy != null && origin != null && coordinates != null) {
                if (DDGame.DEBUG) {
                    origin.setVisible(true);
                    coordinates.setVisible(true);
                    origin.setPosition(enemy.getOriginX() - 1, enemy.getOriginY() - 1);
                    coordinates.setPosition(enemy.getX() - 1, enemy.getY() - 1);
                    if (test && enemy.getPath() != null) {
                        List<Vector2> dirs = enemy.getPath().getDirections();
                        Vector2 pos = new Vector2(enemy.getCurrentPosition());
                        for (Vector2 v : dirs) {
                            Actor a = new Actor();
                            a.setSize(6, 6);
                            a.setVisible(true);
                            a.setPosition(pos.x + (DDGame.TILE_HEIGHT - a.getWidth()) / 2,
                                    pos.y + (DDGame.TILE_HEIGHT - a.getHeight()) / 2);
                            pos.add(new Vector2(v.x * DDGame.TILE_HEIGHT, v.y * DDGame.TILE_HEIGHT));
                            this.addActor(a);
                        }
                        test = false;
                    }
                } else {
                    origin.setVisible(false);
                    coordinates.setVisible(false);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (DDGame.DEBUG) {
            float x = enemy.getX() + enemy.getWidth() + 2;
            float y = enemy.getY() + 2;
            game.getInfo().draw(batch, enemy.getCurrentLevel1State() + "", x, y + 12);
            game.getInfo().draw(batch, enemy.getCurrentLevel2State() + "", x, y + 24);
            game.getInfo().draw(batch, enemy.getCurrentLevel3State() + "", x, y + 36);
            game.getInfo().draw(batch, enemy.getCurrentLevel4State() + "", x, y + 48);
            game.getInfo().draw(batch, enemy.getCurrentDirectionType() + "", x, y + 60);
            game.getInfo().draw(batch, Math.round(enemy.getX()) + ":" + Math.round(enemy.getY()),
                    enemy.getX() - 24,
                    enemy.getY() - 8);
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public HealthBar getHealthBar() {
        return bar;
    }
}
