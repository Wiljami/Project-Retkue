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
        Image title = new Image(Utils.loadTexture("retkue_title.png"));

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

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/16f, 1/8f, 2/3f, 1/16f, 1/12f};

        //Convert the heightArray values from percentages to pixels
        for (int n = 0; n < heightArray.length; n++) {
            heightArray[n] = Main.WORLDPIXELHEIGHT * heightArray[n];
        }

        float titleWidth = Main.WORLDPIXELWIDTH*3f/4f;
        float buttonWidth = Main.WORLDPIXELWIDTH/3.6f;

        Table mainMenuTable= new Table();
        if (debug)mainMenuTable.debug();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add().height(heightArray[0]);
        mainMenuTable.row();
        mainMenuTable.add(title).colspan(2).prefWidth(titleWidth).prefHeight(heightArray[1]);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(heightArray[2]);
        mainMenuTable.row();
        mainMenuTable.add(start).prefWidth(buttonWidth).prefHeight(heightArray[3]);
        mainMenuTable.add(options).prefWidth(buttonWidth).prefHeight(heightArray[3]);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(heightArray[4]);
        getStage().addActor(mainMenuTable);
    }

    /**
     * dispose()
     */
    public void dispose() {
        super.dispose();
    }
}