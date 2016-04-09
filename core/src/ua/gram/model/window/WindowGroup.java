package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.utils.Vector4;

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
    private Vector4 contentPadding;

    public WindowGroup(Skin skin) {
        this(skin, "window-dark-gold");
    }

    public WindowGroup(Skin skin, String styleName) {
        Window.WindowStyle style = skin.get(styleName, Window.WindowStyle.class);
        if (style == null) throw new NullPointerException("Style not found for name: " + styleName);
        layout = new Table(skin);
        layout.setBackground(style.background);
        layout.setVisible(true);
        setBounds(
                DDGame.DEFAULT_BUTTON_HEIGHT / 3f,
                DDGame.DEFAULT_BUTTON_HEIGHT / 2f,
                DDGame.WORLD_WIDTH - DDGame.DEFAULT_BUTTON_HEIGHT * 2 / 3f,
                DDGame.WORLD_HEIGHT - DDGame.DEFAULT_BUTTON_HEIGHT);
        content = new Table(skin);
        content.setVisible(true);
        contentAlign = Align.center;
        contentPadding = new Vector4(10);
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
                .padLeft(contentPadding.x)
                .padTop(contentPadding.y)
                .padRight(contentPadding.z)
                .padBottom(contentPadding.t)
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

    public void setContentPadding(Vector4 contentPadding) {
        this.contentPadding = contentPadding;
        rebuild();
    }

    public void setContentAlign(int contentAlign) {
        this.contentAlign = contentAlign;
        rebuild();
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

    public Table getActionsTable() {
        return actions;
    }

    public Button getActionLeft() {
        return actionLeft;
    }

    public void setActionLeft(Button actionLeft) {
        this.actionLeft = actionLeft;
        rebuild();
    }

    public Button getActionRight() {
        return actionRight;
    }

    public void setActionRight(Button actionRight) {
        this.actionRight = actionRight;
        rebuild();
    }

    public Label getTitle() {
        return title;
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
