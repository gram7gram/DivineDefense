package ua.gram.model.enums;

/**
 * The states of Actors in the game
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Types {

    public static Types.TowerLevels getTowerLevelType(int level) {
        switch (level) {
            case 1:
                return Types.TowerLevels.Lvl1;
            case 2:
                return Types.TowerLevels.Lvl2;
            case 3:
                return Types.TowerLevels.Lvl3;
            case 4:
                return Types.TowerLevels.Lvl4;
            default:
                throw new NullPointerException("Unknown tower level: " + level);
        }
    }

    public enum Tower {
        STUN, SECONDARY, PRIMARY, SPECIAL
    }

    public enum TowerState {
        IDLE, BUILD, ATTACK, SELL
    }

    public enum EnemyState {
        WALKING, STUN, ABILITY, DEAD, FINISH, SPAWN, IDLE
    }

    public enum BossState {
        IDLE, DEFEATED, EXCLAMATE, COMMAND
    }

    public enum TowerLevels {
        Lvl1, Lvl2, Lvl3, Lvl4
    }
}
