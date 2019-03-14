package fi.tuni.tiko;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * TavernPopUp is a popup window that serves as the tavern for the game
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class TavernPopUp extends RetkueDialog {

    /**
     * WindowStyle of the TavernPopUp
     */
    private static String windowStyle = "dialog";

    /**
     * Reference to itself. Because of the subclasses we need this.
     */
    private static TavernPopUp tavernPopUp;

    /**
     * Window title of the tavern
     */
    private static String title = readLine("tavern");

    private TownInfo townInfo;

    /**
     * TavernPopUp constructor
     */
    public TavernPopUp(TownInfo townInfo) {
        super(title, skin, windowStyle);
        tavernPopUp = this;
        this.townInfo = townInfo;
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu creates various UI actors
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        String text = readLine("tavern_desc");
        RetkueLabel desc = new RetkueLabel(text);

        Button quest1 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                QuestPopUp questPopUp = new QuestPopUp(tavernPopUp);
                questPopUp.show(getStage());
            }
        });
        Quest quest = townInfo.findQuest(0);

        Label label0 = new Label(quest.getTitle(), skin);

        Button quest2 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        quest = townInfo.findQuest(1);

        Label label1 = new Label(quest.getTitle(), skin);

        Button quest3 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        quest = townInfo.findQuest(2);

        Label label2 = new Label(quest.getTitle(), skin);

        getContentTable().add(desc).colspan(2).prefWidth(popUpWidth);

        getContentTable().row();
        getContentTable().add(quest1).pad(10).left();
        getContentTable().add(label0).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest2).pad(10).left();
        getContentTable().add(label1).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest3).pad(10).left();
        getContentTable().add(label2).pad(10).left();

        button(readLine("return"), false);
    }
}