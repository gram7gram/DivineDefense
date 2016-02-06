package ua.gram.model;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.prototype.PlayerPrototype;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Player implements ResetableInterface {

    public static String PLAYER_FRACTION;
    public static String SYSTEM_FRACTION;
    public static int DEFAULT_HEALTH;
    public final PlayerPrototype prototype;
    private boolean isDefault;
    private int level;
    private int health;
    private int coins;
    private int gems;

    public Player(PlayerPrototype prototype) {
        this.prototype = prototype;
        this.setDefault(false);
        coins = prototype.coins;
        gems = prototype.gems;
        health = prototype.health;
    }

    public void chargeCoins(int amount) {
        if (coins < amount) throw new IllegalArgumentException("Unable to charge " + amount + " coins");
        Log.info("Player had: " + this.coins + " coins. Now has: " + (coins -= amount));
    }

    public void chargeGems(int amount) {
        if (gems < amount) throw new IllegalArgumentException("Unable to charge " + amount + " gems");
        Log.info("Player had: " + this.gems + " gems. Now has: " + (gems -= amount));
    }

    public void addCoins(int amount) {
        if (coins < 0) throw new IllegalArgumentException("Player cannot have less then 0 coin");
        Log.info("Player had: " + coins + " coins. Now has: " + (coins += amount));
    }

    public void decreaseHealth() {
        if (health <= 0) throw new IllegalArgumentException("Player cannot have less than 0 life");
        Log.info("Player received 1 damage! His health is: " + (health -= 1));
    }

    public void restoreHealth() {
        health = prototype.health;
        Log.info("Player's health is restored to " + health + " lives");
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int money) {
        this.coins = money;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public boolean isDead() {
        return this.health == 0;
    }

    public int nextLevel() {
        level += 1;
        return level;
    }

    public String getPrototypeFraction() {
        return prototype.fraction;
    }

    public void addGems(int i) {
        this.gems += i;
    }

    /**
     * NOTE Gems are not reset
     */
    @Override
    public void reset() {
        coins = prototype.coins;
        restoreHealth();
    }

    public void setFraction(String fraction) {
        prototype.fraction = fraction;
        PLAYER_FRACTION = fraction;
        SYSTEM_FRACTION = getOppositeFraction(fraction);
    }

    public void resetFraction() {
        prototype.fraction = null;
        PLAYER_FRACTION = null;
        SYSTEM_FRACTION = null;
        Log.warn("Game fraction were reset!");
    }

    public String getOppositeFraction(String name) {
        switch (name) {
            case DDGame.ANGEL:
                return DDGame.DEMON;
            case DDGame.DEMON:
                return DDGame.ANGEL;
            default:
                throw new GdxRuntimeException("No fraction registered as " + name);
        }
    }
}
