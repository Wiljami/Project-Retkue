package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private Table mainMenuTable;

    public MainMenuScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        TextBox testBox = new TextBox("test", 200, 400,TextBox.FontType.headline);
        addText(testBox);

        Title title = new Title();

        Button start = new TextButton("Start", getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openGame();
            }
        });

        Button options = new TextButton("Options", getSkin());
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openOptions();
            }
        });

        Label test = new Label("test", getSkin());

        mainMenuTable= new Table();
        if (debug)mainMenuTable.debug();
        mainMenuTable.setFillParent(true);
        mainMenuTable.add(title).colspan(3);
        mainMenuTable.row();
        mainMenuTable.add().prefHeight(400);
        mainMenuTable.row();
        mainMenuTable.add(start).prefWidth(100);
        mainMenuTable.add(test).prefWidth(80);
        mainMenuTable.add(options).prefWidth(100);
        getStage().addActor(mainMenuTable);
    }

    @Override
    public void render(float delta) {
        sceneRender();
    }

    public void dispose() {
        super.dispose();
    }
}