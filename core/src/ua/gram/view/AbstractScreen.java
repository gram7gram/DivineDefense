package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.gram.DDGame;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected final DDGame game;
    protected final SpriteBatch batch;
    private Texture texture;

    public AbstractScreen(DDGame game) {
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        Log.info("Screen set to " + this.getClass().getSimpleName());
        try {
            texture = game.getResources().getRegisteredTexture(Resources.BACKGROUND_TEXTURE);
        } catch (NullPointerException e) {
            Log.exc("Could not init default background texture", e);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.gl == null || batch == null) return;

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (texture != null) {
            batch.begin();
            batch.draw(texture, 0, 0, 900, 480);
            batch.end();
        }

        if (!DDGame.PAUSE) {
            renderNoPause(delta);
        } else {
            renderOnPause(delta);
        }

        renderAlways(delta);

        if (DDGame.DEBUG) {
            batch.begin();
            game.getInfo().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5, 15);
            game.getInfo().draw(batch, "Pause: " + DDGame.PAUSE, 5, 30);
            batch.end();
        }
    }

    protected abstract void renderAlways(float delta);

    /**
     * Renders only if the game is not on PAUSE
     */
    protected abstract void renderNoPause(float delta);

    protected void renderOnPause(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        Log.warn("App enters pause state");
        DDGame.pauseGame();
    }

    @Override
    public void resume() {
        Log.warn("App resumes");
        DDGame.resumeGame();
    }

    @Override
    public void hide() {
        Log.warn("Hiding " + this.getClass().getSimpleName());
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Log.warn("Disposing " + this.getClass().getSimpleName());
        if (texture != null) texture.dispose();
    }

    public DDGame getGame() {
        return game;
    }
}
