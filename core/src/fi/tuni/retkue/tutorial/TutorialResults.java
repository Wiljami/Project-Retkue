package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.RetkueDialog;

public class TutorialResults extends RetkueDialog {
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

    private void closeMe() {
        remove();
    }
}