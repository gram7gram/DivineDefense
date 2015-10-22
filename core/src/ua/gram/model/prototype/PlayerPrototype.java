package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class PlayerPrototype implements Prototype {
    public long id;
    public byte health;
    public short coins;
    public short gems;
    public String fraction;
    public byte towerPrimaryStrategy;
    public byte towerSecondaryStrategy;
    public byte towerStunStrategy;
    public byte towerSpecialStrategy;
    public int unlockedTowerPrimary;
    public int unlockedTowerSecondary;
    public int unlockedTowerStun;
    public int unlockedTowerSpecial;
}
