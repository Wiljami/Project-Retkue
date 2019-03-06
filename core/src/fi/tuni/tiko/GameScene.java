package fi.tuni.tiko;

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
    public GameScene(Main game) {
        super(game);
        createMenu();
        setupBackground("village.png");
    }

    private void createMenu() {
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
               InnPopUp innPopUp = new InnPopUp();
               innPopUp.show(getStage());
           }
        });

        Button shop = new ImageButton(Utils.loadButtonImage("shop button", 50, 50));
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //getGame().openScene(Main.GameView.shop, Main.GameView.gameScreen);
                ShopPopUp shopPopUp = new ShopPopUp();
                shopPopUp.show(getStage());
            }
        });

        Button tavern = new ImageButton(Utils.loadButtonImage("tavern button", 50, 50));
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //getGame().openScene(Main.GameView.tavern, Main.GameView.gameScreen);
                TavernPopUp tavernPopUp = new TavernPopUp();
                tavernPopUp.show(getStage());
            }
        });

        Button adventure = new ImageButton(Utils.loadButtonImage("adventure button", 50, 50));
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //getGame().openScene(Main.GameView.adventure, Main.GameView.gameScreen);
                AdventurePopUp adventurePopUp = new AdventurePopUp();
                adventurePopUp.show(getStage());
            }
        });

        Button menu = new ImageButton(Utils.loadButtonImage("menu button", 50, 50));
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp();
                options.show(getStage());
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
        super.dispose();
    }
}
