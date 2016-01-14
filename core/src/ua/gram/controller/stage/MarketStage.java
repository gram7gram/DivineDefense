package ua.gram.controller.stage;

import ua.gram.DDGame;
import ua.gram.model.group.MarketTable;
import ua.gram.model.group.PlayerDataTable;
import ua.gram.model.prototype.market.MarketCategoryPrototype;
import ua.gram.model.prototype.market.MarketPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketStage extends AbstractStage {

    public MarketStage(final DDGame game, MarketPrototype prototype) {
        super(game);
        MarketCategoryPrototype[] prototypes = prototype.categories;

        MarketTable root = new MarketTable(game, prototypes);

        PlayerDataTable playerData = new PlayerDataTable(game);
        playerData.setPosition(
                (DDGame.WORLD_WIDTH - playerData.getWidth())/2f,
                20);

        this.addActor(root);
        this.addActor(playerData);
    }
}
