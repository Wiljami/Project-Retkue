/**
 * A package containing necessary methods to run the graphical side of the KPS game.
 * Scene class implements screen and contains a stage. It generates an assortment of fonts, styles
 * and skins.
 *
 * @author Viljami Pietarila
 */
package fi.tuni.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public abstract class Scene extends ApplicationAdapter implements Screen {
    private static OrthographicCamera camera;
    private static OrthographicCamera textCamera;

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

    private static boolean initialized = false;
    private static boolean disposed = false;
    public static boolean debug = true;

    private Stage stage;

    private ArrayList<TextBox> texts;

    public Scene(Main game) {
        stage = new Stage(new FitViewport(game.WORLDPIXELWIDTH,
                game.WORLDPIXELHEIGHT), game.getBatch());
        //If this is the first instance of the scene then generate the static resources
        if (!initialized) {
            initialized = true;
            this.game = game;
            this.batch = game.getBatch();
            setupFonts();
            setupCameras();
            setupSkins();
            setupStyles();
            debug = game.debug;
            //bundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), game.locale);
        }
    }

    //Setup the styles used
    private void setupStyles() {
        labelHeadline = new Label.LabelStyle(headline, Color.BLUE);
        labelComicHeadline = new Label.LabelStyle(comicHeadline, Color.PINK);
    }

    //Setup the fonts used
    private void setupFonts() {
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.borderColor = Color.BLUE;
        parameter.borderWidth = 1;
        defaultFont = fontGenerator.generateFont(parameter);
        parameter.size = 24;
        bigText = fontGenerator.generateFont(parameter);
        parameter.size = 48;
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.WHITE;
        parameter.borderWidth = 3;
        headline = fontGenerator.generateFont(parameter);
        FreeTypeFontGenerator fontGeneratorComic =
                new FreeTypeFontGenerator(Gdx.files.internal("comic.ttf"));
        parameter.size = 10;
        parameter.color = Color.PINK;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        comicSans = fontGeneratorComic.generateFont(parameter);
        parameter.size = 48;
        comicHeadline = fontGeneratorComic.generateFont(parameter);
    }

    //Setup skins used
    public void setupSkins() {
        skin = new Skin (Gdx.files.internal("uiskin.json"));
    }

    //Setups two cameras. One to draw elements based on the meters and the other to draw text
    //based on pixels
    private void setupCameras () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLDWIDTH, game.WORLDHEIGHT);
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, game.WORLDPIXELWIDTH, game.WORLDPIXELHEIGHT);
    }

    //The 'actual' render that does the drawing of the elements
    public void sceneRender() {
        clearScreen();
        camera.update();
        textCamera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(textCamera.combined);
        drawText();
        batch.end();
        renderActions();
    }

    //Clear the screen
    private void clearScreen () {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    //Make the actors act
    public void renderActions() {
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
    }

    //Draw all the texts in the texts array
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

    //Add text to an array to be printed on to the screen each render.
    public void addText(TextBox textBox) {
        if (texts == null) {
            texts = new ArrayList<TextBox>();
        }
        centerText(textBox);
        texts.add(textBox);
    }

    //This method centers the text according to its size.
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

    //Try to read a localized file for texts
    public String readBundle (String key) {
        I18NBundle bundle = getBundle();
        try {
            return bundle.get(key);
        }
        catch(Exception e) {
            String error = "Error reading: " + key;
            return error;
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //Make sure you dispose static resources once
        if (!disposed) {
            defaultFont.dispose();
            headline.dispose();
            bigText.dispose();
            comicSans.dispose();
            comicHeadline.dispose();
            disposed = true;
        }
        stage.dispose();
        super.dispose();
    }

    @Override
    public void render(float delta) {
        sceneRender();
    }

    public ArrayList<TextBox> getTexts() {
        return texts;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

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
}