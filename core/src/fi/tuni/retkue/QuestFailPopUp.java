package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * QuestFailPopUp class holds the functionality of the situation when a quest fails
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class QuestFailPopUp extends RetkueDialog {
    /**
     * Title of the RetkeuDialog
     */
    private static String title = readLine("results");

    /**
     * Reference to the party
     */
    private Party party;

    /**
     * ResultsPopUp constructor
     */
    public QuestFailPopUp(Party party) {
        super(title);
        this.party = party;
        createMenu();
        Main.getTownInfo().rollNewQuests();
        Main.getTownInfo().rollNewItems();
        party.resetCosts();
        if (Main.debug) debug();
    }

    /**
     * createMenu creates visible UI actors
     */
    private void createMenu() {

        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        Image image = new Image(Utils.loadTexture("old_guy1.png"));

        String text = readLine("questFail");
        RetkueLabel desc = new RetkueLabel(text);

        getContentTable().add(image).prefWidth(image.getPrefWidth() / 5).prefHeight(image.getPrefHeight() / 5).colspan(3);
        getContentTable().row();

        getContentTable().add(desc).prefWidth(popUpWidth);


        Button ok = new TextButton(readLine("ok"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                game.openScene(Main.GameView.town);
            }
        });
        getContentTable().row();
        getContentTable().add(ok);
    }
}