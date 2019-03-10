package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameHeader extends Table {
    public GameHeader () {
        int topBarHeight = (int)(Main.WORLDPIXELHEIGHT / 6.6f);
        Skin skin = Scene.getSkin();
        Label steps = new Label("Steps", skin);
        Label sCount = new Label("Scount", skin);
        Label converter = new Label("Convert", skin);
        Label gold = new Label("Gold", skin);
        Label gCount = new Label("Money", skin);

        add(steps).prefHeight(topBarHeight).pad(10);
        add(sCount).prefHeight(topBarHeight).pad(10);
        add(converter).prefHeight(topBarHeight).pad(10);
        add(gold).prefHeight(topBarHeight).pad(10);
        add(gCount).prefHeight(topBarHeight).pad(10);
        background(Utils.loadButtonImage("adventure button", 0, 0));
    }
}