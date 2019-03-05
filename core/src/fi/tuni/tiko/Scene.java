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

import javax.rmi.CORBA.Util;

/**
 * A package containing necessary methods to run the graphical side of the KPS game.
 * Scene class implements screen and contains a stage. It generates an assortment of fonts, styles
 * and skins.
 *
 * TODO: create an ArrayList to store fonts, styles and skins to make it neater.
 * TODO: dynamic addition of them as well
 * TODO: Is this worth the trouble?
 *
 * @author Viljami Pietarila
 * @version 2019.0228
 */
    public abstract class Scene extends ApplicationAdapter implements Screen {
    private static OrthographicCamera camera;
    private static OrthographicCamera textCamera;

    private static Map<String, BitmapFont> fonts;

    private static BitmapFont defaultFont;
    private static BitmapFont bigText;
    private static BitmapFont headline;
    private static BitmapFont comicSans;
    private static BitmapFont comicHeadline;

    private static Label.LabelStyle labelHeadline;
    private static Label.LabelStyle labelComicHeadline;

    private static Skin skin;

    private static Main game;
    private static SpriteBatch batch;
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

    private ArrayList<TextBox> texts;

    private boolean hasBackground;
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
            CustomDialog.setDialogSkin(getSkin());
            //TODO: Actually add the localization properly
            //bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), game.locale);
        }
    }

    /**
     * Setup the different Styles
     */
    private void setupStyles() {
        labelHeadline = new Label.LabelStyle(headline, Color.BLUE);
        labelComicHeadline = new Label.LabelStyle(comicHeadline, Color.PINK);
    }

    /**
     * Setup the different Fonts
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
        defaultFont = fontGenerator.generateFont(parameter);

        fonts.put("defaultFont", defaultFont);

        parameter.size = 24;
        bigText = fontGenerator.generateFont(parameter);

        fonts.put("bigText", bigText);

        parameter.size = 48;
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.WHITE;
        parameter.borderWidth = 3;
        headline = fontGenerator.generateFont(parameter);

        fonts.put("headline", headline);

        FreeTypeFontGenerator fontGeneratorComic =
                new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        parameter.size = 10;
        parameter.color = Color.PINK;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        comicSans = fontGeneratorComic.generateFont(parameter);

        fonts.put("comicSans", comicSans);

        parameter.size = 48;
        comicHeadline = fontGeneratorComic.generateFont(parameter);

        fonts.put("comicHeadline", comicHeadline);
    }

    /**
     * Setup the different Skins
     */
    public void setupSkins() {
        skin = new Skin (Gdx.files.internal("uiskin.json"));
    }

    /**
     * Setup the cameras. One camera is for general use and the other one is for text.
     */
    private void setupCameras () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLDWIDTH, game.WORLDHEIGHT);
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, game.WORLDPIXELWIDTH, game.WORLDPIXELHEIGHT);
    }

    /**
     * Everything we wish to run in the render is included
     */
    public void sceneRender() {
        clearScreen();
        camera.update();
        textCamera.update();
        batch.begin();
        //batch.setProjectionMatrix(camera.combined);
        //TODO: Figure it out if we need two cameras
        batch.setProjectionMatrix(textCamera.combined);
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
            getBatch().draw(background, 0, 0, getGame().WORLDPIXELWIDTH, getGame().WORLDPIXELHEIGHT);
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
                switch (textBox.type) {
                    case headline: headline.draw(batch, textBox.text, textBox.x, textBox.y); break;
                    case bigText: bigText.draw(batch, textBox.text, textBox.x, textBox.y); break;
                    case comicSans: comicSans.draw(batch, textBox.text, textBox.x, textBox.y); break;
                    case comicHeadline: comicHeadline.draw(batch, textBox.text, textBox.x, textBox.y); break;
                    default: defaultFont.draw(batch, textBox.text, textBox.x, textBox.y); break;
                }
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
        switch (textBox.type) {
            case headline: layout.setText(headline, textBox.text); break;
            case bigText: layout.setText(bigText, textBox.text); break;
            case comicSans: layout.setText(comicSans, textBox.text); break;
            case comicHeadline: layout.setText(comicHeadline, textBox.text); break;
            default: layout.setText(defaultFont, textBox.text); break;
        }
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
            defaultFont.dispose();
            headline.dispose();
            bigText.dispose();
            comicSans.dispose();
            comicHeadline.dispose();
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
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * getGame()
     * @return reference to the game
     */
    public Main getGame () {
        return game;
    }

    public static BitmapFont getHeadline() {
        return headline;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public Label.LabelStyle getLabelHeadline() {
        return labelHeadline;
    }

    public Label.LabelStyle getLabelComicHeadline() {
        return labelComicHeadline;
    }

    public static I18NBundle getBundle() {
        return bundle;
    }

    public Texture getBackground() {
        return background;
    }

    public void setupBackground(String filename) {
        background = Utils.loadTexture(filename);
        setHasBackground(true);
    }

    public boolean getHasBackground() {
        return hasBackground;
    }

    public void setHasBackground(boolean hasBackground) {
        this.hasBackground = hasBackground;
    }

    public BitmapFont fontType(String name) {
        return fonts.get(name);
    }
}