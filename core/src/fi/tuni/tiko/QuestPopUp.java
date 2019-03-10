package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * QuestPopUp holds the dialog between the player and the questgiver.
 * TODO: Fix the resizing when changing between texts
 *
 * @author Viljami Pietarila
 * @version 2019.0308
 */
public class QuestPopUp extends RetkueDialog {
    /**
     * windowStyle of the quest Window
     */
    private static String windowStyle = "dialog";

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
     * Constructor of QuestPopUp
     * @param tavernPopUp tavernPopUp Reference
     */
    public QuestPopUp(TavernPopUp tavernPopUp) {
        super(title, skin, windowStyle);
        this.tavernPopUp = tavernPopUp;
        Image image = new Image(Utils.loadTexture("old_guy1.png"));

        getTitleLabel().setText(readLine("QUEST_001_TITLE"));

        String text = readLine("QUEST_001_TEXT");

        yes = new TextButton(readLine("yes"), getSkin());
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        getContentTable().add(desc).prefWidth(250).colspan(3);

        getContentTable().row();

        getContentTable().add(yes).pad(10);
        getContentTable().add(ok).pad(10);
        getContentTable().add(no).pad(10);
    }

    /**
     * closeMe method closes the tavernPopUp and the questPopUp
     */
    public void closeMe() {
        tavernPopUp.remove();
        remove();
    }

    /**
     * acceptQuest changes the text on screen to the next text of the quest and hides the yes and no
     * buttons. It turns the ok button visible.
     */
    public void acceptQuest() {
        String accept = readLine("QUEST_001_ACCEPT");
        desc.setText(accept);
        no.setVisible(false);
        yes.setVisible(false);
        ok.setVisible(true);
    }
}