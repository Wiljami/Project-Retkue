package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * GameHeader class has the functionality of the top header bar that appears in at least two of
 * the game scenes. It will create the table UI and have hold the functionality.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class GameHeader extends Table {
    public GameHeader (float height) {
        Skin skin = Scene.getSkin();
        Image stepImage = new Image(Utils.loadTexture("steps"));
        Label sCount = new Label("999", skin);
        ImageButton convert = new ImageButton(Utils.loadButtonImage("convert", 50, 50));
        Image goldImage = new Image(Utils.loadTexture("gold"));
        Label gCount = new Label("100", skin);

        add(stepImage).prefHeight(height).pad(10);
        add(sCount).prefHeight(height).pad(10);
        add(convert).prefHeight(height).pad(10);
        add(goldImage).prefHeight(height).pad(10);
        add(gCount).prefHeight(height).pad(10);
        background(Utils.loadButtonImage("adventure button", 0, 0));
    }
}