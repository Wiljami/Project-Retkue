package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * PartyBar holds UI and functionality of the party display in forest scene.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class PartyBar extends Table {
    public PartyBar(float height) {
        Skin skin = Scene.getSkin();

        Image retku1 = new Image(Utils.loadTexture("old_guy1.png"));
        Image retku2 = new Image(Utils.loadTexture("old_guy1.png"));
        Image retku3 = new Image(Utils.loadTexture("old_guy1.png"));

        float healthBarWidth = Main.WORLDPIXELWIDTH/3f - 10f;
        float healthBarHeight = height / 6f;

        float charHeight = height - healthBarHeight;

        HealthBar bar1 = new HealthBar(healthBarWidth, healthBarHeight);
        HealthBar bar2 = new HealthBar(healthBarWidth, healthBarHeight);
        HealthBar bar3 = new HealthBar(healthBarWidth, healthBarHeight);

        bar1.setValue(0.5f);
        bar2.setValue(0.9f);
        bar3.setValue(0.1f);

        add(bar1).width(healthBarWidth);
        add(bar2).width(healthBarWidth);
        add(bar3).width(healthBarWidth);

        row();

        add(retku1).prefHeight(charHeight);
        add(retku2).prefHeight(charHeight);
        add(retku3).prefHeight(charHeight);

        background(Utils.loadButtonImage("retkue_title.png", 0, 0));
    }
}
