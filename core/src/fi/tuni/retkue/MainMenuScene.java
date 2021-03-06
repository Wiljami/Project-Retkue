package fi.tuni.retkue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Class holding the MainMenu scene.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class MainMenuScene extends Scene {
    /**
     * MainMenuScene constructor.
     */
    public MainMenuScene() {
        super();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("retkuetheme.ogg"));
        createMenu();
        setupBackground("mainmenu.png");
        menuScene = this;
    }

    /**
     * Reference to itself
     */
    Scene menuScene;

    /**
     * TextButton start
     */
    private TextButton start;

    /**
     * TextButton options
     */
    private ImageButton options;

    /**
     * createMenu creates the UI of the mainMenu.
     */
    private void createMenu() {
        start = new TextButton("", getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.town);
            }
        });

        options = new ImageButton(Utils.loadButtonImage("options.png", 50, 50));
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp(menuScene);
                options.show(getStage());
            }
        });

        getStage().addActor(options);
        options.setX(300);
        options.setY(20);

        generateTexts();

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/16f, 1/8f, 2/3f, 1/16f, 1/12f};

        Utils.convertToPixels(heightArray);

        float titleWidth = Main.WORLDPIXELWIDTH*3f/4f;
        float buttonWidth = Main.WORLDPIXELWIDTH/3.6f;

        Table mainMenuTable= new Table();
        if (debug)mainMenuTable.debug();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add().height(heightArray[0]);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(heightArray[1]);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(heightArray[2]);
        mainMenuTable.row();
        mainMenuTable.add(start).prefWidth(buttonWidth).prefHeight(heightArray[3]);
        mainMenuTable.row();
        getStage().addActor(mainMenuTable);
    }

    /**
     * Override for updateValues
     */
    @Override
    public void updateValues() {
    }

    /**
     * Override for hide to stop the music
     */
    @Override
    public void hide() {
        backgroundMusic.stop();
    }

    /**
     * dispose()
     */
    public void dispose() {
        super.dispose();
    }

    /**
     * overRide for generateTexts for the texts in mainMenu
     */
    @Override
    public void generateTexts() {
        start.setText(readLine("start"));
    }
}