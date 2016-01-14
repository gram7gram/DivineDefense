package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype<P extends ParametersPrototype> implements PrototypeInterface {
    public PlayerPrototype player;
    public MapPrototype map;
    public ua.gram.model.prototype.market.MarketPrototype market;
    public LevelConfigPrototype level;
    public WavePrototype[] waves;
    public ua.gram.model.prototype.enemy.EnemyPrototype[] enemies;
    public ua.gram.model.prototype.tower.TowerPrototype[] towers;

    public abstract String getConfigPath();

    public abstract P getParameters();
}
