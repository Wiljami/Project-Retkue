package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.RetkueDialog;
import fi.tuni.tiko.Utils;

public class TutorialInn extends RetkueDialog {
    private TutorialTownScene origin;

    public TutorialInn(TutorialTownScene origin) {
        super("Inn", skin, "dialog");

        this.origin = origin;

        Label text = new Label("Hello, dears.", skin);
        Image emilia = new Image(Utils.loadTexture("emilia.png"));

        Button rest = new TextButton("Rest", skin);

        rest.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedRest();
            }
        });

        getContentTable().add(text);
        getContentTable().add(emilia).prefHeight(200f).prefWidth(136f);
        getContentTable().row();
        getContentTable().add(rest).colspan(2);
    }

    private void clickedRest() {
        remove();
        origin.continueTutorial(105);
    }
}