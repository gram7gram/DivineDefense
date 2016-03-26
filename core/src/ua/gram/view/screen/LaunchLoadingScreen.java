package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.Resources;
import ua.gram.model.Player;
import ua.gram.view.AbstractLoadingScreen;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LaunchLoadingScreen extends AbstractLoadingScreen {

    public LaunchLoadingScreen(DDGame game) {
        super(game);
        Log.info("LaunchLoadingScreen is OK");
    }

    @Override
    public void show() {
        game.setPlayer(new Player(prototype.player));
        game.getResources().loadTexture(Resources.BACKGROUND_TEXTURE);
        game.createCamera();
        game.createBatch();
        game.createViewport();
        super.show();
    }

    @Override
    public void doAction() {
        if (game.getPlayer().getPrototypeFraction() != null) {
            game.setScreen(new MainMenuScreen(game));
        } else {
            game.setScreen(new FactionScreen(game));
        }
    }

}
