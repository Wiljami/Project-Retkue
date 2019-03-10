package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class containing the game actual.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */

public class TownScene extends Scene {
    public TownScene(Main game) {
        super(game);
        createMenu();
        setupBackground("village.png");
    }

    private void createMenu() {
        Button mainMenu = new TextButton( readLine("backToMainMenu"), getSkin());
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.mainMenu);
            }
        });

        Button inn = new TextButton( readLine("inn"), getSkin());
        inn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                InnPopUp innPopUp = new InnPopUp();
                innPopUp.show(getStage());
            }
        });

        Button shop = new TextButton( readLine("shop"), getSkin());
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopPopUp shopPopUp = new ShopPopUp();
                shopPopUp.show(getStage());
            }
        });

        Button tavern = new TextButton( readLine("tavern"), getSkin());
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TavernPopUp tavernPopUp = new TavernPopUp();
                tavernPopUp.show(getStage());
            }
        });

        Button adventure = new TextButton( readLine("adventure"), getSkin());
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AdventurePopUp adventurePopUp = new AdventurePopUp();
                adventurePopUp.show(getStage());
            }
        });

        Button menu = new TextButton( readLine("options"), getSkin());
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp();
                options.show(getStage());
            }
        });

/*      Button inn = new ImageButton(Utils.loadButtonImage("inn button", 50, 50));
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
                ShopPopUp shopPopUp = new ShopPopUp();
                shopPopUp.show(getStage());
            }
        });

        Button tavern = new ImageButton(Utils.loadButtonImage("tavern button", 50, 50));
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TavernPopUp tavernPopUp = new TavernPopUp();
                tavernPopUp.show(getStage());
            }
        });

        Button adventure = new ImageButton(Utils.loadButtonImage("adventure button", 50, 50));
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AdventurePopUp adventurePopUp = new AdventurePopUp();
                adventurePopUp.show(getStage());
            }
        });*/

/*        Button menu = new ImageButton(Utils.loadButtonImage("menu button", 50, 50));
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp();
                options.show(getStage());
            }
        });*/

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/6.4f, 1/4f, 1/16f, 1/8f, 1/16f, 1/8f, 1/16f, 1/24f, 1/16f, 1/19.2f};
        for (int n = 0; n < heightArray.length; n++) {
            heightArray[n] = Main.WORLDPIXELHEIGHT * heightArray[n];
        }

        GameHeader header = new GameHeader();

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(header).colspan(3).expand().fill().prefHeight(heightArray[0]);
        table.row();
        table.add().prefHeight(heightArray[1]);
        table.row();
        table.add(inn).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        table.add(shop).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        table.add(tavern).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        table.row();
        table.add().prefHeight(heightArray[3]);
        table.row();
        table.add(adventure).colspan(3).prefHeight(heightArray[4]);
        table.row();
        table.add().prefHeight(heightArray[5]);
        table.row();
        table.add(menu).colspan(3).prefHeight(heightArray[6]);
        table.row();
        table.add().prefHeight(heightArray[7]);
        table.row();
        table.add(mainMenu).colspan(3).prefHeight(heightArray[8]);
        table.row();
        table.add().prefHeight(heightArray[9]);
        getStage().addActor(table);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}