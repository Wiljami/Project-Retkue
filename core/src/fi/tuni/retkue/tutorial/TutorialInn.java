package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.Utils;

/**
 * TutorialInn holds the functionality and UI elements for the inn parts of the tutorial
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class TutorialInn extends RetkueDialog {
    /**
     * the scene that called the Inn
     */
    private TutorialTownScene origin;

    /**
     * Constructor of the TutorialInn
     * @param origin origin of the popUp
     */
    public TutorialInn(TutorialTownScene origin) {
        super("Inn");

        this.origin = origin;

        Label text = new Label("Hello, dears.", skin);
        Image emilia = new Image(Utils.loadTexture("emilia.png"));

        Button rest = new TextButton(readLine("rest"), skin);

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

    /**
     * method called when clicked
     */
    private void clickedRest() {
        remove();
        origin.continueTutorial(105);
    }
}