package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected final DDGame game;
    protected final GamePrototype prototype;

    public AbstractScreen(DDGame game, GamePrototype prototype) {
        this.game = game;
        this.prototype = prototype;
    }

    public AbstractScreen(DDGame game) {
        this.game = game;
        this.prototype = game.getPrototype();
    }

    @Override
    public void render(float delta) {
        if (!DDGame.PAUSE) {
            renderOtherElements(delta);
        }
        renderUiElements(delta);
    }

    /**
     * Allways renders
     */
    public abstract void renderUiElements(float delta);

    /**
     * Renders only if the game is not on PAUSE
     */
    public abstract void renderOtherElements(float delta);

    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height, false);
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
    }

    @Override
    public void hide() {
        Log.warn("Hiding " + this.getClass().getSimpleName());
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Log.warn("Disposing " + this.getClass().getSimpleName());
    }

    public GamePrototype getPrototype() {
        return prototype;
    }

    public DDGame getGame() {
        return game;
    }
}
