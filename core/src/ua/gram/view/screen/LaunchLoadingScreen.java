package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.factory.PlayerFactory;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads main resources into memory, but not all. Also creates essential dislay elements: camera, viewport and batch.
 * FIXME Add animation instead of loading label (will increase launch speed)
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LaunchLoadingScreen extends AbstractLoadingScreen {

    public LaunchLoadingScreen(DDGame game) {
        super(game);
    }

    @Override
    public void show() {
//        game.getSecurity().checkSum();
        game.setPlayer(PlayerFactory.defaults());//game.getSecurity().load(game))
        game.getResources().loadBasicFiles();
        game.getResources().loadTexture(Resources.BACKGROUND_TEXTURE);
        game.getResources().loadTexture(Resources.LOCK_TEXTURE);
        game.createCamera();
        game.createBatch();
        game.createViewport();
        super.show();
    }

    @Override
    public void doAction() {
        game.setScreen(new FractionScreen(game));
    }

}
