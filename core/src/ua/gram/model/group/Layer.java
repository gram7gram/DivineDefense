package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Layer extends Group {

    private DDGame game;
    private ShapeRenderer shape;
    private int index;

    public Layer() {
    }

    public Layer(DDGame game, int index) {
        this.game = game;
        this.index = index;
        shape = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.setDebug(DDGame.DEBUG);
        if (DDGame.DEBUG && game != null && shape != null) {

            game.getInfo().draw(batch, this.getZIndex() + "",
                    getX() + 2, getY());

            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.GREEN);
            shape.rect(0, DDGame.WORLD_HEIGHT - index * DDGame.TILE_HEIGHT,
                    DDGame.WORLD_WIDTH, DDGame.TILE_HEIGHT);
            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
        }
    }
}

