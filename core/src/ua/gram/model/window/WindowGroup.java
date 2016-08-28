package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WindowGroup extends Group {

    private final Table layout;
    private Table content;
    private Table actions;
    private Button actionLeft;
    private Button actionRight;
    private Label title;
    private int contentAlign;

    public WindowGroup(Skin skin) {
        this(skin, "window-dark-gold");
    }

    public WindowGroup(Skin skin, String styleName) {
        Window.WindowStyle style = skin.get(styleName, Window.WindowStyle.class);
        if (style == null) throw new NullPointerException("Style not found for name: " + styleName);
        layout = new Table(skin);
        layout.setBackground(style.background);
        layout.setVisible(true);
        int gap = 20;
        setBounds(gap, gap,
                DDGame.WORLD_WIDTH - gap * 2,
                DDGame.WORLD_HEIGHT - gap * 2);
        content = new Table(skin);
        content.setVisible(true);
        contentAlign = Align.center;
        rebuild();
    }

    private void rebuild() {
        layout.invalidateHierarchy();
        layout.clear();
        layout.pad(15);
        layout.setFillParent(true);

        float size = Math.min(60, DDGame.DEFAULT_BUTTON_HEIGHT);

        if (actionLeft != null || title != null || actionRight != null) {
            layout.add(actionLeft).left()
                    .width(size)
                    .height(size);

            layout.add(title).center().expandX();

            layout.add(actionRight).right()
                    .width(size)
                    .height(size);

            layout.row();
        }

        layout.add(content)
                .align(contentAlign)
                .pad(10)
                .expand()
                .colspan(3).row();

        if (actions != null) {
            layout.add().height(size).expandX();
            layout.add(actions).height(size).expandX();
            layout.add().height(size).expandX();
        }

        addActor(layout);
        layout.toBack();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
    }

    public Table getLayoutTable() {
        return layout;
    }

    public Table getContent() {
        return content;
    }

    public void setContent(Table content) {
        this.content = content;
        rebuild();
    }

    public void setActionLeft(Button actionLeft) {
        this.actionLeft = actionLeft;
        rebuild();
    }

    public void setActionRight(Button actionRight) {
        this.actionRight = actionRight;
        rebuild();
    }

    public void setTitle(Label title) {
        this.title = title;
        rebuild();
    }

    public void setActions(Table actions) {
        this.actions = actions;
        rebuild();
    }
}
