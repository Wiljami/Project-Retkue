package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ResultsPopUp contains the functionality of the results pop up in the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class ResultsPopUp extends RetkueDialog {
    //TODO: More elegant solution here
    /**
     * WindowStyle of the results window
     */
    private static String windowStyle = "dialog";

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
    public ResultsPopUp(Party party) {
        super(title, skin, windowStyle);
        this.party = party;
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu creates visible UI actors
     */
    private void createMenu() {

        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        String text = party.getCurrentQuest().getCompleteText();
        String reward = "\n\n" + readLine("reward") + ": ";
        reward += party.getCurrentQuest().getReward().getGold();
        reward += " " + readLine("golds");
        text += reward;
        RetkueLabel desc = new RetkueLabel(text);
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