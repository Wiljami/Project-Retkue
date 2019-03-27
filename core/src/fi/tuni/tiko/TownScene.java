package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * TownScene contains the town view and its functionality. It serves as the hub of the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */

/**
 * TownScene constructor.
 */
public class TownScene extends Scene {

    public TownScene(Main game) {
        super(game);
        createMenu();
        this.townInfo = Main.getTownInfo();
        setupBackground("village.png");
        townTheme = Gdx.audio.newMusic(Gdx.files.internal("Chillage.ogg"));
    }

    /**
     * Reference to the party object in the game.
     */
    private Party party;

    private TownInfo townInfo;

    /**
     * header object
     */
    private GameHeader header;

    /**
     * createMenu adds various UI actors to the table and to the stage
     */
    private void createMenu() {
        Button mainMenu = new TextButton( readLine("backToMainMenu"), getSkin());
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.mainMenu);
            }
        });

/*        Button inn = new TextButton( readLine("inn"), getSkin());
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
        });*/

        Button adventure = new TextButton( readLine("adventure"), getSkin());
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (townInfo.findChosenQuest() != null) {
                    AdventurePopUp adventurePopUp = new AdventurePopUp(townInfo.findChosenQuest());
                    adventurePopUp.show(getStage());
                }
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

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/6.4f, 1/4f, 1/16f, 1/8f, 1/16f, 1/8f, 1/16f, 1/24f, 1/16f, 1/19.2f};

        Utils.convertToPixels(heightArray);

        party = getGame().getParty();

        header = new GameHeader(heightArray[0], party);

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(header).colspan(3).expand().fill().prefHeight(heightArray[0]);
        table.row();
        table.add().prefHeight(heightArray[1]);
        table.row();
        //table.add(inn).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        //table.add(shop).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        //table.add(tavern).padRight(10).padLeft(10).prefHeight(heightArray[2]);
        table.add().prefHeight(heightArray[2]);
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

        FadeActor shop = new FadeActor(Utils.loadTexture("shop_button.png"));
        float wRatio = shop.getWidth() / 1080;
        float hRatio = shop.getHeight() / 1920;
        shop.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        shop.setY(316f);
        shop.setX(87f);
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopPopUp shopPopUp = new ShopPopUp(party);
                shopPopUp.show(getStage());
            }
        });

        FadeActor inn = new FadeActor(Utils.loadTexture("inn_button.png"));
        wRatio = inn.getWidth() / 1080;
        hRatio = inn.getHeight() / 1920;
        inn.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        inn.setY(177f);
        inn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                InnPopUp innPopUp = new InnPopUp(party);
                innPopUp.show(getStage());
            }
        });

        FadeActor tavern = new FadeActor(Utils.loadTexture("tavern_button.png"));
        wRatio = tavern.getWidth() / 1080;
        hRatio = tavern.getHeight() / 1920;
        tavern.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        tavern.setY(215f);
        tavern.setX(229f);
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TavernPopUp tavernPopUp = new TavernPopUp();
                tavernPopUp.show(getStage());
            }
        });

        getStage().addActor(tavern);
        getStage().addActor(shop);
        getStage().addActor(inn);
    }

    @Override
    public void show() {
        townTheme.play();
    }

    @Override
    public void hide() {
        townTheme.stop();
    }

    @Override
    public void updateValues() {
        header.updateValues();
    }

    public TownInfo getTownInfo() {
        return townInfo;
    }
}