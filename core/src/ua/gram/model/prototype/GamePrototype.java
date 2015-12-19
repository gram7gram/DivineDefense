package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GamePrototype implements Prototype {
    public PlayerPrototype player;
    public MapPrototype map;
    public MarketPrototype market;
    public LevelPrototype[] levels;
    public WavePrototype[] waves;
    public EnemyPrototype[] enemies;
    public TowerPrototype[] towers;

    public abstract String getConfigPath();

    public abstract <P extends ParametersPrototype> P getParameters();
}
