package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * PartyBar holds UI and functionality of the party display in forest scene.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class PartyBar extends Table {
    public PartyBar(float height, Party party) {
        Image retku0 = new Image(Utils.loadTexture(party.findRetku(0).getImageFile()));
        Image retku1 = new Image(Utils.loadTexture(party.findRetku(1).getImageFile()));
        Image retku2 = new Image(Utils.loadTexture(party.findRetku(2).getImageFile()));

        float healthBarWidth = Main.WORLDPIXELWIDTH/3f - 10f;
        float healthBarHeight = height / 6f;

        float charHeight = height - healthBarHeight;

        HealthBar bar0 = new HealthBar(healthBarWidth, healthBarHeight);
        HealthBar bar1 = new HealthBar(healthBarWidth, healthBarHeight);
        HealthBar bar2 = new HealthBar(healthBarWidth, healthBarHeight);

        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());

        add(bar0).width(healthBarWidth);
        add(bar1).width(healthBarWidth);
        add(bar2).width(healthBarWidth);

        row();

        add(retku0).prefHeight(charHeight);
        add(retku1).prefHeight(charHeight);
        add(retku2).prefHeight(charHeight);

        background(Utils.loadButtonImage("retkue_title.png", 0, 0));
    }
}