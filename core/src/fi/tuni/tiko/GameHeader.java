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

    public GameHeader (float height, final Party party) {
        this.party = party;

        String steps = Integer.toString(party.getSteps());
        String gold = Integer.toString(party.getGold());

        Skin skin = Scene.getSkin();
        Image stepImage = new Image(Utils.loadTexture("steps"));
        stepCount = new Label(steps, skin);
        ImageButton convert = new ImageButton(Utils.loadButtonImage("convert", 50, 50));
        convert.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(party.convert()) {
                    updateValues();
                }
            }
        });

        Image goldImage = new Image(Utils.loadTexture("gold"));
        goldCount = new Label(gold, skin);

        add(stepImage).prefHeight(height).pad(10);
        add(stepCount).prefHeight(height).pad(10);
        add(convert).prefHeight(height).pad(10);
        add(goldImage).prefHeight(height).pad(10);
        add(goldCount).prefHeight(height).pad(10);
        background(Utils.loadButtonImage("retkue_header.png", 0, 0));
    }

    public void updateValues() {
        String steps = Integer.toString(party.getSteps());
        String gold = Integer.toString(party.getGold());
        goldCount.setText(gold);
        stepCount.setText(steps);
    }
}