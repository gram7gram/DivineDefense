package ua.gram.model.group;

import com.badlogic.gdx.graphics.g2d.Batch;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateHolder;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.HealthBar;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyGroup extends ActorGroup<Enemy> {

    private final DDGame game;

    public EnemyGroup(DDGame game, Enemy enemy) {
        super(enemy);
        this.game = game;
        addActor(enemy);
        addActor(new HealthBar(game.getResources().getSkin(), enemy));
        enemy.toFront();
        setVisible(true);
        Log.info("Group for " + enemy + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (DDGame.DEBUG) {
            float x = root.getX() + root.getWidth() + 2;
            float y = root.getY() + 2;
            String[] fullAnimationName = root.getPoolableAnimation().getName().split("/");
            String name = fullAnimationName[fullAnimationName.length - 2]
                    + "/" + fullAnimationName[fullAnimationName.length - 1];

            EnemyStateHolder holder = root.getStateHolder();

            game.getInfo().draw(batch, holder.getCurrentLevel1State() + "", x, y + 12);
            game.getInfo().draw(batch, holder.getCurrentLevel2State() + "", x, y + 24);
            game.getInfo().draw(batch, holder.getCurrentLevel3State() + "", x, y + 36);
            game.getInfo().draw(batch, holder.getCurrentLevel4State() + "", x, y + 48);
            game.getInfo().draw(batch, root.getDirectionHolder().getCurrentDirectionType() + "", x, y + 60);
            game.getInfo().draw(batch, name, x, y + 72);
            game.getInfo().draw(batch, Math.round(root.getX()) + ":" + Math.round(root.getY()),
                    root.getX() - 24,
                    root.getY() - 8);
            game.getInfo().draw(batch, getLayer().getZIndex() + ":" + getZIndex(),
                    root.getX() - 24,
                    root.getY() + root.getHeight());
        }
    }

}
