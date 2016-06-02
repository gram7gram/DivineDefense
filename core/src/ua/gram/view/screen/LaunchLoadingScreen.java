package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.utils.Log;
import ua.gram.view.AbstractLoadingScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LaunchLoadingScreen extends AbstractLoadingScreen {

    public LaunchLoadingScreen(DDGame game) {
        super(game);
        Log.info("LaunchLoadingScreen is OK");
    }

    @Override
    public void show() {
        game.createPlayer();
        super.show();
    }

    @Override
    public void onLoad() {
        if (game.getPlayer().isNewPlayer()) {
            game.setScreen(new FactionScreen(game));
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
