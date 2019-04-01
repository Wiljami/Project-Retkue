package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.RetkueDialog;


public class TutorialPrompt extends RetkueDialog {
    private static String windowStyle = "dialog";
    private static String title = "";

    public TutorialPrompt(TutorialController tutorialController) {
        super(title, skin, windowStyle);
        createMenu(tutorialController);
    }

    private void createMenu(final TutorialController tutorialController) {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        this.getTitleLabel().setText(readLine("tutorial_prompt"));
        String text = readLine("tutorial_desc");
        RetkueLabel desc = new RetkueLabel(text);

        getContentTable().add(desc).prefWidth(popUpWidth);
        getContentTable().row();
        getContentTable().row();
        getContentTable().row();

        TextButton ok = new TextButton(readLine("ok"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                tutorialController.tutorialPhase(3);
            }
        });

        getContentTable().add(ok);
    }
}