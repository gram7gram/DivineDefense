package ua.gram.model.prototype.ui.window;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindowPrototype extends WindowPrototype {
    public String buttonText;
    public String buttonTextStyle;
    public String titleRegion;
    public String rewardTextStyle;
    public int buttonWidth;
    public int buttonHeight;
    public int minimumReward;
    public int maximumReward;

    public int getReward() {
        return minimumReward + (int) (Math.random() * maximumReward);
    }
}
