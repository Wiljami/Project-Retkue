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
 * @version 2019.02.19
 */
public class MainMenuScene extends Scene {

    private Title title;

    public MainMenuScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        title = new Title();

        Image temp = new Image(Utils.loadTexture("sad"));
        temp.setSize(100,100);

        Button start = new TextButton("Start", getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.gameScreen);
            }
        });

        Button options = new TextButton("Options", getSkin());
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.menu, Main.GameView.mainMenu);
            }
        });

        Table mainMenuTable= new Table();
        if (debug)mainMenuTable.debug();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add(temp).colspan(3).prefWidth(267).prefHeight(67);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(400);
        mainMenuTable.row();
        mainMenuTable.add(start).prefWidth(100);
        mainMenuTable.add().prefWidth(80);
        mainMenuTable.add(options).prefWidth(100);
        getStage().addActor(mainMenuTable);
    }

    @Override
    public void render(float delta) {
        sceneRender();
    }

    public void dispose() {
        super.dispose();
        title.dispose();
    }
}