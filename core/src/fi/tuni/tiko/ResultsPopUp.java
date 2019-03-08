package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * ResultsPopUp contains the functionality of the results pop up in the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0307
 */
public class ResultsPopUp extends RetkueDialog {
    //TODO: More elegant solution here
    private static String windowStyle = "dialog";

    /**
     * Title of the RetkeuDialog
     */
    private static String title = readLine("results");

    public ResultsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        String text = readLine("results_desc");
        RetkueLabel desc = new RetkueLabel(text);
        getContentTable().add(desc).prefWidth(250);

        button(readLine("ok"), true);
    }

    public void result(Object obj) {
        if (obj.equals(true)) {
            //remove();
            game.openScene(Main.GameView.gameScreen);
        }
    }
}