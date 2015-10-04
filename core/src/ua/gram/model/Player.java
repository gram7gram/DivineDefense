package ua.gram.model;

import com.badlogic.gdx.Gdx;
import ua.gram.model.actor.Tower;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Player {

    public static String PLAYER_FRACTION;
    public static String SYSTEM_FRACTION;
    public static Tower.Strategy TOWER_PRIMARY_STRATEGY;
    public static Tower.Strategy TOWER_SECONDARY_STRATEGY;
    public static Tower.Strategy TOWER_STUN_STRATEGY;
    public static Tower.Strategy TOWER_SPECIAL_STRATEGY;
    public static int UNLOCKED_TOWER_PRIMARY;
    public static int UNLOCKED_TOWER_SECONDARY;
    public static int UNLOCKED_TOWER_STUN;
    public static int DEFAULT_HEALTH;
    public static int UNLOCKED_TOWER_SPECIAL;
    private boolean isDefault;
    private int level;
    private int health;
    private int coins;
    private int gems;

    public void chargeCoins(int amount) {
        if (coins < amount) throw new IllegalArgumentException("Unable to charge " + amount + " coins");
        Gdx.app.log("INFO", "Player had: " + this.coins + " coins. Now has: " + (this.coins -= amount));
    }

    public void chargeGems(int amount) {
        if (gems < amount) throw new IllegalArgumentException("Unable to charge " + amount + " gems");
        Gdx.app.log("INFO", "Player had: " + this.gems + " gems. Now has: " + (this.gems -= amount));
    }

    public void addCoins(int amount) {
        if (this.coins < 0) throw new IllegalArgumentException("Player cannot have less then 0 coin");
        Gdx.app.log("INFO", "Player had: " + this.coins + " coins. Now has: " + (this.coins += amount));
    }

    public void decreaseHealth() {
        if (this.health <= 0) throw new IllegalArgumentException("Player cannot have less than 0 life");
        Gdx.app.log("INFO", "Player received 1 damage! His health is: " + (this.health -= 1));
    }

    public void restoreHealth() {
        this.health = DEFAULT_HEALTH;
        Gdx.app.log("INFO", "Player's health is restored to " + DEFAULT_HEALTH + " lives");
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

    public void nextLevel() {
        this.level += 1;
    }

    public void addGems(int i) {
        this.gems += i;
    }
}
