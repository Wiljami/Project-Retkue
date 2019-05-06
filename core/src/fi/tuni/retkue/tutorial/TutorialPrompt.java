package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.RetkueLabel;

/**
 * TutorialPrompt class contains functionality and UI elements of the popUp asking player
 * wether they wish the enter the tutorial or not.
 * @author Viljami Pietarila
 * @version 2019.0505
 */

public class TutorialPrompt extends RetkueDialog {
    private static String title = "";

    /**
     * Constructor for TutorialPrompt
     * @param tutorialController reference to the tutorialController
     */
    public TutorialPrompt(TutorialController tutorialController) {
        super(title);
        createMenu(tutorialController);
    }

    /**
     * method createMenu creates the UI elements
     * @param tutorialController reference to the tutorialController
     */
    private void createMenu(final TutorialController tutorialController) {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        this.getTitleLabel().setText(readLine("tutorial_prompt"));
        String text = readLine("tutorial_desc");
        RetkueLabel desc = new RetkueLabel(text);

        getContentTable().add(desc).prefWidth(popUpWidth).colspan(2);
        getContentTable().row();
        getContentTable().row();
        getContentTable().row();

        TextButton ok = new TextButton(readLine("ok"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                tutorialController.tutorialPhase(1);
            }
        });

        TextButton skipTutorial = new TextButton(readLine("tutorial_skip"), getSkin());
        skipTutorial.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });

        getContentTable().add(skipTutorial);
        getContentTable().add(ok);
    }
}