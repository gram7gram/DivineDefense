package ua.gram.model.enums;

/**
 * The states of Actors in the game
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Types {

    public enum Tower {
        STUN, SECONDARY, PRIMARY, SPECIAL
    }

    public enum TowerState {
        IDLE, BUILD, ATTACK, SELL
    }

    public enum EnemyState {
        WALKING, STUN, ABILITY, DEAD, FINISH, SPAWN, IDLE
    }

    public enum TowerLevels {
        Lvl1, Lvl2, Lvl3, Lvl4
    }
}
