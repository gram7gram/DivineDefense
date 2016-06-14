package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ua.gram.DDGame;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected final DDGame game;
    private Stage backgroundStage;

    public AbstractScreen(DDGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Log.info("Screen set to " + this.getClass().getSimpleName());
        backgroundStage = new Stage(game.getViewport(), game.getBatch());
        try {
            Texture texture = game.getResources().getRegisteredTexture(Resources.BACKGROUND_TEXTURE);

            Image backgroundImage = new Image(texture);
            backgroundImage.setBounds(0, 0, DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
            backgroundStage.addActor(backgroundImage);
        } catch (NullPointerException e) {
            Log.exc("Could not init default background texture", e);
        }
    }

    @Override
    public void render(float delta) {
        Batch batch = game.getBatch();
        if (Gdx.gl == null || batch == null) return;

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        backgroundStage.act(delta);
        backgroundStage.draw();

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
        if (backgroundStage != null) backgroundStage.dispose();
    }

    public DDGame getGame() {
        return game;
    }
}
