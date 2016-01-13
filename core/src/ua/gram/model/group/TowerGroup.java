package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.misc.ProgressBar;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.state.tower.TowerStateHolder;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends ActorGroup<Tower> {

    private final DDGame game;
    private final ShapeRenderer shapeRenderer;

    public TowerGroup(DDGame game, Tower tower) {
        super(tower);
        shapeRenderer = new ShapeRenderer();
        this.game = game;
        Weapon weapon = tower.getWeapon();
        Actor bar = new ProgressBar(game.getResources().getSkin(), tower);
        this.addActor(bar);
        this.addActor(tower);
        this.addActor(weapon);
        weapon.setVisible(false);
        weapon.setSource(this);
        Log.info("Group for " + tower + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && DDGame.DEBUG) {
            float x = root.getX() + root.getWidth() + 2;
            float y = root.getY() + 2;

            TowerStateHolder holder = root.getStateHolder();

            game.getInfo().draw(batch, holder.getCurrentLevel1State() + "", x, y + 12);
            game.getInfo().draw(batch, holder.getCurrentLevel2State() + "", x, y + 24);
            game.getInfo().draw(batch, Math.round(root.getX()) + ":" + Math.round(root.getY()),
                    root.getX() - 24,
                    root.getY() - 8);

            if (this.getLayer() != null) {
                game.getInfo().draw(batch, this.getLayer().getZIndex() + ":" + this.getZIndex(),
                        root.getX() - 16,
                        root.getY() + root.getHeight());

                batch.end();

                Gdx.gl.glEnable(GL20.GL_BLEND);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(this.getOriginX(), this.getOriginY(),
                        this.getRootActor().getPrototype().range * DDGame.TILE_HEIGHT * 1.5f);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);

                batch.begin();
            }
        }
    }
}
