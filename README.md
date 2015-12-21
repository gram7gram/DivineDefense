![GAME ICON](https://github.com/gram7gram/DivineDefense/blob/mainstream/logo_small.png?raw=true)

## Divine Defense

> The Tower Defense game

> By Gram: <gram7gram@gmail.com>

**Game idea**: There are two eternal fractions, battling over and over.
Choose your fraction and join the divine battle for world domination!

You may choose between Demon and Angel fraction. Each has unique weaponry and stats.
Defend from invasion of the opposite fraction, gain game progress, gold and upgrades!

> **Contributors**

Art: Евгений Ф. <yeugenyf@gmail.com>

### SHOWCASE

> prototype

![prototype screenshot](http://i.imgur.com/lghZ9DT.png)

> v0.01

![v0.01 screenshot](http://imgur.com/ZcO1J2gl.png)

![v0.01 screenshot](http://imgur.com/ufJsggsl.png)

![v0.01 screenshot](http://imgur.com/fjNNCPvl.png)

### Concurrent state-machine (CSM)

State is a representation of the object in the specific moment of time. 
Finit, concurrent, stack state-machines are up to you to decide which is the best way.

I have choosen the concurrent one.

```java
package ua.gram.model.state;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface StateInterface<T extends Actor> {

    /**
     * Execute before actual management:
     * load nessessary recources, objects etc.
     */
    void preManage(T actor);

    /**
     * Implement state logic
     */
    void manage(T actor, float delta);

    /**
     * Clean-un and reset state
     */
    void postManage(T actor);
}
```

Basicaly, I have an Actor, which can have several states simultaneously.
As a came accross gdx-AI ([gdx-ai]) I was pleased over the fact that we though in the same direction on implementing the state-machine.

Yet, whether my implementation is good is, again, UP TO YOU TO DECIDE.

Each state has:
 
 * smth that should be done before it becames the object's state (*preManage*, example: updating animation)
 
 * smth that shoud be done continiously(*manage*, example: move an Actor)
 
 * smth to do if state is disabled (*postManage*, example: cleanup, unset variables).

So, to manage StateInterface, StateManager is essential:

```java
package ua.gram.model.state;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class StateManager<A extends Actor> {

    protected A actor;

    public abstract void init(A actor);

    public abstract void update(A actor, float delta);

    /**
     * Swap states. Executes StateInterface::postManage on {@param before}
     * and StateInterface::preManage on {@param after}.
     *
     * @param actor the actor which will be managed
     * @param before current state; nullable
     * @param after new state; nullable
     * @param level integer represetation of the state level, aka 1-4
     */
    public abstract void swap(A actor, StateInterface before, StateInterface after, int level);

    /**
     * Save actor-specific state
     * @param actor
     * @param newState
     */
    public abstract void persist(A actor, StateInterface newState, int level) throws NullPointerException;

    /**
     * Reset all states for the Actor
     *
     * @param actor
     */
    public abstract void reset(A actor);

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }

}
```

By division states into class hierarchy it is possible to reduce code-redundancy and improve scalability of the feature. 

Here are 4 levels of states:

1. **Activity level**: active/inactive, dead, finish, spawn

2. **Behaviour level**: idle, walk

3. **Ability level**: ability

4. **Affected level**: stun

And an example of the State:

```java
package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.group.EnemyGroup;
import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeadState extends InactiveState {

    public DeadState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) throws GdxRuntimeException {
        super.preManage(enemy);
        
        enemy.getAnimationProvider().get(enemy).free(enemy);

        EnemySpawner spawner = enemy.getSpawner();
        EnemyGroup group = enemy.getEnemyGroup();

        enemy.clearActions();
        
        spawner.free(enemy);
        
        group.clear();
        group.remove();
        
        Gdx.app.log("INFO", enemy + " is dead");
    }

    @Override
    public void postManage(Enemy enemy) {
        game.getPlayer().addCoins(enemy.reward);
        float value = new Random().nextFloat();

        //10% chance to get a gem
        if (value >= .45 && value < .55) {
            getGame().getPlayer().addGems(1);
            Gdx.app.log("INFO", "Player got 1 gem");
        }
    }
}
```

States diagramm:

![states image](http://github.com/gram7gram/DivineDefese/blob/DD-9/misc/states.png?raw=true)

References:

* State patterns: [online book]

* Gdx-AI library: [gdx-ai]

[gdx-ai]: https://github.com/libgdx/gdx-ai
[online book]: http://gameprogrammingpatterns.com/state.html
