package ua.gram.model.state;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface State {

    void preManage();

    void manage();

    void postManage();
}
