package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ConversionDialog class is the dialog that holds the functionality and UI elements of the
 * conversion popUp
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class ConversionDialog extends RetkueDialog {
    /**
     * Reference to the player party
     */
    private Party party;

    /**
     * RetkueLabel text
     */
    private RetkueLabel text;

    /**
     * Reference to the GameHeader
     */
    private GameHeader origin;

    /**
     * Constructor for ConversionDialog
     * @param party player's party
     * @param origin GameHeader that called this
     */
    public ConversionDialog(Party party, GameHeader origin) {
        super("");
        this.party = party;
        this.origin = origin;
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        getTitleLabel().setText(readLine("conversionTitle"));
        text = new RetkueLabel(readLine("conversionDesc"));
        String textString = party.getConvCost() + " " + readLine("steps");
        textString +=  " : " + party.getConvGold() + " " + readLine("golds");
        RetkueLabel steps = new RetkueLabel(textString);

        TextButton convert = new TextButton(readLine("convert"), skin);
        convert.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                convert();
            }
        });

        TextButton dontConvert = new TextButton(readLine("dontConvert"), skin);
        dontConvert.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().add(text).prefWidth(popUpWidth).pad(5).colspan(2);
        getContentTable().row();
        getContentTable().add(steps).colspan(2);
        getContentTable().row();
        getContentTable().add(dontConvert).pad(10);
        getContentTable().add(convert).pad(10);
    }

    /**
     * convert converts steps to gold if player can afford it, if unable to afford it, changes text
     */
    private void convert() {
        if (party.canAffordToConvert()) {
            party.convert();
            origin.updateValues();
            closeMe();
        } else {
            text.setText(readLine("conversionTooExpensive"));
        }
    }

    /**
     * closeMe closes this popUp
     */
    private void closeMe() {
        remove();
    }
}