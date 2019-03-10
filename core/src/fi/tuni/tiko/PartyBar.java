package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PartyBar extends Table {
    private float height;

    public PartyBar() {
        height = Main.WORLDPIXELHEIGHT * 362f / 1920f;
        Skin skin = Scene.getSkin();
        Label steps = new Label("Steps", skin);
        Label sCount = new Label("Scount", skin);
        Label converter = new Label("Convert", skin);
        Label gold = new Label("Gold", skin);
        Label gCount = new Label("Money", skin);

        add(steps).prefHeight(height).pad(10);
        add(sCount).prefHeight(height).pad(10);
        add(converter).prefHeight(height).pad(10);
        add(gold).prefHeight(height).pad(10);
        add(gCount).prefHeight(height).pad(10);
        background(Utils.loadButtonImage("adventure button", 0, 0));
    }

    @Override
    public float getHeight() {
        return height;
    }
}