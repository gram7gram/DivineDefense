package ua.gram.model.state.tower.level2;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.state.tower.TowerStateManager;

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
        Log.info(actor + " is attacking...");
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.ATTACK;
    }

    @Override
    public void manage(Tower tower, float delta) {
        super.manage(tower, delta);
        Weapon weapon = tower.getWeapon();
        if (tower.attackCount >= tower.getProperty().getRate() && weapon.isFinished()) {
            List<EnemyGroup> victims = tower.getVictims();
            if (victims != null && !victims.isEmpty()) {
                for (EnemyGroup victimGroup : victims) {
                    Enemy victim = victimGroup.getRootActor();
                    if (tower.isInRange(victim)) {
                        weapon.addAction(
                                Actions.sequence(
                                        Actions.run(() -> weapon.preAttack(tower, victim)),
                                        Actions.run(() -> weapon.attack(tower, victim))
                                )
                        );
                    }

                    weapon.addAction(
                            Actions.run(() -> weapon.postAttack(tower, victim))
                    );
                }

                TowerStateManager manager = tower.getTowerShop().getStateManager();
                manager.swap(tower,
                        tower.getStateHolder().getCurrentLevel2State(),
                        manager.getSearchState(), 2);
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
