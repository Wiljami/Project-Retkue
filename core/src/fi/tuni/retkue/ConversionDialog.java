package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ConversionDialog extends RetkueDialog {

    private Party party;
    private RetkueLabel text;
    private GameHeader origin;

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

    private void convert() {
        if (party.canAffordToConvert()) {
            party.convert();
            origin.updateValues();
            closeMe();
        } else {
            text.setText(readLine("conversionTooExpensive"));
        }
    }

    private void closeMe() {
        remove();
    }
}