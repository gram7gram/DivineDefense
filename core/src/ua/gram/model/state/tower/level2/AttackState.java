package ua.gram.model.state.tower.level2;

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
        if (tower.attackCount >= tower.getPrototype().rate) {
            List<EnemyGroup> victims = tower.getVictims();
            Weapon weapon = tower.getWeapon();
            if (victims != null && !victims.isEmpty()) {
                for (EnemyGroup victimGroup : victims) {
                    Enemy victim = victimGroup.getRootActor();
                    if (tower.isInRange(victim)) {
                        preAttack(tower, victim);
                        attack(tower, victim);
                    } else {
                        postAttack(tower, victim);
                        weapon.reset();
                        TowerStateManager manager = tower.getTowerShop().getStateManager();
                        manager.swap(tower,
                                tower.getStateHolder().getCurrentLevel2State(),
                                manager.getSearchState(), 2);
                    }
                }
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


    /**
     * Perform TowerState specific preparations before attack.
     *
     * @param victim the enemy to attack
     */
    private void preAttack(Tower tower, Enemy victim) {
        Weapon weapon = tower.getWeapon();
        weapon.setTarget(victim.getParent());
        weapon.setVisible(true);
    }

    /**
     * Perform towerGroup-specific attack.
     *
     * @param victim the enemy to attack
     */
    private void attack(Tower tower, Enemy victim) {
        victim.isAttacked = true;
        victim.damage(tower.getPrototype().damage);
    }

    /**
     * Perform TowerState specific actions after attack.
     *
     * @param victim the enemy attacked
     */
    private void postAttack(Tower tower, Enemy victim) {
        victim.isAttacked = false;
    }
}
