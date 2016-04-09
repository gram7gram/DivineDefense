package ua.gram.model.prototype;

import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype<P extends ParametersPrototype> extends Prototype {

    public PlayerPrototype player;
    public EnemyPrototype[] enemies;
    public TowerPrototype[] towers;
    public WeaponPrototype[] weapons;
    public LevelConfigPrototype levelConfig;
    public UIPrototype ui;

    public abstract String getConfigPath();

    public String getFullConfigPath() {
        return System.getProperty("user.home") + "/" + getConfigPath() + "parameters.json";
    }

    public abstract P getParameters();
}
