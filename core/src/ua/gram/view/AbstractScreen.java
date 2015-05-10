package ua.gram.view;

import com.badlogic.gdx.Screen;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractScreen implements Screen {

    protected DDGame game;

    public AbstractScreen(DDGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        render_ui(delta);
        if (!DDGame.PAUSE) {
            render_other(delta);
        }
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
}
