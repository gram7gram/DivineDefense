package ua.gram.model.prototype.ui.window;

import java.util.ArrayList;
import java.util.List;

import ua.gram.model.prototype.ui.ButtonPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuWindowPrototype extends WindowPrototype {
    public String headerText;
    public String headerTextStyle;
    public ButtonPrototype[] actions;

    public List<ButtonPrototype> orderActions() {
        List<ButtonPrototype> list = new ArrayList<>(actions.length);
        for (ButtonPrototype action : actions) {
            list.add(action.order, action);
        }
        return list;
    }
}
