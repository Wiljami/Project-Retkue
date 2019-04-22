package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * OptionsPopUp contains the functionality of the options menu within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */

public class OptionsPopUp extends RetkueDialog {
    /**
     * Receives scene in which it is opened
     */
    Scene currentScene;

    /**
     * Title for the options window
     */
    private static String title = readLine("options");

    /**
     * Checkbox for mute
     */
    CheckBox muteBox;

    /**
     * Constructor for OptionsPopUp
     */
    public OptionsPopUp(Scene currentScene) {
        super(title);
        createMenu();
        if (Main.debug) debug();
        this.currentScene = currentScene;
    }

    /**
     * createMenu creates various visible UI actors.
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        this.getTitleLabel().setText(readLine("options"));
        RetkueLabel desc = new RetkueLabel("");

        ImageButton en_flag = new ImageButton(Utils.loadButtonImage("en_flag.png", 100, 100));
        ImageButton fi_flag = new ImageButton(Utils.loadButtonImage("fi_flag.png", 100, 100));

        en_flag.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config.setLanguage(Config.Language.ENGLISH);
            }
        });

        fi_flag.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config.setLanguage(Config.Language.FINNISH);
            }
        });

        muteBox = new CheckBox("Mute Music", getSkin());
        if(Config.isMuted()) {
            muteBox.setChecked(true);
        }

        getContentTable().add(desc).prefWidth(popUpWidth).colspan(2);
        getContentTable().row();
        getContentTable().add(fi_flag);
        getContentTable().add(en_flag);
        getContentTable().row();
        getContentTable().row();
        getContentTable().add(muteBox).colspan(2);

        muteBox.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.graphics.setContinuousRendering(muteBox.isChecked());
                Config.setIsMuted(muteBox.isChecked());
                if(Config.isMuted()) {
                    currentScene.backgroundMusic.stop();
                } else {
                    currentScene.backgroundMusic.play();
                }
            }
        });

        TextButton close = new TextButton(readLine("return"), getSkin());
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().row();
        getContentTable().add(close).colspan(2);
    }

    private void closeMe() {
        getGame().saveGame();
        remove();
    }
}