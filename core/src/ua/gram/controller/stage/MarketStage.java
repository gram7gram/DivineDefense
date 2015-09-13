package ua.gram.controller.stage;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.listener.DebugListener;
import ua.gram.model.actor.market.MarketCategoryContainer;
import ua.gram.model.group.MarketGroup;
import ua.gram.model.prototype.MarketCategoryPrototype;
import ua.gram.view.screen.MarketScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketStage extends Stage {

    private final DDGame game;

    public MarketStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        if (DDGame.DEBUG) this.addListener(new DebugListener(this));
        this.game = game;
        this.setDebugAll(DDGame.DEBUG);
        MarketCategoryPrototype[] prototypes = new Json().fromJson(
                MarketCategoryPrototype[].class,
                Resources.loadFile(Resources.MARKET));

        MarketGroup root = new MarketGroup(game, prototypes);

        this.addActor(root);

    }
}
