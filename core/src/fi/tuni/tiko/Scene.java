package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A package containing necessary methods to run the graphical side of the KPS game.
 * Scene class implements screen and contains a stage. It generates an assortment of fonts, styles
 * and skins.
 *
 * TODO: create an HashMap to store fonts, styles and skins to make it neater.
 * TODO: dynamic addition of them as well
 * TODO: Is this worth the trouble? YES
 *
 * @author Viljami Pietarila
 * @version 2019.0307
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

    //TODO: Turn the Labels to a HashMap as well
    private static Label.LabelStyle labelHeadline;
    private static Label.LabelStyle labelComicHeadline;

    //TODO: Turn the skins to a HashMap as well
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
     * This is pretty outdated thing.
     * TODO: Remove this
     */
    private ArrayList<TextBox> texts;

    /**
     * boolean wether the scene has a background or not
     */
    private boolean hasBackground;

    /**
     * background Texture of the scene
     */
    private Texture background;

    /**
     * Constructor for the Scene. Initialized boolean is used to make sure that the static resources
     * are generated only once.
     * @param game reference to the main that creates and controls all the scenes
     */
    public Scene(Main game) {
        stage = new Stage(new FitViewport(game.WORLDPIXELWIDTH,
                game.WORLDPIXELHEIGHT), game.getBatch());
        if (!initialized) {
            initialized = true;
            this.game = game;
            this.batch = game.getBatch();
            setupFonts();
            setupCameras();
            setupSkins();
            setupStyles();
            debug = game.debug;
            //Set default skin for the dialogs in the game
            RetkueDialog.setRetkueSkin(getSkin());
            RetkueDialog.pointToGame(getGame());
            //TODO: Actually add the localization properly
            bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), game.locale);
            RetkueDialog.giveBundle(bundle);
        }
    }

    /**
     * Setup the different Styles
     */
    private void setupStyles() {
        labelHeadline = new Label.LabelStyle(fontType("headline"), Color.BLUE);
        labelComicHeadline = new Label.LabelStyle(fontType("comicHeadline"), Color.PINK);
    }

    /**
     * Setup the different Fonts and add them to the fonts HashMap
     * TODO: Refactor some of this code?
     */
    private void setupFonts() {
        fonts = new HashMap<String, BitmapFont>();
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.borderColor = Color.BLUE;
        parameter.borderWidth = 1;
        BitmapFont tempFont = fontGenerator.generateFont(parameter);

        fonts.put("defaultFont", tempFont);

        parameter.size = 24;
        tempFont = fontGenerator.generateFont(parameter);

        fonts.put("bigText", tempFont);

        parameter.size = 48;
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.WHITE;
        parameter.borderWidth = 3;
        tempFont = fontGenerator.generateFont(parameter);

        fonts.put("headline", tempFont);

        FreeTypeFontGenerator fontGeneratorComic =
                new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        parameter.size = 10;
        parameter.color = Color.PINK;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        tempFont = fontGeneratorComic.generateFont(parameter);

        fonts.put("comicSans", tempFont);

        parameter.size = 48;
        tempFont = fontGeneratorComic.generateFont(parameter);

        fonts.put("comicHeadline", tempFont);
    }

    /**
     * Setup the different Skins
     */
    public void setupSkins() {
        skin = new Skin (Gdx.files.internal("uiskin.json"));
    }

    /**
     * Setup the camera.
     */
    private void setupCameras () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLDPIXELWIDTH, game.WORLDPIXELHEIGHT);
    }

    /**
     * Everything we wish to run in the render is included
     */
    public void sceneRender() {
        clearScreen();
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        drawText();
        renderBackground();
        batch.end();
        renderActions();
    }

    /**
     * renderBackground renders a background image if the scene has one.
     */
    public void renderBackground() {
        if (hasBackground) {
            getBatch().draw(background, 0, 0, getGame().WORLDPIXELWIDTH,
                    getGame().WORLDPIXELHEIGHT);
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
     * Draw everything that has been included in the texts ArrayList
     */
    public void drawText() {
        if (texts != null) {
            for (TextBox textBox : texts) {
                fontType(textBox.font).draw(batch, textBox.text, textBox.x, textBox.y);
            }
        }
    }

    /**
     * Add a text to the texts ArrayList
     * @param textBox object containing the text, coordinates and font.
     */
    public void addText(TextBox textBox) {
        if (texts == null) {
            texts = new ArrayList<TextBox>();
        }
        centerText(textBox);
        texts.add(textBox);
    }

    /**
     * Find the center of a textBox
     * @param textBox object containing the text, coordinates and font.
     */
    private void centerText(TextBox textBox) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(fontType(textBox.font), textBox.text);
        textBox.x = textBox.x - (int)layout.width/2;
        textBox.x = textBox.x - (int)layout.height/2;
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
     * show()
     */
    @Override
    public void show() {
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
     * hide()
     */
    @Override
    public void hide() {

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
     * getTexts()
     * @return ArrayList of the texts
     */
    public ArrayList<TextBox> getTexts() {
        return texts;
    }

    /**
     * getBatch()
     * @return SpriteBatch
     */
    public static SpriteBatch getBatch() {
        return batch;
    }

    /**
     * getGame()
     * @return reference to the game
     */
    public static Main getGame () {
        return game;
    }

    /**
     * getStage8)
     * @return reference to the Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * getSkin()
     * TODO: Implement the HashList
     * @return static reference to a Skin
     */
    public static Skin getSkin() {
        return skin;
    }

    /**
     * GetLabel
     * TODO: Implement the HashList
     * @return
     */
    public Label.LabelStyle getLabelHeadline() {
        return labelHeadline;
    }

    /**
     * GetLabel
     * TODO: Implement the HashList
     * @return
     */
    public Label.LabelStyle getLabelComicHeadline() {
        return labelComicHeadline;
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
        String s  = Utils.readBundle(bundle, key);
        return s;
    }
}