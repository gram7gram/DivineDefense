package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.factory.PlayerFactory;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads main resources into memory, but not all.
 * Also creates essential dislay elements: camera, viewport and batch.
 * FIXME Add animation instead of loading label (will increase launch speed)
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LaunchLoadingScreen extends AbstractLoadingScreen {

    public LaunchLoadingScreen(DDGame game) {
        super(game);
        Gdx.app.log("INFO", "LaunchLoadingScreen is OK");
    }

    @Override
    public void show() {
        Gdx.app.log("INFO", "Screen set to GameScreen");
        game.setPlayer(PlayerFactory.defaults());
        game.getResources().loadTexture(Resources.BACKGROUND_TEXTURE);
        game.createCamera();
        game.createBatch();
        game.createViewport();
        super.show();
    }

    @Override
    public void doAction() {
//        game.setScreen(new MarketScreen(game));
//        game.setScreen(new FractionScreen(game));
        game.setScreen(new LevelSelectScreen(game));
    }

}
