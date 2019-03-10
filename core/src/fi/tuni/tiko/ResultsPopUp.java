package fi.tuni.tiko;

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
     * ResultsPopUp constructor
     */
    public ResultsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu creates visible UI actors
     */
    private void createMenu() {
        String text = readLine("results_desc");
        RetkueLabel desc = new RetkueLabel(text);
        getContentTable().add(desc).prefWidth(250);

        button(readLine("ok"), true);
    }

    /**
     * TODO: Make this neater
     * When ok button is clicked this is called and the game is redirected to the townMenu
     * @param obj obj is always true
     */
    public void result(Object obj) {
        if (obj.equals(true)) {
            //remove();
            game.openScene(Main.GameView.gameScreen);
        }
    }
}