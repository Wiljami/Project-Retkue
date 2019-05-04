package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ResultsPopUp contains the functionality of the results pop up in the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class ResultsPopUp extends RetkueDialog {
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
     * @param party refence to the party
     */
    public ResultsPopUp(Party party) {
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

        Image image = new Image(Utils.loadTexture("old_guy3.png"));

        String text = party.getCurrentQuest().getCompleteText();
        String reward = "\n\n" + readLine("reward") + ": ";
        reward += party.getCurrentQuest().getReward().getGold();
        reward += " " + readLine("golds");
        text += reward;
        RetkueLabel desc = new RetkueLabel(text);

        party.earnGold(party.getCurrentQuest().getReward().getGold());

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