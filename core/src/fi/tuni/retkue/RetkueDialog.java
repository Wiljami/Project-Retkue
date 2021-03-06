package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * RetkueDialog is a custom dialog pop up used in the game. It extends dialogs and has some changes
 * compared to it. The actual PopUps need to extend RetkueDialog in turn
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public abstract class RetkueDialog extends Dialog {
    /**
     * Reference to the Skin we use in the game
     */
    public static Skin skin;

    /**
     * Reference to the Main
     */
    public static Main game;

    /**
     * Reference to the bundle used
     */
    private static I18NBundle bundle;


    /**
     * RetkuDialog constructor
     * @param title title of the window
     */
    public RetkueDialog(String title) {
        super(title, skin);
    }

    /**
     * Override the hide method to get rid of the action ran on Dialog's hide
     */
    @Override
    public void hide() {
        hide(null);
    }

    /**
     * Override the show method to get rid of the action ran on Dialog's show
     *
     * @param stage stage
     * @return this
     */
    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }

    /**
     * set the skin used in all the RetkueDialogs
     * @param s skin to be used
     */
    public static void setRetkueSkin(Skin s) {
        skin = s;
    }

    /**
     * give pointer to the Main
     * @param g Main
     */
    public static void pointToGame(Main g) {
        game = g;
    }

    /**
     * give pointer to the localization bundle
     * @param b localization I18NBundle
     */
    public static void giveBundle(I18NBundle b) {
        bundle = b;
    }

    /**
     * get I18NBundle localization
     * @return I18NBundle localization
     */
    public static I18NBundle getBundle() {
        return bundle;
    }

    /**
     * Call readLine from Utils to securely read the bundle
     * @param key key of the line
     * @return String matching the key in bundle
     */
    public static String readLine (String key) {
        String s  = Utils.readBundle(bundle, key);
        return s;
    }

    /**
     * Getter for Main
     * @return Main reference to Main
     */
    public static Main getGame() {
        return game;
    }
}