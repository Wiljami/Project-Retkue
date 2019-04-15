package fi.tuni.tiko;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * TavernPopUp is a popup window that serves as the tavern for the game
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class TavernPopUp extends RetkueDialog {
    /**
     * Reference to itself. Because of the subclasses we need this.
     */
    private static TavernPopUp tavernPopUp;

    /**
     * Window title of the tavern
     */
    private static String title = readLine("tavern");

    private TownInfo townInfo;

    private Scene scene;

    private Party party;

    /**
     * TavernPopUp constructor
     */
    public TavernPopUp(Scene scene, Party party) {
        super(title);
        this.party = party;
        tavernPopUp = this;
        this.townInfo = Main.getTownInfo();
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

        Quest quest = townInfo.findQuest(0);

        TextButton label0 = new TextButton(quest.getTitle(), skin);
        label0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            openQuestPopUp(0);
            }
        });

        quest = townInfo.findQuest(1);
        TextButton label1 = new TextButton(quest.getTitle(), skin);
        label1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openQuestPopUp(1);
            }
        });

        quest = townInfo.findQuest(2);
        TextButton label2 = new TextButton(quest.getTitle(), skin);
        label2 .addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openQuestPopUp(2);
            }
        });

        getContentTable().add(desc).prefWidth(popUpWidth);

        getContentTable().row();
        getContentTable().add(label0).pad(5);

        getContentTable().row();
        getContentTable().add(label1).pad(5);

        getContentTable().row();
        getContentTable().add(label2).pad(5).padBottom(20);

        TextButton close = new TextButton(readLine("return"), getSkin());
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().row();
        getContentTable().add(close);
    }

    private void closeMe() {
        getGame().saveGame();
        remove();
    }

    private void openQuestPopUp (int questId) {
        QuestPopUp questPopUp = new QuestPopUp(tavernPopUp, questId, party);
        questPopUp.show(getStage());
    }


    public TownInfo getTownInfo() {
        return townInfo;
    }

    public Scene getScene() {
        return scene;
    }
}