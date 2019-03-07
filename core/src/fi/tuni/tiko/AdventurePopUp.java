package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * AdventurePopUp is the class that holds the functions for the adventure prompt pop up.
 *
 * @author Viljami Pietarila
 * @version 2019.0307
 */
public class AdventurePopUp extends RetkueDialog {
    //TODO: Make this more elegant, ok?
    private static String windowStyle = "dialog";

    /**
     * Title of the RetkueDialog window
     */
    private static String title = readLine("adventure");

    /**
     * Constructor
     */
    public AdventurePopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu() creates the elements within the pop up.
     */
    private void createMenu() {
        String text = readLine("adventure_desc");
        Label desc = new Label(text, skin);

        getContentTable().add(desc);
        getContentTable().row();

        button(readLine("back"), false);
        button(readLine("forward"), true);
    }

    /**
     * result is called when either back or forward button is clicked.
     * @param obj the result from clicking back or forward buttons.
     */
    public void result(Object obj) {
        if (obj.equals(true)) {
            remove();
            Quest quest = new Quest();
            game.getForestScene().setQuest(quest);
            quest.begin();
            game.openScene(Main.GameView.forest);
        }
    }
}