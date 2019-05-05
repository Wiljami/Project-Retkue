package fi.tuni.retkue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * OptionsPopUp contains the functionality of the options menu within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
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
     * TextButton for leaving the options menu
     */
    TextButton close;

    /**
     * Reference to the stage
     */
    private Stage stage;

    /**
     * Constructor for OptionsPopUp
     */
    public OptionsPopUp(Scene currentScene) {
        super(title);
        createMenu();
        if (Main.debug) debug();
        this.currentScene = currentScene;
        this.stage = getStage();
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
                currentScene.generateTexts();
                generateTexts();
            }
        });

        fi_flag.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config.setLanguage(Config.Language.FINNISH);
                currentScene.generateTexts();
                generateTexts();
            }
        });

        muteBox = new CheckBox("", getSkin());
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

        close = new TextButton("", getSkin());
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        TextButton credits = new TextButton("Credits", getSkin());
        credits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CreditsPopUp creditsPopUp = new CreditsPopUp();
                creditsPopUp.show(getStage());
            }
        });

        getContentTable().row();
        getContentTable().add(credits).colspan(2);
        getContentTable().row();
        getContentTable().add(close).colspan(2);
        generateTexts();
    }

    /**
     * closeMe tells the game to save itself and OptionsPopUp to close itself
     */
    private void closeMe() {
        getGame().saveGame();
        remove();
    }

    /**
     * generateTexts is called to generate different texts within the options menu
     */
    private void generateTexts() {
        muteBox.setText(readLine("mute"));
        close.setText(readLine("return"));
    }
}