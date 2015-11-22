package ua.gram.view;

import com.badlogic.gdx.Screen;
import ua.gram.DDGame;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected DDGame game;
    protected GamePrototype prototype;

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
            render_other(delta);
        }
        render_ui(delta);
    }

    /**
     * Allways renders
     *
     * @param delta
     */
    public abstract void render_ui(float delta);

    /**
     * Renders only if the game is not on PAUSE
     *
     * @param delta
     */
    public abstract void render_other(float delta);

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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public DDGame getGame() {
        return game;
    }
}
