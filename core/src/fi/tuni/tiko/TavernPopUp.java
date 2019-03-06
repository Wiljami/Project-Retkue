package fi.tuni.tiko;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
public class TavernPopUp extends Dialog {

    private static String windowStyle = "dialog";

    private static String title = "Tavern";
    private String text = "This is a tavern. In tavern you\n you choose between few tasks to do";
    public TavernPopUp() {
        super(title, Scene.getSkin(), windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * Override the hide action to get rid of the hide action
     */
    @Override
    public void hide() {
        hide(null);
    }

    @Override
    public Dialog show (Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }


    private String quest = "I have a mission for you. If you choose\n to accept it, then you can not...";

    private void confirmQuest() {
        Image image = new Image(Utils.loadTexture("old_guy1.png"));
        CustomDialog dialog = new CustomDialog(quest,"", image) {
            public void result(Object obj) {
                System.out.println(obj);
            }
        };
        //TODO: Localization!
        dialog.show(getStage());
    }

    private void createMenu() {
        Label desc = new Label(text, getSkin());

        Button quest1 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmQuest();
            }
        });

        Label label1 = new Label("Quest 1", Scene.getSkin());

        Button quest2 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmQuest();
            }
        });

        Label label2 = new Label("Quest 2", Scene.getSkin());

        Button quest3 = new ImageButton(Utils.loadButtonImage("quest icon", 50, 50));
        quest3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmQuest();
            }
        });

        Label label3 = new Label("Quest 3", Scene.getSkin());

        getContentTable().add(desc).colspan(2);

        getContentTable().row();
        getContentTable().add(quest1).pad(10).left();
        getContentTable().add(label1).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest2).pad(10).left();
        getContentTable().add(label2).pad(10).left();

        getContentTable().row();
        getContentTable().add(quest3).pad(10).left();
        getContentTable().add(label3).pad(10).left();

        button("Return", false);
    }
}