package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import ua.gram.DDGame;
import ua.gram.controller.pool.animation.AnimationController.Types;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.tower.TowerAnimationController;
import ua.gram.controller.tower.TowerLevelAnimationContainer;

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

    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    public static final byte MAX_POWER_LEVEL = 4;
    public final float build_delay = 2;
    public final int animationWidth = 60;
    public final int animationHeight = 90;
    private final AimIcon aim;
    public float damage;
    public float range;
    public float rate;
    public int cost;
    public int power_lvl;
    public int tower_lvl;
    public Strategy strategy;
    public boolean isActive;
    public boolean isBuilding;
    public float countBuilding = 0;
    protected DDGame game;
    protected GameBattleStage stage_battle;
    protected TextureRegion currentFrame;
    protected TowerAnimationController controller;
    protected TowerLevelAnimationContainer container;
    protected Animation animation;
    protected Weapon weapon;
    private float stateTime;
    private float count = 0;
    private int targetIndex = -1;
    private Enemy victim;

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
        aim = new AimIcon(game.getResources());
        controller = new TowerAnimationController(game.getResources().getSkin(), this);
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
        if (currentFrame != null) {
            batch.draw(currentFrame, this.getX(), this.getY());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() * 1.5f / 2f);
            if (isBuilding) {
                if (countBuilding >= build_delay) {
                    countBuilding = 0;
                    isBuilding = false;
                    isActive = true;
                    this.setTouchable(Touchable.enabled);
                    stage_battle.addActor(aim);
                    weapon.setSource(this);
                    Gdx.app.log("INFO", this + " is builded");
                } else {
                    countBuilding += delta;
                }
            } else if (isActive) {
                if (count >= this.rate) {
                    count = 0;
                    if (victim == null) {
                        ArrayList<Enemy> targets = scan();
                        if (!targets.isEmpty()) {
                            List<Enemy> victims = chooseVictims(targets);
                            if (!victims.isEmpty()) {
                                int index = ++targetIndex <= victims.size() - 1 ? targetIndex : 0;
                                victim = victims.get(index % 2 == 0 && index != 0 ? index : victims.size() - 1 - index);
                                if (victim != null && isInRange(victim)) {
                                    victim.isAttacked = true;
                                    aim.setEnemy(victim);
                                    weapon.setTarget(victim);
                                    aim.setVisible(true);
                                    weapon.setVisible(true);
                                    weapon.toFront();
                                    victim.receiveDamage(this.damage);
                                }
                            } else if (victim != null) {
                                victim.isAttacked = false;
                            } else {
                                Gdx.app.error("ERROR", "Could not choose targets!");
                            }
                        }
//                    } else if (!isInRange(victim) && victim.isAttacked) {
//                        victim.isAttacked = false;
//                        victim = null;
//                        aim.setVisible(false);
//                        weapon.setTarget(null);
//                        weapon.setVisible(false);
                    } else {
                        victim.isAttacked = false;
                        victim = null;
                        aim.setVisible(false);
                        weapon.setTarget(null);
                        weapon.setVisible(false);
                    }
                } else {
                    count += delta;
                }
            }
        }
    }

    /**
     * Grab Enemies in range.
     */
    private ArrayList<Enemy> scan() {
        ArrayList<Enemy> targets = new ArrayList<Enemy>();
        for (Enemy enemy : stage_battle.getEnemiesOnMap()) {
            if (isInRange(enemy) && enemy.health > 0) {
//                Gdx.app.log("INFO", enemy + "@" + enemy.hashCode() + " is in range");
                targets.add(enemy);
            }
        }
        return targets;
    }

    /**
     * Choose Enemies according to Tower level and strategy.
     *
     * @param victims potential victims in range
     * @return actual victims
     */
    private List<Enemy> chooseVictims(ArrayList<Enemy> victims) {
        List<Enemy> enemyTargets = new ArrayList<Enemy>();
        switch (this.strategy) {
            case STRONGEST:
                Collections.sort(victims, new EnemyHealthComparator(EnemyHealthComparator.MAX));
                break;
            case WEAKEST:
                Collections.sort(victims, new EnemyHealthComparator(EnemyHealthComparator.MIN));
                break;
            case NEAREST:
                Collections.sort(victims, new EnemyDistanceComparator(this));
                break;
            case RANDOM:
                byte index = victims.size() > 1 ? (byte) (Math.random() * (victims.size())) : 0;
                enemyTargets.add(victims.get(index));
                return enemyTargets;
        }
        if (this.getTowerLevel() > 1 && victims.size() > 1) {
            enemyTargets = victims.subList(0, victims.size() > this.getTowerLevel() ? this.getTowerLevel() : victims.size());
        } else {
            enemyTargets.add(victims.get(0));
        }
        return enemyTargets;
    }

    private boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getX() + enemy.getWidth() / 2f, enemy.getY() + enemy.getHeight() / 2f);
        Vector2 towerPos = new Vector2(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
        float distance = enemyPos.dst(towerPos);
        return distance <= this.range * DDGame.TILE_HEIGHT;
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
        //update range radius
        Gdx.app.log("INFO", this + " is upgraded to " + tower_lvl + " level");
    }

    /**
     * TODO Set corresponding type.
     *
     * @param type desired animation type
     */
    public void changeAnimation(Types type) {
        Gdx.app.log("INFO", "Tower animation changed to: " + tower_lvl + "_" + type.name());
        type = Types.IDLE;//remove
        this.setLevelAnimationContainer(tower_lvl);
        this.setAnimation(container.getAnimation(type));
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public TowerAnimationController getController() {
        return controller;
    }

    public void setController(TowerAnimationController controller) {
        this.controller = controller;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
    }

    public void setLevelAnimationContainer(int level) {
        container = controller.getLevelAnimationContainer(level);
    }

    public enum Strategy {

        NEAREST, RANDOM, WEAKEST, STRONGEST
    }

    private class EnemyHealthComparator implements Comparator<Enemy> {

        public static final int MAX = 1;
        public static final int MIN = -1;
        private final int type;

        public EnemyHealthComparator(int type) {
            this.type = type;
        }

        @Override
        public int compare(Enemy enemy1, Enemy enemy2) {
            return (int) ((enemy1.health - enemy2.health) * type);
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
