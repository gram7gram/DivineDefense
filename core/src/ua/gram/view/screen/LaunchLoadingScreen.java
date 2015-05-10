package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.factory.PlayerFactory;
import ua.gram.model.Player;
import ua.gram.view.AbstractLoadingScreen;

/**
 * TODO Add animation instead of loading label (will increase launch speed)
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LaunchLoadingScreen extends AbstractLoadingScreen {

    public LaunchLoadingScreen(DDGame game) {
        super(game);
    }

    @Override
    public void show() {
        game.getResources().loadBasicFiles();
        game.createCamera();
        game.createBatch();
        game.createViewport();
        if (!DDGame.DEBUG) {
            game.getSecurity().checkSum();
        }
        game.setPlayer(PlayerFactory.defaults());//game.getSecurity().load(game));
        super.show();
    }

    @Override
    public void doAction() {
        game.setScreen(new FractionScreen(game));
    }

}
