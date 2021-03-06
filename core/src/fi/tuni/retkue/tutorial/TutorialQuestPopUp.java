package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.RetkueLabel;
import fi.tuni.retkue.Utils;

/**
 * TutorialQuestPopUp class has the functionality and ui elements of the questPop for tutorial
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class TutorialQuestPopUp extends RetkueDialog {
    /**
     * origin is the popUp that called this
     */
    private TutorialTavern origin;

    /**
     * Label time
     */
    RetkueLabel time;

    /**
     * Label reward
     */
    RetkueLabel reward;

    /**
     * TextButton accept
     */
    TextButton accept;

    /**
     * Constructor for TutorialQuestPopUp
     * @param origin the class that called this popUp
     */
    public TutorialQuestPopUp(final TutorialTavern origin) {
        super("");

        this.origin = origin;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        Image image = new Image(Utils.loadTexture("old_guy1.png"));

        String text = "";

        getTitleLabel().setText("Tutorial quest");

        TextButton yes = new TextButton(readLine("yes"), getSkin());
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        accept = new TextButton(readLine("accept"), skin);
        accept.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
                origin.getScene().continueTutorial(12);
            }
        });

        getContentTable().add(image).prefWidth(image.getPrefWidth() / 5).prefHeight(image.getPrefHeight() / 5).colspan(3);
        getContentTable().row();

        RetkueLabel desc = new RetkueLabel(text);

        time = new RetkueLabel("10:00");

        reward = new RetkueLabel("1000 gold");

        getContentTable().add(time).colspan(3);

        getContentTable().row();

        getContentTable().add(reward).colspan(3);
        //getContentTable().add(desc).prefWidth(popUpWidth).colspan(3);

        getContentTable().row();

        getContentTable().add(accept).pad(10).colspan(3);
    }

    /**
     * closeMe closes this
     */
    public void closeMe() {
        origin.remove();
        remove();
    }
}
