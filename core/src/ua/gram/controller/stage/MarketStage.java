package ua.gram.controller.stage;

import com.badlogic.gdx.utils.Json;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.group.MarketTable;
import ua.gram.model.group.PlayerDataTable;
import ua.gram.model.prototype.MarketCategoryPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketStage extends AbstractStage {

    public MarketStage(final DDGame game) {
        super(game);
        MarketCategoryPrototype[] prototypes = new Json().fromJson(
                MarketCategoryPrototype[].class,
                Resources.loadFile(Resources.MARKET));

        MarketTable root = new MarketTable(game, prototypes);

        PlayerDataTable playerData = new PlayerDataTable(game);
        playerData.setPosition(
                (DDGame.WORLD_WIDTH - playerData.getWidth())/2f,
                20);

        this.addActor(root);
        this.addActor(playerData);
    }
}
