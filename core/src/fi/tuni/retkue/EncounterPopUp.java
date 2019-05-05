package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Encounter PopUp is the UI and functionality of the encounter PopUp
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class EncounterPopUp extends RetkueDialog {
    /**
     * origin is reference of the Scene that called this
     */
    ForestScene origin;

    /**
     * constructor of EncounterPopUp
     * @param enemy localized name of the enemy
     * @param origin the Scene that called this popUp
     */
    public EncounterPopUp(String enemy, ForestScene origin) {
        super("");
        this.origin = origin;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        String text = readLine("encounterText");
        text += " " + enemy + "!\n";
        text += readLine("encounterPrompt");

        TextButton ok = new TextButton(readLine("ok"), skin);
        ok.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                begin();
            }
        });

        RetkueLabel textLabel = new RetkueLabel(text);
        getContentTable().add(textLabel).prefWidth(popUpWidth);
        getContentTable().row();
        getContentTable().add(ok);
    }

    /**
     * Begins the encounter and removes the popUp
     */
    private void begin() {
        origin.beginEncounter();
        remove();
    }
}
