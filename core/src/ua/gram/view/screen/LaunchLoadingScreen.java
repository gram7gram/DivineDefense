package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.model.prototype.SoundStatePrototype;
import ua.gram.model.prototype.ui.UIPrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;
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

        UIPrototype uiPrototype = game.getPrototype().ui;
        Resources resources = game.getResources();

        for (SoundStatePrototype p : uiPrototype.sounds) {
            resources.loadSound(p.sound);
        }

        for (SoundStatePrototype p : uiPrototype.music) {
            resources.loadMusic(p.sound);
        }
    }

    @Override
    public void onLoadingComplete() {
        game.getAudioManager().startGlobalBackgroundMusic();

        if (game.getPlayer().isNewPlayer()) {
            game.setScreen(new FactionScreen(game));
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
