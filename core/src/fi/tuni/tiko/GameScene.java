package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class containing the game actual.
 * TODO: Should this be split in two? One for town and one for the forest.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */

public class GameScene extends Scene {
    private Texture backGround;

    public GameScene(Main game) {
        super(game);
        CreateMenu();
        backGround = new Texture(Gdx.files.internal("village.png"));
    }

    @Override
    public void renderBackground() {
        getBatch().draw(backGround, 0, 0, getGame().WORLDPIXELWIDTH, getGame().WORLDPIXELHEIGHT);
    }

    private void CreateMenu() {
        Button mainMenu = new TextButton("Return to Main Menu", getSkin());
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.mainMenu);
            }
        });

        Button inn = new ImageButton(Utils.loadButtonImage("inn button", 50, 50));
        inn.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               getGame().openScene(Main.GameView.inn, Main.GameView.gameScreen);
            }
        });

        Button shop = new ImageButton(Utils.loadButtonImage("shop button", 50, 50));
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.shop, Main.GameView.gameScreen);
            }
        });

        Button tavern = new ImageButton(Utils.loadButtonImage("tavern button", 50, 50));
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.tavern, Main.GameView.gameScreen);
            }
        });

        Button adventure = new ImageButton(Utils.loadButtonImage("adventure button", 50, 50));
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.adventure, Main.GameView.gameScreen);
            }
        });

        Button menu = new ImageButton(Utils.loadButtonImage("menu button", 50, 50));
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.menu, Main.GameView.gameScreen);
            }
        });

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Game", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(inn);
        table.add(shop);
        table.add(tavern);
        table.row();
        table.add(adventure).colspan(3);
        table.add().prefHeight(200);
        table.row();
        table.add(menu).colspan(3);
        table.row();
        table.add(mainMenu).colspan(3);
        getStage().addActor(table);
    }

    @Override
    public void dispose() {
        backGround.dispose();
        super.dispose();
    }
}
