package ua.gram.controller.factory;

import ua.gram.model.Player;
import ua.gram.model.actor.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PlayerFactory implements Factory<Player> {

    public String id;
    public byte health;
    public short coins;
    public short gems;
    public String fraction;
    public Tower.Strategy towerPrimaryStrategy;
    public Tower.Strategy towerCannonStrategy;
    public Tower.Strategy towerStunStrategy;
    public Tower.Strategy towerSpecialStrategy;
    public int unlockedTowerPrimary;
    public int unlockedTowerCannon;
    public int unlockedTowerStun;
    public int unlockedTowerSpecial;

    public static Player defaults() {
        Player player = new Player();
        player.setCoins(1000);
        player.setGems(7);
        player.setHealth(10);
        Player.DEFAULT_HEALTH = 10;
        Player.TOWER_PRIMARY_STRATEGY = Tower.Strategy.STRONGEST;
        Player.TOWER_CANNON_STRATEGY = Tower.Strategy.STRONGEST;
        Player.TOWER_STUN_STRATEGY = Tower.Strategy.STRONGEST;
        Player.TOWER_SPECIAL_STRATEGY = Tower.Strategy.STRONGEST;
        Player.UNLOCKED_TOWER_PRIMARY = 1;
        Player.UNLOCKED_TOWER_CANNON = 1;
        Player.UNLOCKED_TOWER_STUN = 1;
        Player.UNLOCKED_TOWER_SPECIAL = 1;
        return player;
    }

    @Override
    public Player create(Class<? extends Player> type) {
        Player player = new Player();
        player.setDefault(false);
        player.setCoins(coins);
        player.setGems(gems);
        player.setHealth(health);
        Player.DEFAULT_HEALTH = new Integer(health);//prevent referencing
        Player.TOWER_PRIMARY_STRATEGY = towerPrimaryStrategy;
        Player.TOWER_CANNON_STRATEGY = towerCannonStrategy;
        Player.TOWER_STUN_STRATEGY = towerStunStrategy;
        Player.TOWER_SPECIAL_STRATEGY = towerSpecialStrategy;
        Player.UNLOCKED_TOWER_PRIMARY = unlockedTowerPrimary;
        Player.UNLOCKED_TOWER_CANNON = unlockedTowerCannon;
        Player.UNLOCKED_TOWER_STUN = unlockedTowerStun;
        Player.UNLOCKED_TOWER_SPECIAL = unlockedTowerSpecial;
        return player;
    }
}
