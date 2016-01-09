package ua.gram.model.state.enemy;

import ua.gram.model.state.enemy.level1.Level1State;
import ua.gram.model.state.enemy.level2.Level2State;
import ua.gram.model.state.enemy.level3.Level3State;
import ua.gram.model.state.enemy.level4.Level4State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyStateHolder {

    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Level3State currentLevel3State;
    private Level4State currentLevel4State;

    public Level1State getCurrentLevel1State() {
        return currentLevel1State;
    }

    public void setCurrentLevel1State(Level1State currentLevel1State) {
        this.currentLevel1State = currentLevel1State;
    }

    public Level2State getCurrentLevel2State() {
        return currentLevel2State;
    }

    public void setCurrentLevel2State(Level2State currentLevel2State) {
        this.currentLevel2State = currentLevel2State;
    }

    public Level3State getCurrentLevel3State() {
        return currentLevel3State;
    }

    public void setCurrentLevel3State(Level3State currentLevel3State) {
        this.currentLevel3State = currentLevel3State;
    }

    public Level4State getCurrentLevel4State() {
        return currentLevel4State;
    }

    public void setCurrentLevel4State(Level4State currentLevel4State) {
        this.currentLevel4State = currentLevel4State;
    }
}
