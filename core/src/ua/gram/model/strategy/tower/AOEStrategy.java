package ua.gram.model.strategy.tower;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.prototype.weapon.AOEWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AOEStrategy implements TowerStrategy {

    @Override
    public List<Enemy> chooseVictims(Tower tower, List<Enemy> targets) {

        if (targets.size() == 0) throw new NullPointerException("Nothing to compare");

        if (targets.size() == 1) return targets;

        WeaponPrototype weapon = tower.getWeaponPool().getPrototype();

        if (!(weapon instanceof AOEWeaponPrototype))
            throw new IllegalArgumentException("Non-AOE weapon should not have access to this method");

        return getVictimsInRange(targets, (AOEWeaponPrototype) weapon);
    }

    protected List<Enemy> getVictimsInRange(List<Enemy> targets, AOEWeaponPrototype weapon) {
        Enemy mainTarget = targets.get(0);

        ArrayList<Enemy> victimInRange = new ArrayList<Enemy>(5);
        victimInRange.add(mainTarget);

        for (Enemy target : targets) {
            if (victimInRange.contains(target)) continue;
            Vector2 victimPosition = target.getCurrentPosition();
            Vector2 mainVictimPosition = mainTarget.getCurrentPosition();
            float distance = victimPosition.dst(mainVictimPosition);
            if (distance <= weapon.aoeRange) {
                victimInRange.add(target);
            }
        }

        return victimInRange;
    }
}
