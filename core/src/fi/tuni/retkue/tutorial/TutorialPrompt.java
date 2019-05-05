package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.RetkueLabel;


public class TutorialPrompt extends RetkueDialog {
    private static String title = "";

    public TutorialPrompt(TutorialController tutorialController) {
        super(title);
        createMenu(tutorialController);
    }

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