package fi.tuni.retkue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Scene is the abstract class that contains all the shared resources of the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public abstract class Scene extends ApplicationAdapter implements Screen {
    /**
     * camera is the camera used in the game
     */
    private static OrthographicCamera camera;

    /**
     * A HashMap that holds all the fonts used in the game. String is used to name the fonts and
     * other elements call the fonts with the name.
     */
    private static Map<String, BitmapFont> fonts;

    /**
     * LabelStyle labelHeadLine
     */
    private static Label.LabelStyle labelHeadline;

    /**
     * LabelStyle labelSmallHeadLine
     */
    private static Label.LabelStyle labelSmallHeadLine;

    /**
     * LabelStyle labelTextLabel
     */
    private static Label.LabelStyle labelTextLabel;

    /**
     * The skin we use in the game
     */
    private static Skin skin;

    /**
     * Reference to the main method of the game
     */
    private static Main game;

    /**
     * Reference to the SpriteBatch.
     */
    private static SpriteBatch batch;

    /**
     * Localization file
     */
    private static I18NBundle bundle;

    /**
     * initialized is a boolean that tracks if the static resources of this class have been created
     */
    private static boolean initialized = false;
    /**
     * disposed is a boolean that tracks if the static resources of this class have been disposed
     */
    private static boolean disposed = false;
    /**
     * debug is a boolean that tracks if debug mode is on or not.
     */
    public static boolean debug;

    /**
     * Each Scene has a Stage.
     */
    private Stage stage;

    /**
     * boolean wether the scene has a background or not
     */
    private boolean hasBackground;

    /**
     * background Texture of the scene
     */
    private Texture background;

    /**
     * Music instances for different scenes
     */
    public Music backgroundMusic;

    /**
     * Constructor for the Scene.
     */
    public Scene() {
        stage = new Stage(new FitViewport(Main.WORLDPIXELWIDTH,
                Main.WORLDPIXELHEIGHT), game.getBatch());
    }

    /**
     * initiateScene initiates all the shared resources used within the Scene. initialized boolean
     * is used to make sure that it is ran only once.
     * @param main reference to Main
     */
    public static void initiateScene(Main main) {
        if (!initialized) {
            initialized = true;
            game = main;
            batch = game.getBatch();
            setupFonts();
            setupCameras();
            setupSkins();
            setupStyles();
            debug = Main.debug;
            RetkueDialog.setRetkueSkin(getSkin());
            RetkueDialog.pointToGame(getGame());
        }
    }

    /**
     * loadBundle loads the bundle
     */
    public static void loadBundle() {
        Locale locale = new Locale(Config.getLanguageName());

        bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        RetkueDialog.giveBundle(bundle);
    }

    /**
     * Setup the different Styles
     */
    private static void setupStyles() {
        labelHeadline = new Label.LabelStyle(fontType("headline"), Color.WHITE);
        labelSmallHeadLine = new Label.LabelStyle(fontType("smallheadline"), Color.WHITE);
        labelTextLabel = new Label.LabelStyle(fontType("defaultFont"), Color.WHITE);
    }

    /**
     * Setup the different Fonts and add them to the fonts HashMap
     */
    private static void setupFonts() {
        fonts = new HashMap<String, BitmapFont>();
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        BitmapFont tempFont = fontGenerator.generateFont(parameter);

        fonts.put("defaultFont", tempFont);

        parameter.size = 12;
        tempFont = fontGenerator.generateFont(parameter);

        fonts.put("logFont", tempFont);

        parameter.size = 24;
        tempFont = fontGenerator.generateFont(parameter);

        fonts.put("bigText", tempFont);

        parameter.size = 48;
        tempFont = fontGenerator.generateFont(parameter);
        parameter.color = Color.WHITE;

        fonts.put("headline", tempFont);

        parameter.size = 18;
        tempFont = fontGenerator.generateFont(parameter);
        fonts.put("smallheadline", tempFont);
    }

    /**
     * Setup the different Skins
     */
    private static void setupSkins() {
        skin = new Skin();
        skin = new Skin (Gdx.files.internal("retku_final2.json"));
        skin.add("label", fonts.get("defaultFont"), BitmapFont.class);
        skin.add("log", fonts.get("logFont"), BitmapFont.class);
    }

    /**
     * Setup the camera.
     */
    private static void setupCameras () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WORLDPIXELWIDTH, Main.WORLDPIXELHEIGHT);
    }

    /**
     * Everything we wish to run in the render is included
     */
    public void sceneRender() {
        clearScreen();
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        renderBackground();
        batch.end();
        renderActions();
    }

    /**
     * renderBackground renders a background image if the scene has one.
     */
    public void renderBackground() {
        if (hasBackground) {
            getBatch().draw(background, 0, 0, Main.WORLDPIXELWIDTH,
                    Main.WORLDPIXELHEIGHT);
        }
    }

    /**
     * Clear the screen. For the use of the render.
     */
    private void clearScreen () {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Act and draw the stage's members
     */
    public void renderActions() {
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
    }

     /**
     * Try to read the localization file
     * @param key key of the text we want
     * @return String containing the text or an error message
     */
    public String readBundle (String key) {
        I18NBundle bundle = getBundle();
        try {
            return bundle.get(key);
        }
        catch(Exception e) {
            return "Error reading: " + key;
        }
    }

    /**
     * Override of the show to add functionality we want for our Scenes
     */
    @Override
    public void show() {
        getGame().saveGame();
        updateValues();
        backgroundMusic.setLooping(true);
        if(Config.isMuted()) {
            backgroundMusic.stop();
        } else {
            backgroundMusic.play();
        }
    }

    /**
     * resize()
     * @param width width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * pause()
     */
    @Override
    public void pause() {
    }

    /**
     * resume()
     */
    @Override
    public void resume() {
    }

    /**
     * Override for hide. We save the game and stop the backgroundMusic.
     */
    @Override
    public void hide() {
        getGame().saveGame();
        backgroundMusic.stop();
    }

    /**
     * Run the dispose for the scene. Uses the disposed boolean to make sure that static resources
     * are disposed only once.
     */
    @Override
    public void dispose() {
        if (!disposed) {
            for ( BitmapFont font : fonts.values()) {
                font.dispose();
            }
            disposed = true;
        }
        if (hasBackground) {
            background.dispose();
        }
        stage.dispose();
        super.dispose();
    }

    /**
     * render(float delta)
     * @param delta deltaTime
     */
    @Override
    public void render(float delta) {
        sceneRender();
    }

    /**
     * getBatch returns the SpriteBatch
     * @return SpriteBatch
     */
    public static SpriteBatch getBatch() {
        return batch;
    }

    /**
     * getGame returns the Main
     * @return reference to the game
     */
    public static Main getGame () {
        return game;
    }

    /**
     * getStage returns the Stage of this Scene
     * @return reference to the Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * getSkin returns the skin.
     * @return static reference to a Skin
     */
    public static Skin getSkin() {
        return skin;
    }

    /**
     * GetLabelHeadLine returns the LabelHeadLineStyle labelHeadLine
     * @return Label.LabelStyle labelHeadLine
     */
    public Label.LabelStyle getLabelHeadline() {
        return labelHeadline;
    }

    /**
     * getBundle() returns a reference to the localization
     * @return return reference to the localization bundle
     */
    public static I18NBundle getBundle() {
        return bundle;
    }

    /**
     * Background image of the scene.
     * @return background Texture
     */
    public Texture getBackground() {
        return background;
    }

    /**
     * Setups the background. Loads the Texture and sets the boolean to indicate that there is a
     * background image.
     * @param filename the filename of the image to be used.
     */
    public void setupBackground(String filename) {
        background = Utils.loadTexture(filename);
        setHasBackground(true);
    }

    /**
     * Setter for the hasBackground
     * @param hasBackground boolean value wether there is a background
     */
    public void setHasBackground(boolean hasBackground) {
        this.hasBackground = hasBackground;
    }

    /**
     * fontType method finds the font with the key name from the fonts HashMap. If there is no
     * matching font, then it will return the font with defaultFont key from the HashMap.
     * @param name the name of the wanted font
     * @return BitmapFont of the wanted font
     */
    public static BitmapFont fontType(String name) {
        try {
            return fonts.get(name);
        } catch(Exception e) {
            System.out.println("Could not find font: " + name + ". Using default font.");
            return fonts.get("defaultFont");
        }
    }

    /**
     * Call readLine from Utils to securely read the bundle
     * @param key key of the line
     * @return String matching the key in bundle
     */
    public static String readLine (String key) {
        String s = Utils.readBundle(bundle, key);
        return s;
    }

    /**
     * getLabelSmallHeadLine returns the LabelStyle labelSmallHeadLine
     * @return LabelStyle labelSmallHeadLine
     */
    public static Label.LabelStyle getLabelSmallHeadLine() {
        return labelSmallHeadLine;
    }

    /**
     * An empty method for generating texts. A Scene may want to override this.
     */
    public void generateTexts() {
    }

    /**
     * updateValues is an abstract method we want each Scene to have.
     */
    public abstract void updateValues();
}