package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EncounterPopUp extends RetkueDialog {

    ForestScene origin;

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

    private void begin() {
        origin.beginEncounter();
        remove();
    }
}
