package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * GameHeader class has the functionality of the top header bar that appears in at least two of
 * the game scenes. It will create the table UI and have hold the functionality.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class GameHeader extends Table {
    Label stepCount;
    Label goldCount;
    Party party;

    /**
     * GameHeader constructor.
     *
     * Height tells this object how much vertical size it has available to it.
     * Party is needed as reference for the different currency values.
     * @param height height of this element in pixels
     * @param party reference to the game's party
     */
    public GameHeader (float height, final Party party) {
        if (Main.debug) debug();
        this.party = party;

        int buttonWidth = (int)(height * 5f / 8f);
        int buttonHeight = (int)height;

        float elementWidth = Main.WORLDPIXELWIDTH/5f;

        String steps = Integer.toString(party.getSteps());
        String gold = Integer.toString(party.getGold());

        Skin skin = Scene.getSkin();
        ImageButton stepImage = new ImageButton(Utils.loadButtonImage("gold-sack.png", buttonWidth, buttonHeight));
        stepCount = new Label(steps, skin);
        stepCount.setAlignment(1);
        ImageButton convert = new ImageButton(Utils.loadButtonImage("arrow.png", buttonWidth, buttonHeight));
        convert.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(party.convert()) {
                    updateValues();
                }
            }
        });

        ImageButton goldImage = new ImageButton(Utils.loadButtonImage("gold-sack.png", buttonWidth, buttonHeight));
        goldCount = new Label(gold, skin);
        goldCount.setAlignment(1);

        System.out.println(elementWidth);
        add(stepImage).width(elementWidth).padBottom(10);
        add(stepCount).width(elementWidth).center();
        add(convert).width(elementWidth);
        add(goldImage).width(elementWidth).padBottom(10);
        add(goldCount).width(elementWidth).center();
        background(Utils.loadButtonImage("retkue_header.png", 0, 0));
    }

    /**
     * updateValues() updates the values of the gold and steps on screen.
     */
    public void updateValues() {
        String steps = Integer.toString(party.getSteps());
        String gold = Integer.toString(party.getGold());
        goldCount.setText(gold);
        stepCount.setText(steps);
    }
}