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

public class GameScene extends Scene {
    public GameScene(Main game) {
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

        int topBarHeight = (int)(getGame().WORLDPIXELHEIGHT / 6.6f);

        Label steps = new Label("Steps", getSkin());
        Label sCount = new Label("Scount", getSkin());
        Label converter = new Label("Convert", getSkin());
        Label gold = new Label("Gold", getSkin());
        Label gCount = new Label("Money", getSkin());

        Table header = new Table();
        header.add(steps).prefHeight(topBarHeight);
        header.add(sCount).prefHeight(topBarHeight);
        header.add(converter).prefHeight(topBarHeight);
        header.add(gold).prefHeight(topBarHeight);
        header.add(gCount).prefHeight(topBarHeight);

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(header).colspan(3).expand().fill();
        table.row();
        table.add(new Label( readLine("town"), getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(inn).pad(10);
        table.add(shop).pad(10);
        table.add(tavern).pad(10);
        table.row();
        table.add(adventure).colspan(3);
        table.add().prefHeight(200);
        table.row();
        table.add(menu).colspan(3).pad(20);
        table.row();
        table.add(mainMenu).colspan(3).pad(20);
        getStage().addActor(table);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}