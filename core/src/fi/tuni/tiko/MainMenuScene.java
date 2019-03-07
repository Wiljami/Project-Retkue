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
 * @version 2019.0228
 */
public class MainMenuScene extends Scene {
    /**
     * Actor for title.
     * TODO: Can we do this better?
     */
    private Title title;

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
        title = new Title();

        Image temp = new Image(Utils.loadTexture("retkue_title.png"));
        temp.setSize(100,100);

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

        Table mainMenuTable= new Table();
        if (debug)mainMenuTable.debug();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add(temp).colspan(3).prefWidth(267).prefHeight(67);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(500);
        mainMenuTable.row();
        mainMenuTable.add(start).prefWidth(100);
        mainMenuTable.add().prefWidth(80);
        mainMenuTable.add(options).prefWidth(100);
        getStage().addActor(mainMenuTable);
    }

    public void dispose() {
        super.dispose();
        title.dispose();
    }
}