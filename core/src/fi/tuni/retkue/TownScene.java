package fi.tuni.retkue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * TownScene contains the town view and its functionality. It serves as the hub of the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */

/**
 * TownScene constructor.
 */
public class TownScene extends Scene {

    /**
     * Rererence to itself
     */
    TownScene townScene;

    /**
     * Constructor
     */
    public TownScene() {
        super();
        this.townInfo = Main.getTownInfo();
        createMenu();
        setupBackground("village.png");
        townScene = this;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Chillage.ogg"));
    }

    /**
     * Reference to the party object in the game.
     */
    private Party party;

    /**
     * Reference to the townInfo object
     */
    private TownInfo townInfo;

    /**
     * header object
     */
    private GameHeader header;

    /**
     * Reference to the button object leading to adventure
     */
    private TextButton adventure;

    /**
     * Reference to the button object leading to options
     */
    private ImageButton options;

    /**
     * createMenu adds various UI actors to the table and to the stage
     */
    public void createMenu() {
        adventure = new TextButton( "", getSkin());
        adventure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (townInfo.findChosenQuest() != null && party.checkForConsciousness()) {
                    AdventurePopUp adventurePopUp = new AdventurePopUp(townInfo.findChosenQuest());
                    adventurePopUp.show(getStage());
                } else {
                }
            }
        });

        options = new ImageButton(Utils.loadButtonImage("options.png", 50, 50));
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp(townScene);
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
        table.add().prefHeight(heightArray[2]);
        table.row();
        table.add().prefHeight(heightArray[3]);
        table.row();
        table.add(adventure).colspan(3).prefHeight(heightArray[4]);
        table.row();
        table.add().prefHeight(heightArray[5]);
        table.row();
        table.add().colspan(3).prefHeight(heightArray[6]);
        table.row();
        table.add().prefHeight(heightArray[7]);
        table.row();
        table.add().colspan(3).prefHeight(heightArray[8]);
        table.row();
        table.add().prefHeight(heightArray[9]);

        FadeActor shop = spawnShop();
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopPopUp shopPopUp = new ShopPopUp(party);
                shopPopUp.show(getStage());
            }
        });

        FadeActor inn = spawnInn();
        inn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                InnPopUp innPopUp = new InnPopUp(party, townScene);
                innPopUp.show(getStage());
            }
        });

        FadeActor tavern = spawnTavern();
        tavern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TavernPopUp tavernPopUp = new TavernPopUp(townScene, party);
                tavernPopUp.show(getStage());
            }
        });

        getStage().addActor(options);
        options.setX(300);
        options.setY(20);

        getStage().addActor(tavern);
        getStage().addActor(shop);
        getStage().addActor(inn);
        getStage().addActor(table);
        generateTexts();
    }
    /**
     * spawnShop creates the fading actor button for the shop
     * @return the FadeActor for the Shop button
     */
    private FadeActor spawnShop() {
        FadeActor shop = new FadeActor(Utils.loadTexture("shop_button.png"));
        float wRatio = shop.getWidth() / 1080;
        float hRatio = shop.getHeight() / 1920;
        shop.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        shop.setY(316f);
        shop.setX(87f);
        return shop;
    }

    /**
     * spawnInn creates the fading actor button for the inn
     * @return the FadeActor for the Inn button
     */
    private FadeActor spawnInn() {
        FadeActor inn = new FadeActor(Utils.loadTexture("inn_button.png"));
        float wRatio = inn.getWidth() / 1080;
        float hRatio = inn.getHeight() / 1920;
        inn.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        inn.setY(177f);
        return inn;
    }

    /**
     * spawnTavern creates the fading actor button for the tavern
     * @return the FadeActor for the Tavern button
     */
    private FadeActor spawnTavern() {
        FadeActor tavern = new FadeActor(Utils.loadTexture("tavern_button.png"));
        float wRatio = tavern.getWidth() / 1080;
        float hRatio = tavern.getHeight() / 1920;
        tavern.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        tavern.setY(215f);
        tavern.setX(229f);
        return tavern;
    }

    /**
     * Updates the text withing the adventure text button with the quest name.
     */
    private void updateQuestButton() {
        if (townInfo.findChosenQuest() != null) {
            String questName = townInfo.findChosenQuest().getTitle();
            adventure.setText(readLine("adventure") + ":\n" + questName);
        } else {
            adventure.setText(readLine("no_quest"));
        }
    }

    /**
     * Override to get updateQuestButton called
     */
    @Override
    public void generateTexts() {
        updateQuestButton();
    }

    /**
     * Override to get the header's values updated
     */
    @Override
    public void updateValues() {
        header.updateValues();
    }
}