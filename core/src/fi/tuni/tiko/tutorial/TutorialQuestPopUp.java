package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.RetkueDialog;
import fi.tuni.tiko.Utils;

public class TutorialQuestPopUp extends RetkueDialog {

    private TutorialTavern origin;

    RetkueLabel time;
    RetkueLabel reward;
    TextButton accept;

    public TutorialQuestPopUp(final TutorialTavern origin) {
        super("", skin, "dialog");

        this.origin = origin;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        Image image = new Image(Utils.loadTexture("old_guy1.png"));

        String text = "rololo";

        getTitleLabel().setText("Tutorial quest");

        TextButton yes = new TextButton(readLine("yes"), getSkin());
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        accept = new TextButton("Accept", skin);
        accept.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
                origin.getScene().continueTutorial(12);
            }
        });

        accept.setVisible(false);

        getContentTable().add(image).prefWidth(image.getPrefWidth() / 5).prefHeight(image.getPrefHeight() / 5).colspan(3);
        getContentTable().row();

        RetkueLabel desc = new RetkueLabel(text);

        time = new RetkueLabel("10:00");

        reward = new RetkueLabel("1000 gold");

        getContentTable().add(time).colspan(3);

        getContentTable().row();

        getContentTable().add(reward).colspan(3);
        reward.setVisible(false);
        //getContentTable().add(desc).prefWidth(popUpWidth).colspan(3);

        getContentTable().row();

        getContentTable().add(accept).pad(10).colspan(3);
    }

    public void tutorial(int id) {
        switch (id) {
            case 8: reward.setVisible(true); origin.getScene().continueTutorial(id); break;
            case 10: accept.setVisible(true); origin.getScene().continueTutorial(id); break;
        }
    }

    public void closeMe() {
        origin.remove();
        remove();
    }
}
