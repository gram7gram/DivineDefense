package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.ui.LabelPrototype;
import ua.gram.model.prototype.ui.ParticipantPrototype;
import ua.gram.model.prototype.ui.window.CreditsWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CreditsWindow extends WindowGroup {

    public CreditsWindow(final DDGame game, Skin skin, WindowPrototype proto) {
        super(skin, proto.style);

        if (!(proto instanceof CreditsWindowPrototype))
            throw new IllegalArgumentException("Prototype s not instance of CreditsWindowPrototype");

        CreditsWindowPrototype prototype = (CreditsWindowPrototype) proto;

        LabelPrototype descriptionPrototype = prototype.content.description;
        ParticipantPrototype[] prototypes = prototype.content.participants;

        CustomLabel description = new CustomLabel(descriptionPrototype.text, skin, descriptionPrototype.style);
        description.setAlignment(Align.left);
        description.setWrap(true);

        Table credits = new Table();

        List<String> roles = getUniqueRoles(prototypes);

        int count = 0;
        for (String role : roles) {
            List<String> participants = getParticipantsByRole(role, prototypes);
            CustomLabel currentCredit = new CustomLabel(role, skin, "smallpopupwhite");
            currentCredit.setAlignment(++count % 2 == 0 ? Align.left : Align.right);
            for (String participant : participants) {
                currentCredit.append("\n" + participant);
            }
            credits.add(currentCredit)
                    .width(DDGame.WORLD_WIDTH / 2.5f)
                    .expandX()
                    .pad(10)
                    .row();

            currentCredit.addAction(Actions.sequence(
                    Actions.alpha(0),
                    Actions.moveBy(-DDGame.WORLD_WIDTH / 2.5f, 0),
                    Actions.delay(.2f * count),
                    Actions.parallel(
                            Actions.alpha(1, .4f),
                            Actions.moveBy(DDGame.WORLD_WIDTH / 2.5f, 0, .4f)
                    )
            ));
        }

        Table nested = new Table();
        nested.add(description).width(DDGame.WORLD_WIDTH / 2.5f).expandY();

        ScrollPane descriptionScroll = new ScrollPane(nested);
        descriptionScroll.setScrollingDisabled(true, false);
        descriptionScroll.setVisible(true);

        ScrollPane creditsScroll = new ScrollPane(credits);
        creditsScroll.setScrollingDisabled(true, false);
        creditsScroll.setVisible(true);

        Button back = new Button(skin, "right-small");
        back.setVisible(true);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        setActionRight(back);
        setTitle(new Label(prototype.header.text, skin, prototype.header.style));
        getContent().add(creditsScroll).fill().expand();
        getContent().add(descriptionScroll).expand();
    }

    private List<String> getUniqueRoles(ParticipantPrototype[] prototypes) {
        List<String> roles = new ArrayList<>(prototypes.length * 2);

        for (ParticipantPrototype prototype : prototypes) {
            for (String role : prototype.roles) {
                if (!roles.contains(role)) {
                    roles.add(role);
                }
            }
        }

        return roles;
    }

    private List<String> getParticipantsByRole(String role, ParticipantPrototype[] prototypes) {
        List<String> participants = new ArrayList<>(prototypes.length);

        for (ParticipantPrototype prototype : prototypes) {
            for (String protoRole : prototype.roles) {
                if (!participants.contains(prototype.name) && role.equals(protoRole)) {
                    participants.add(prototype.name);
                }
            }
        }

        return participants;
    }
}
