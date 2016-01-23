package ua.gram.model.prototype;

import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.model.prototype.market.MarketPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype<P extends ParametersPrototype> implements PrototypeInterface {
    public PlayerPrototype player;
    public MapPrototype map;
    public MarketPrototype market;
    public LevelConfigPrototype levelConfig;
    public EnemyPrototype[] enemies;
    public TowerPrototype[] towers;
    public RemoteConfigurationPrototype remoteConfig;

    public abstract String getConfigPath();

    public abstract P getParameters();
}
