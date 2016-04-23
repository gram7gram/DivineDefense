package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.gram.DDGame;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected final DDGame game;
    protected final GamePrototype prototype;
    private final Sprite background;
    private final SpriteBatch batch;

    public AbstractScreen(DDGame game) {
        this.game = game;
        this.prototype = game.getPrototype();
        batch = game.getBatch();
        background = new Sprite(game.getResources().getRegisteredTexture(Resources.BACKGROUND_TEXTURE));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.44f, .62f, .8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.end();

        if (!DDGame.PAUSE) {
            renderOtherElements(delta);
        }
        renderUiElements(delta);
    }

    /**
     * Always renders
     */
    public abstract void renderUiElements(float delta);

    /** Renders only if the game is not on PAUSE */
    public abstract void renderOtherElements(float delta);

    @Override
    public void resize(int width, int height) {
//        NOTE update() do nothing with width && height...
//        game.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        DDGame.PAUSE = true;
    }

    @Override
    public void resume() {
        DDGame.PAUSE = false;
    }

    @Override
    public void show() {
        Log.info("Screen set to " + this.getClass().getSimpleName());
        background.setBounds(0, 0, DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
    }

    @Override
    public void hide() {
        Log.warn("Hiding " + this.getClass().getSimpleName());
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Log.warn("Disposing " + this.getClass().getSimpleName());
        background.getTexture().dispose();
    }

    public GamePrototype getPrototype() {
        return prototype;
    }

    public DDGame getGame() {
        return game;
    }
}
