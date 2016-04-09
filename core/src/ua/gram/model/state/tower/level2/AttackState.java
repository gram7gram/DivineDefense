package ua.gram.model.state.tower.level2;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.List;

import ua.gram.DDGame;
import ua.gram.model.ActiveTarget;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.enums.Types;
import ua.gram.model.state.tower.TowerStateManager;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AttackState extends Level2State {

    public AttackState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Tower actor) {
        super.preManage(actor);
        actor.getParent().init();
        Log.info(actor + " is attacking...");
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.ATTACK;
    }

    @Override
    public void manage(final Tower tower, float delta) {
        super.manage(tower, delta);
        if (tower.attackCount >= tower.getProperty().getRate()) {
            List<ActiveTarget> targets = tower.getTargets();
            if (!targets.isEmpty()) {
                for (ActiveTarget target : targets) {

                    final Weapon weapon = target.getWeapon();
                    final Enemy victim = target.getTarget();

                    if (tower.isInRange(victim)) {
                        weapon.addAction(
                                Actions.sequence(
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                weapon.preAttack(tower, victim);
                                            }
                                        }),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                weapon.attack(tower, victim);
                                            }
                                        }),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                weapon.postAttack(tower, victim);
                                            }
                                        })
                                )
                        );
                    } else {
                        if (weapon.hasTarget()) {
                            weapon.addAction(
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            weapon.postAttack(tower, victim);
                                        }
                                    })
                            );
                        }
                    }
                }

                TowerStateManager manager = tower.getTowerShop().getStateManager();
                manager.swap(tower, manager.getSearchState());
            } else {
                tower.resetVictims();
            }
        } else {
            tower.attackCount += delta;
        }
    }

    @Override
    public void postManage(Tower tower) {
        super.postManage(tower);
        tower.resetVictims();
        tower.attackCount = 0;
    }

}
