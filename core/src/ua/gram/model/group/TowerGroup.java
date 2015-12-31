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

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerGroup extends ActorGroup<Tower> {

    private final Weapon weapon;
    private final DDGame game;
    private ShapeRenderer shapeRenderer;

    public TowerGroup(DDGame game, Tower tower) {
        super(tower);
        this.game = game;
        //NOTE Add dummy actor to have freedom in swapping z-indexes
        this.addActor(new Actor());
        //NOTE Tower should have a parent, before getting a weapon
        this.addActor(tower);
        this.weapon = tower.getWeapon();
        weapon.setVisible(false);
        this.addActor(weapon);
        this.addActor(new ProgressBar(game.getResources().getSkin(), tower));
        tower.setZIndex(1);
        shapeRenderer = new ShapeRenderer();
        Log.info("Group for " + tower + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && DDGame.DEBUG) {
            game.getInfo().draw(batch, this.getParent().getZIndex() + "",
                    root.getX() - 8,
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

    public Weapon getWeapon() {
        return weapon;
    }
}
