package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HealDialog extends RetkueDialog {

    private Party party;
    private RetkueLabel text;

    public HealDialog(Party party) {
        super("");
        this.party = party;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        getTitleLabel().setText(readLine("healTitle"));
        text = new RetkueLabel(readLine("healDesc"));
        String textString = party.getHealCost() + " " + readLine("steps");
        RetkueLabel steps = new RetkueLabel(textString);

        TextButton heal = new TextButton(readLine("heal"), skin);
        heal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                heal();
            }
        });

        TextButton dontHeal = new TextButton(readLine("dontHeal"), skin);
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

    private void heal() {
        if (party.canAffordToHeal()) {
            party.healParty();
            closeMe();
        } else {
            text.setText(readLine("healTooExpensive"));
        }
    }

    private void closeMe() {
        remove();
    }
}