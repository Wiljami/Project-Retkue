package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.RetkueDialog;

public class TutorialResults extends RetkueDialog {
    public TutorialResults(final TutorialForestScene origin) {
        super("Results", skin, "dialog");

        Label text = new Label("Your task is a success!\n\nReward: 1000 gold pieces", skin);

        TextButton ok = new TextButton("Return to town", skin);
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

    private void closeMe() {
        remove();
    }
}
