package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * QuestPopUp holds the dialog between the player and the questgiver.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class QuestPopUp extends RetkueDialog {
    /**
     * Title of the quest window. It's empty as it is changed immediately.
     */
    private static String title = "";

    /**
     * no-button
     */
    private Button no;

    /**
     * yes-button
     */
    private Button yes;

    /**
     * ok-button
     */
    private Button ok;
    /**
     * Actual text for the window
     */
    private RetkueLabel desc;

    /**
     * Reference to the tavernPopUp
     */
    private static TavernPopUp tavernPopUp;

    /**
     * String accept is accept text
     */
    private String accept;

    /**
     * Reference to the party
     */
    private Party party;

    /**
     * Reference to the Quest
     */
    private Quest quest;

    /**
     * Reference to the Image
     */
    private Image image;

    /**
     * TextureRegion of the OldGuy2 image
     */
    private TextureRegionDrawable oldGuy2;

    /**
     * QuestPopUp constructor
     * @param tavernPopUp reference to the tavernPopUp that opened this
     * @param id id of the quest
     * @param party player's party
     */
    public QuestPopUp(final TavernPopUp tavernPopUp, final int id, final Party party) {
        super(title);

        this.party = party;

        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        QuestPopUp.tavernPopUp = tavernPopUp;
        image = new Image(Utils.loadTexture("old_guy1.png"));
        oldGuy2 = new TextureRegionDrawable(Utils.loadTexture("oldGuy2.png"));


        final TownInfo info = tavernPopUp.getTownInfo();
        quest = info.findQuest(id);

        getTitleLabel().setText(quest.getTitle());

        String text = quest.getBriefing();

        text += "\n\n" + readLine("reward");
        text += ": " + quest.getReward();
        text += "\n" + readLine("questLength");
        text += ": " + Utils.convertToTimeStamp(quest.getQuestLength());

        accept = quest.getDescription();

        yes = new TextButton(readLine("yes"), getSkin());
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                info.chooseQuest(id);
                party.setQuest(quest);
                acceptQuest();
            }
        });

        no = new TextButton(readLine("no"), getSkin());
        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });

        ok = new TextButton(readLine("ok"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        ok.setVisible(false);

        getContentTable().add(image).prefWidth(image.getPrefWidth() / 5).prefHeight(image.getPrefHeight() / 5).colspan(3);
        getContentTable().row();

        desc = new RetkueLabel(text);
        getContentTable().add(desc).prefWidth(popUpWidth).colspan(3);

        getContentTable().row();

        getContentTable().add(yes).pad(10);
        getContentTable().add(ok).pad(10);
        getContentTable().add(no).pad(10);
    }

    /**
     * closeMe method closes the tavernPopUp and the questPopUp
     */
    public void closeMe() {
        Scene.getGame().openScene(Main.GameView.town);
        tavernPopUp.remove();
        remove();
    }

    /**
     * acceptQuest changes the text on screen to the next text of the quest and hides the yes and no
     * buttons. It turns the ok button visible.
     */
    public void acceptQuest() {
        desc.setText(accept);
        no.setVisible(false);
        yes.setVisible(false);
        image.setDrawable(oldGuy2);
        ok.setVisible(true);
    }
}