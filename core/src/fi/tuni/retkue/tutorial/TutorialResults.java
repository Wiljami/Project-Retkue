package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.RetkueDialog;

/**
 * TutorialResults class has the functionality and ui elements of the results popUp for tutorial
 * results
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class TutorialResults extends RetkueDialog {
    /**
     * Consturctor for TutorialForestScene
     * @param origin the Scene that called this popUp
     */
    public TutorialResults(final TutorialForestScene origin) {
        super("Results");

        Label text = new Label("Your task is a success!\n\nReward: 1000 gold pieces", skin);

        TextButton ok = new TextButton(readLine("ready"), skin);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
                origin.continueTutorial(19);
            }
        });

        getContentTable().add(text);
        getContentTable().row();
        getContentTable().add(ok);
    }

    /**
     * closeMe makes this close itself
     */
    private void closeMe() {
        remove();
    }
}
