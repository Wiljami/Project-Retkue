package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Class holding the MainMenu scene.
 *
 * @author Viljami Pietarila
 * @version 2019.0311
 */
public class MainMenuScene extends Scene {
    /**
     * MainMenuScene constructor.
     * @param game pointer to the Main.
     */
    public MainMenuScene(Main game) {
        super(game);
        createMenu();
        setupBackground("mainmenu.png");
        menuScene = this;
    }

    /**
     * ????
     */
    Scene menuScene;

    /**
     * createMenu creates the UI of the mainMenu.
     */
    private void createMenu() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("retkuetheme.ogg"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        if(getMute()) {
            backgroundMusic.setVolume(0);
        } else {
            backgroundMusic.setVolume(1);
        }

        Button start = new TextButton(readLine("start"), getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.town);
            }
        });

        Button options = new TextButton(readLine("options"), getSkin());
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp(menuScene);
                options.show(getStage());
            }
        });

        Button reset = new TextButton( "Reset Game", getSkin());
        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().getParty().newGame();
            }
        });

        Button loadGame = new TextButton( "Load Game", getSkin());
        loadGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveGame.load(getGame().getSaveFileName(), getGame().getParty());
            }
        });

        Button saveGame = new TextButton( "Save Game", getSkin());
        saveGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveGame.save(getGame().getSaveFileName(), getGame().getParty());
            }
        });

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
        mainMenuTable.add(options).prefWidth(buttonWidth).prefHeight(heightArray[3]);
        mainMenuTable.row();
/*        mainMenuTable.add(reset).prefHeight(heightArray[4]);
        mainMenuTable.add(loadGame).prefHeight(heightArray[4]);
        mainMenuTable.add(saveGame).prefHeight(heightArray[4]);*/
        getStage().addActor(mainMenuTable);
    }

    @Override
    public void updateValues() {
    }

    @Override
    public void show() {
        if(getMute()) {
            backgroundMusic.setVolume(0);
        } else {
            backgroundMusic.setVolume(1);
        }
        backgroundMusic.play();
    }

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
}