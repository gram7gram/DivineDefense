package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.Player;
import ua.gram.model.prototype.PlayerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PlayerFactory implements Factory<Player, PlayerPrototype> {

    public static Player defaults() {
        Player player = new Player();
        player.setCoins(1000);
        player.setGems(7);
        player.setHealth(10);
        Player.DEFAULT_HEALTH = 10;
//        Player.TOWER_PRIMARY_STRATEGY = Tower.Strategy.STRONGEST;
//        Player.TOWER_SECONDARY_STRATEGY = Tower.Strategy.STRONGEST;
//        Player.TOWER_STUN_STRATEGY = Tower.Strategy.STRONGEST;
//        Player.TOWER_SPECIAL_STRATEGY = Tower.Strategy.STRONGEST;
        Player.UNLOCKED_TOWER_PRIMARY = 1;
        Player.UNLOCKED_TOWER_SECONDARY = 1;
        Player.UNLOCKED_TOWER_STUN = 1;
        Player.UNLOCKED_TOWER_SPECIAL = 1;
        return player;
    }

    @Override
    public Player create(DDGame game, PlayerPrototype prototype) {
        return new Player(prototype);
    }
}
