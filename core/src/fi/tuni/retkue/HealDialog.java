package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * HealDialog class is the dialog that holds the functionality and UI elements of the heal popUp
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class HealDialog extends RetkueDialog {

    /**
     * Reference to the party
     */
    private Party party;

    /**
     * RetkueLabel text in the popup
     */
    private RetkueLabel text;

    /**
     * HealDialog Constructor
     * @param party player's party
     */
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

    /**
     * heal heals the party if the player can afford it, otherwise it changes the text
     */
    private void heal() {
        if (party.canAffordToHeal()) {
            party.healParty();
            closeMe();
        } else {
            text.setText(readLine("healTooExpensive"));
        }
    }

    /**
     * closeMe tells HealDialog to close itself
     */
    private void closeMe() {
        remove();
    }
}