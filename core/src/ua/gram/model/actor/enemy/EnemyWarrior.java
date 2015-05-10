package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyWarrior extends Enemy implements Cloneable, Pool.Poolable {

    public EnemyWarrior(DDGame game, float[] stats) {
        super(game, stats);
        this.setSize(animationWidth, animationHeight);
        this.setBounds(getX(), getY(), animationWidth, animationHeight);
    }

    @Override
    public void reset() {
        this.health = defaultHealth;
        this.speed = defaultSpeed;
        this.armor = defaultArmor;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

    @Override
    public EnemyWarrior clone() throws CloneNotSupportedException {
        return (EnemyWarrior) super.clone();
    }

//	/**
//	 * <pre>
//	 * Marks the Enemy with Dead flag inorder to prevent following shooting at him. 
//	 * Also, sets the animation to Death and waits for it's end.
//	 * 
//	 * TODO Display death animation. 
//	 * </pre>
//	 */
//	@Override
//	public void die() {
//		isDead = true;
//		this.getController().free(this.getAnimation());
//		this.remove();
//		Gdx.app.log("INFO", this.getClass().getSimpleName() + " is dead");
//	}
}
