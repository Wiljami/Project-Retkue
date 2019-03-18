package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    }

    /**
     * createMenu creates the UI of the mainMenu.
     */
    private void createMenu() {
        Button start = new TextButton(readLine("start"), getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.gameScreen);
            }
        });

        Button options = new TextButton(readLine("options"), getSkin());
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OptionsPopUp options = new OptionsPopUp();
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
        mainMenuTable.add(reset).prefHeight(heightArray[4]);
        mainMenuTable.add(loadGame).prefHeight(heightArray[4]);
        mainMenuTable.add(saveGame).prefHeight(heightArray[4]);
        getStage().addActor(mainMenuTable);
    }

    @Override
    public void updateValues() {
    }

    /**
     * dispose()
     */
    public void dispose() {
        super.dispose();
    }
}