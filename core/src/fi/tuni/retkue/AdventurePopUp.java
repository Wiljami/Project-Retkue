package fi.tuni.retkue;

/**
 * AdventurePopUp is the class that holds the functions for the adventure prompt pop up.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class AdventurePopUp extends RetkueDialog {
    /**
     * Title of the RetkueDialog window
     */
    private static String title = readLine("adventure");

    /**
     * Reference to the Quest
     */
    private Quest quest;

    /**
     * constructor for AdventurePopUp
     * @param quest chosen Quest
     */
    public AdventurePopUp(Quest quest) {
        super(title);
        this.quest = quest;
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu() creates the elements within the pop up.
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        String text = readLine("adventure_desc");
        RetkueLabel desc = new RetkueLabel(text);

        getContentTable().add(desc).prefWidth(popUpWidth);
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
            game.getParty().setCurrentQuest(quest);
            game.getParty().beginQuest();
            Main.getTownInfo().clearChosenQuest();
//            game.getForestScene().setQuestOver(false);
            game.openScene(Main.GameView.forest);
        }
    }
}