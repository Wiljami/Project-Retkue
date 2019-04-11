package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FasterDialog extends RetkueDialog {

    private Party party;
    private RetkueLabel text;

    public FasterDialog(Party party) {
        super("");
        this.party = party;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        getTitleLabel().setText(readLine("fasterTitle"));
        text = new RetkueLabel(readLine("fasterDesc"));
        String textString = party.getFasterCost() + " " + readLine("steps");
        RetkueLabel steps = new RetkueLabel(textString);

        TextButton heal = new TextButton(readLine("faster"), skin);
        heal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                faster();
            }
        });

        TextButton dontHeal = new TextButton(readLine("dontFaster"), skin);
        dontHeal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().add(text).prefWidth(popUpWidth).pad(5).colspan(2);
        getContentTable().row();
        getContentTable().add(steps).colspan(2);
        getContentTable().row();
        getContentTable().add(dontHeal).pad(10);
        getContentTable().add(heal).pad(10);
    }

    private void faster() {
        if (party.canAffordToFaster()) {
            party.fasterQuest();
            closeMe();
        } else {
            text.setText(readLine("fasterTooExpensive"));
        }
    }

    private void closeMe() {
        remove();
    }
}