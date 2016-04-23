package ua.gram.model.player;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.model.Resetable;
import ua.gram.model.prototype.PlayerPrototype;
import ua.gram.model.prototype.progress.ProgressPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Player implements Resetable {

    public static String PLAYER_FACTION;
    public static String SYSTEM_FACTION;
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
        if (prototype.faction != null) {
            setFraction(prototype.faction);
            Log.info("Player faction restored to " + PLAYER_FACTION);
        }
    }

    public void chargeCoins(int amount) {
        if (coins < amount)
            throw new IllegalArgumentException("Unable to charge " + amount + " coins");
        Log.info("Player had: " + this.coins + " coins. Now has: " + (coins -= amount));
    }

    public void chargeGems(int amount) {
        if (gems < amount)
            throw new IllegalArgumentException("Unable to charge " + amount + " gems");
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

    public boolean isNewPlayer() {
        return getFactionPrototype() == null;
    }

    public String getFactionPrototype() {
        return prototype.faction;
    }

    public void addGems(int i) {
        this.gems += i;
    }

    /**
     * NOTE Gems are not reset
     */
    @Override
    public void resetObject() {
        coins = prototype.coins;
        restoreHealth();
    }

    public void setFraction(String fraction) {
        prototype.faction = fraction;
        PLAYER_FACTION = fraction;
        SYSTEM_FACTION = getOppositeFaction(fraction);
    }

    public void resetFraction() {
        prototype.faction = null;
        PLAYER_FACTION = null;
        SYSTEM_FACTION = null;
        Log.warn("Game faction were reset!");
    }

    public String getOppositeFaction(String name) {
        if (name.equals(DDGame.FACTION_2)) return DDGame.FACTION_1;
        else if (name.equals(DDGame.FACTION_1)) return DDGame.FACTION_2;
        else throw new GdxRuntimeException("No faction registered as " + name);

    }

    public ProgressPrototype getProgress() {
        return prototype.progress;
    }

    public int getLastUnlockedLevel() {
        int defaultLevel = 1;

        if (prototype.progress == null || prototype.progress.level == null)
            return defaultLevel;

        return prototype.progress.level.lastUnlocked;
    }

    public void incrementLastUnlockedLevel() {
        if (prototype.progress == null || prototype.progress.level == null)
            throw new GdxRuntimeException("Missing player progress");

        ++prototype.progress.level.lastUnlocked;
    }

    public boolean hasCoins(int price) {
        return coins >= price;
    }
}
