package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.controller.pool.animation.AnimationController.Types;
import ua.gram.controller.tower.TowerAnimationController;
import ua.gram.controller.tower.TowerLevelAnimationContainer;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.view.stage.GameBattleStage;
import ua.gram.view.stage.group.TowerGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * TODO Different animations: IDLE, BUILDING, SELLING, SHOOTING
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Tower extends Actor {

    protected DDGame game;
    public final float build_delay = 2;
    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    public static final byte MAX_POWER_LEVEL = 4;
    protected final int animationWidth = 40;
    protected final int animationHeight = 60;
    protected GameBattleStage stage_battle;
    protected TextureRegion currentFrame;
    protected TowerAnimationController controller;
    protected TowerLevelAnimationContainer container;
    protected Animation animation;
    protected Weapon weapon;
    private float stateTime;
    public float damage;
    public float range;
    public float rate;
    public int cost;
    public int power_lvl;
    public int tower_lvl;
    public Strategy strategy;
    public boolean isActive;
    public boolean isBuilding;

    public Tower(DDGame game, float[] stats) {
        this.game = game;
        this.power_lvl = (int) stats[0];
        this.tower_lvl = (int) stats[1];
        this.damage = stats[2];
        this.range = stats[3];
        this.rate = stats[4];
        this.cost = (int) stats[5];
        this.strategy = Strategy.STRONGEST;
        isActive = false;
        isBuilding = false;
        this.setSize(animationWidth, animationHeight);
        this.setBounds(getX(), getY(), animationWidth, animationHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE) {
            try {
                stateTime += Gdx.graphics.getDeltaTime();
                currentFrame = animation.getKeyFrame(stateTime, true);
            } catch (NullPointerException e) {
                //Prevent too soon rendering.
            }
        }
        if (currentFrame != null)
            batch.draw(currentFrame, this.getX(), this.getY());
    }

    private float count = 0;
    public float countBuilding = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (isActive) {
                if (count >= this.rate) {
                    count = 0;
                    ArrayList<Enemy> victims = scan();
                    if (!victims.isEmpty()) {
                        attack(chooseTargets(victims));
                    } else {
                        weapon.setVisible(false);
                    }
                } else {
                    count += delta;
                }
            } else if (isBuilding) {
                if (countBuilding >= build_delay) {
                    countBuilding = 0;
                    isBuilding = false;
                    isActive = true;
                    this.setZIndex(stage_battle.getIndexByActor(this));
                    this.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            ((TowerGroup) getParent()).toggleControls();
                        }
                    });
                    Gdx.app.log("INFO", this.getClass().getSimpleName() + " is builded");
                } else {
                    countBuilding += delta;
                }
            }
        }
    }

    public void attack(List<Enemy> victims) {
        for (Enemy victim : victims) {
            weapon.updatePosition(this.getCenterPoint(), victim.getCenterPoint());
            weapon.setVisible(true);
            victim.receiveDamage(this.damage);
            Gdx.app.log("INFO", this + " attacked " + victim + "@" + victim.hashCode());
        }
    }

    private boolean hasWeapon = false;

    /**
     * Grab Enemies in range.
     */
    private ArrayList<Enemy> scan() {
        if (!hasWeapon) {
            stage_battle.addActor(weapon);
            hasWeapon = true;
        }
        ArrayList<Enemy> targets = new ArrayList<Enemy>();
        for (Enemy enemy : stage_battle.getEnemiesOnMap()) {
            if (isInRange(enemy) && enemy.health > 0) {
                Gdx.app.log("INFO", enemy + "@" + enemy.hashCode() + " is in range");
                targets.add(enemy);
            }
        }
        return targets;
    }

    /**
     * Choose Enemies according to Tower level and strategy.
     *
     * @param victims - potential targets in range
     * @return - actual targets
     */
    private List<Enemy> chooseTargets(ArrayList<Enemy> victims) {
        List<Enemy> targets = new ArrayList<Enemy>();
        switch (this.strategy) {
            case STRONGEST:
                Collections.sort(victims, new EnemyMaxHealthComparator());
                break;
            case WEAKEST:
                Collections.sort(victims, new EnemyMinHealthComparator());
                break;
            case NEAREST:
                Collections.sort(victims, new EnemyDistanceComparator(this));
                break;
            case RANDOM:
                byte index = victims.size() > 1 ? (byte) (Math.random() * (victims.size())) : 0;
                targets.add(victims.get(index));
                return targets;
            default:
                throw new UnsupportedOperationException("Target choosing for Random is not yet implemented");
        }
        if (this.getTowerLevel() > 1 && victims.size() > 1) {
            targets = victims.subList(0, victims.size() > this.getTowerLevel() ? this.getTowerLevel() : victims.size());
        } else {
            targets.add(victims.get(0));
        }
        return targets;
    }

    private boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getX() + enemy.getWidth() / 2f, enemy.getY() + enemy.getHeight() / 2f);
        Vector2 towerPos = new Vector2(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
        return enemyPos.dst(towerPos) <= this.range * DDGame.TILEHEIGHT;
    }

    /**
     * Increases stats of the tower, according to the level and the tower type.
     * TODO Increase stats
     * TODO Charge according to the next level
     * TODO Change Range radius
     */
    public void upgrade() {
        tower_lvl++;
        this.setLevelAnimationContainer(tower_lvl);
        changeAnimation(Types.BUILD);
        game.getPlayer().chargeCoins(30);
        //update range
        Gdx.app.log("INFO", this + " is upgraded to " + tower_lvl + " level");
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * TODO Set corresponding type.
     *
     * @param type desired animation type
     */
    public void changeAnimation(Types type) {
        Gdx.app.log("INFO", "Tower animation changed to: " + tower_lvl + "x" + type.name());
        type = Types.IDLE;//remove
        this.setLevelAnimationContainer(tower_lvl);
        setAnimation(container.getAnimation(type));
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public float getDamage() {
        return damage;
    }

    public void setStageBattle(GameBattleStage stage_battle) {
        this.stage_battle = stage_battle;
    }

    public float getRange() {
        return range;
    }

    public float getRate() {
        return rate;
    }

    public int getCost() {
        return cost;
    }

    public int getUpgradeLevel() {
        return power_lvl;
    }

    public int getTowerLevel() {
        return tower_lvl;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public TowerAnimationController getController() {
        return controller;
    }

    public void setController(TowerAnimationController controller) {
        this.controller = controller;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;

    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + this.getWidth() / 2f,
                this.getY() + this.getHeight() / 2f
        );
    }

    public void setLevelAnimationContainer(int level) {
        container = controller.getLevelAnimationContainer(level);
    }


    public enum Strategy {

        NEAREST, RANDOM, WEAKEST, STRONGEST
    }

    private class EnemyMaxHealthComparator implements Comparator<Enemy> {
        @Override
        public int compare(Enemy enemy1, Enemy enemy2) {
            return (int) (enemy1.health - enemy2.health);
        }
    }

    private class EnemyMinHealthComparator implements Comparator<Enemy> {
        @Override
        public int compare(Enemy enemy1, Enemy enemy2) {
            return (int) (enemy2.health - enemy1.health);
        }
    }

    private class EnemyDistanceComparator implements Comparator<Enemy> {

        private Tower tower;

        public EnemyDistanceComparator(Tower tower) {
            this.tower = tower;
        }

        @Override
        public int compare(Enemy enemy1, Enemy enemy2) {
            Vector2 pos1 = enemy1.getCenterPoint();
            Vector2 pos2 = enemy2.getCenterPoint();
            Vector2 posTower = tower.getCenterPoint();
            float dist1 = pos1.dst(posTower);
            float dist2 = pos2.dst(posTower);
            return (int) (dist1 - dist2);
        }
    }
}
