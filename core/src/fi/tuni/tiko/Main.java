package fi.tuni.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

/**
 * Main class for the 2019 spring mobile game project. Controls different scenes and their relations.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */
public class Main extends Game {
	SpriteBatch batch;
	MainMenuScene mainMenuScene;
	OptionsScene optionScene;
	GameScene gameScene;
	InnScene innScene;
	ShopScene shopScene;
	TavernScene tavernScene;
	AdventureScene adventureScene;
	ForestScene forestScene;
	ResultsScene resultsScene;

	public final int WORLDPIXELHEIGHT = 640;
    public final int WORLDPIXELWIDTH = 360;

    public final int WORLDHEIGHT = 4;
    public final int WORLDWIDTH = 3;

    public enum GameView {
        mainMenu, gameScreen, menu, inn, shop, tavern, adventure, forest, results
    }

    /**
     * debug boolean toggles debug features in the code
     */
    public boolean debug = true;
    //TODO: Currently unused
    Locale locale = Locale.getDefault();

    @Override
	public void create () {
        //TODO: implement elegant exit on BackKey
        //TODO: This is needed as default behavior bugs out the graphics
        Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
        mainMenuScene = new MainMenuScene(this);
        optionScene = new OptionsScene(this);
        gameScene = new GameScene(this);
        openScene(GameView.mainMenu);
	}

	public void openScene(GameView gameView) {
        Scene scene;
        switch(gameView) {
            case mainMenu: scene = mainMenuScene; break;
            case gameScreen: scene = gameScene; break;
            case menu: scene = optionScene; break;
            case inn: scene = mainMenuScene; break;
            case shop: scene = mainMenuScene; break;
            case tavern: scene = mainMenuScene; break;
            case adventure: scene = mainMenuScene; break;
            case forest: scene = mainMenuScene; break;
            case results: scene = mainMenuScene; break;
            default: throw new IllegalArgumentException ("openScene defaulted with " + gameView);
        }
        Gdx.input.setInputProcessor(scene.getStage());
        setScreen(scene);
    }

    private GameView origin;
    public void openScene(GameView gameView, GameView origin) {
        this.origin = origin;
        openScene(gameView);
    }

    public void returnToOrigin() {
        openScene(origin);
    }

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        mainMenuScene.dispose();
        optionScene.dispose();
        gameScene.dispose();
	}

    public SpriteBatch getBatch() {
		return batch;
	}
}