package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class containing the game actual.
 * TODO: Should this be split in two? One for town and one for the forest.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */
public class GameScene extends Scene {
    public GameScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        Button mainMenu = new TextButton("Return to Main Menu", getSkin());
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openMainMenu();
            }
        });

        Button playGame = new ImageButton(Utils.loadButtonImage("Booo", 50, 50));
        playGame.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
                System.out.println("waaaaah");
            }
        });

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Game", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(playGame);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(mainMenu);

        getStage().addActor(table);
    }
}
