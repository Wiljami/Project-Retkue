package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * FasterDialog class is the dialog that holds the functionality and UI elements of the faster popUp
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class FasterDialog extends RetkueDialog {
    /**
     * Reference the party
     */
    private Party party;

    /**
     * RetkueLabel text
     */
    private RetkueLabel text;

    /**
     * Constructor for FasterDialog
     * @param party player's party
     */
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

    /**
     * faster speeds the party if the player can afford it, otherwise it changes the text
     */
    private void faster() {
        if (party.canAffordToFaster()) {
            party.fasterQuest();
            closeMe();
        } else {
            text.setText(readLine("fasterTooExpensive"));
        }
    }

    /**
     * closeMe tells HealDialog to close itself
     */
    private void closeMe() {
        remove();
    }
}