package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PartyBar extends Table {
    private float height;

    public PartyBar() {
        height = Main.WORLDPIXELHEIGHT * 362f / 1920f;
        float barHeight = Main.WORLDPIXELHEIGHT * 70f /1920f;
        float charHeight = height - barHeight;
        Skin skin = Scene.getSkin();

        Image retku1 = new Image(Utils.loadTexture("old_guy1.png"));
        Image retku2 = new Image(Utils.loadTexture("old_guy1.png"));
        Image retku3 = new Image(Utils.loadTexture("old_guy1.png"));

        Image bar1 = new Image(Utils.loadTexture("village.png"));
        Image bar2 = new Image(Utils.loadTexture("village.png"));
        Image bar3 = new Image(Utils.loadTexture("village.png"));

        add(bar1).prefHeight(barHeight);
        add(bar2).prefHeight(barHeight);
        add(bar3).prefHeight(barHeight);
        row();
        add(retku1).prefHeight(charHeight);
        add(retku2).prefHeight(charHeight);
        add(retku3).prefHeight(charHeight);

        background(Utils.loadButtonImage("retkue_title.png", 0, 0));
    }

    @Override
    public float getHeight() {
        return height;
    }


}
