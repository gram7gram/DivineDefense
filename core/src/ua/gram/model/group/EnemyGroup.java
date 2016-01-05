package ua.gram.model.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.HealthBar;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends ActorGroup<Enemy> {

    private final DDGame game;
    private Actor coordinates;
    private boolean hasPathFinder;

    public EnemyGroup(DDGame game, Enemy enemy) {
        super(enemy);
        this.game = game;
        this.addActor(enemy);
        this.addActor(new HealthBar(game.getResources().getSkin(), enemy));
        enemy.toFront();
        hasPathFinder = false;
        Log.info("Group for " + enemy + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (DDGame.DEBUG) {
                if (coordinates == null) {
                    coordinates = new Actor();
                    coordinates.setSize(3, 3);
                    coordinates.setVisible(true);

                    this.addActor(coordinates);
                }
                if (!hasPathFinder && root.getPath() != null) {
                    List<Vector2> dirs = root.getPath().getDirections();
                    Vector2 pos = new Vector2(root.getCurrentPosition());
                    for (Vector2 v : dirs) {
                        Actor a = new Actor();
                        a.setSize(6, 6);
                        a.setVisible(true);
                        a.setPosition(pos.x + (DDGame.TILE_HEIGHT - a.getWidth()) / 2,
                                pos.y + (DDGame.TILE_HEIGHT - a.getHeight()) / 2);
                        pos.add(new Vector2(v.x * DDGame.TILE_HEIGHT, v.y * DDGame.TILE_HEIGHT));
                        this.addActor(a);
                    }
                    hasPathFinder = true;
                }
                coordinates.setPosition(root.getX() - 1, root.getY() - 1);
            } else {
                if (coordinates != null) coordinates.setVisible(false);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (DDGame.DEBUG) {
            float x = root.getX() + root.getWidth() + 2;
            float y = root.getY() + 2;
            game.getInfo().draw(batch, root.getCurrentLevel1State() + "", x, y + 12);
            game.getInfo().draw(batch, root.getCurrentLevel2State() + "", x, y + 24);
            game.getInfo().draw(batch, root.getCurrentLevel3State() + "", x, y + 36);
            game.getInfo().draw(batch, root.getCurrentLevel4State() + "", x, y + 48);
            game.getInfo().draw(batch, root.getCurrentDirectionType() + "", x, y + 60);
            game.getInfo().draw(batch, Math.round(root.getX()) + ":" + Math.round(root.getY()),
                    root.getX() - 24,
                    root.getY() - 8);
            game.getInfo().draw(batch, this.getLayer().getZIndex() + ":" + this.getZIndex(),
                    root.getX() - 16,
                    root.getY() + root.getHeight());
        }
    }

}
