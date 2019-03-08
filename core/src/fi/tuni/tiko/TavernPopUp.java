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
 * @version 2019.0306
 */
public class TavernPopUp extends RetkueDialog {

    private static String windowStyle = "dialog";
    private static TavernPopUp tavernPopUp;


    private static String title = readLine("tavern");
    public TavernPopUp() {
        super(title, skin, windowStyle);
        tavernPopUp = this;
        createMenu();
        if (Main.debug) debug();
    }

    /*    private String quest = readLine("QUEST_001_TEXT");

    private void confirmQuest() {
        Image image = new Image(Utils.loadTexture("old_guy1.png"));
        CustomDialog dialog = new CustomDialog(quest,"", image) {
            public void result(Object obj) {
                System.out.println(obj);
            }
        };
        dialog.show(getStage());
    }
*/
    private void createMenu() {
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

        Label label1 = new Label("Quest 1", skin);

        Button quest2 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        Label label2 = new Label("Quest 2", skin);

        Button quest3 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        Label label3 = new Label("Quest 3", skin);

        getContentTable().add(desc).colspan(2).prefWidth(250);

        getContentTable().row();
        getContentTable().add(quest1).pad(10).left();
        getContentTable().add(label1).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest2).pad(10).left();
        getContentTable().add(label2).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest3).pad(10).left();
        getContentTable().add(label3).pad(10).left();

        button(readLine("return"), false);
    }
}