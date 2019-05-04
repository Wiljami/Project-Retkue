package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * RestDialog class holding the functionality of the resting dialog
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */

public class RestDialog extends RetkueDialog {

    /**
     * Reference to the party
     */
    private Party party;

    /**
     * RetkueLabel text is the text displayed
     */
    private RetkueLabel text;

    /**
     * Reference to the Inn
     */
    private InnPopUp inn;

    /**
     * Constructor for RestDialog
     * @param party reference to the party
     * @param inn reference to the InnPopUp
     */
    public RestDialog(Party party, InnPopUp inn) {
        super("");
        this.party = party;
        this.inn = inn;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        getTitleLabel().setText(readLine("restTitle"));
        text = new RetkueLabel(readLine("restDesc"));

        TextButton rest = new TextButton(readLine("rest"), skin);
        rest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rest();
            }
        });

        TextButton dontRest = new TextButton(readLine("dontRest"), skin);
        dontRest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().add(text).prefWidth(popUpWidth).pad(5).colspan(2);
        getContentTable().row();
        getContentTable().add(dontRest).pad(10);
        getContentTable().add(rest).pad(10);
    }

    /**
     * rest method does the functions of the rest button
     */
    private void rest() {
        if (party.canAffordToRest()) {
            party.restParty();
            closeMe();
        } else {
            text.setText(readLine("restTooExpensive"));
        }
    }

    /**
     * CloseMe tells the RestDialog to perform updates on screen and close itself
     */
    private void closeMe() {
        inn.updateHealthBars();
        getGame().saveGame();
        remove();
    }
}