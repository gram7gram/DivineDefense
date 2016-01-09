package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype<P extends ParametersPrototype> implements Prototype {
    public PlayerPrototype player;
    public MapPrototype map;
    public MarketPrototype market;
    public LevelConfigPrototype level;
    public WavePrototype[] waves;
    public EnemyPrototype[] enemies;
    public TowerPrototype[] towers;

    public abstract String getConfigPath();

    public abstract P getParameters();
}
