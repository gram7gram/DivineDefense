package ua.gram.model.player;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.model.prototype.player.PlayerPreferences;
import ua.gram.model.prototype.player.PlayerPrototype;
import ua.gram.model.prototype.progress.ProgressPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Player implements Disposable {

    public static String PLAYER_FACTION;
    public static String SYSTEM_FACTION;
    public static int DEFAULT_HEALTH;
    private final PlayerPrototype prototype;
    private final PlayerPreferences preferences;
    private final ProgressPrototype progress;
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
        preferences = prototype.preferences;
        progress = prototype.progress;

        if (prototype.faction != null) {
            setFraction(prototype.faction);
            Log.info("Player faction restored to " + PLAYER_FACTION);
        }
    }

    public void chargeCoins(int amount) {
        if (coins < amount)
            throw new IllegalArgumentException("Unable to charge " + amount + " coins");
        coins -= amount;
        Log.info("Player is charged " + coins + " coins");
    }

    public void chargeGems(int amount) {
        if (gems < amount)
            throw new IllegalArgumentException("Player cannot have less then 0 gems");
        gems -= amount;
        Log.info("Player is charged " + gems + " gems");
    }

    public void addCoins(int amount) {
        if (coins < 0) throw new IllegalArgumentException("Player cannot have less then 0 coin");
        coins += amount;
        Log.info("Player earned " + coins + " coins");
    }

    public void decreaseHealth(float amount) {
        if (health <= 0) throw new IllegalArgumentException("Player cannot have less than 0 life");
        health -= 1;
        Log.info("Player received " + amount + " damage! His health is: " + health);
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
    public void dispose() {
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

    public PlayerPreferences getPreferences() {
        return preferences;
    }

    public ProgressPrototype getProgress() {
        return progress;
    }
}
