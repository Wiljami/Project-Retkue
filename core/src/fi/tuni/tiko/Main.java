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

	public final int WORLDPIXELHEIGHT = 640;
    public final int WORLDPIXELWIDTH = 360;

    public final int WORLDHEIGHT = 4;
    public final int WORLDWIDTH = 3;

    public boolean debug = false;
    //TODO: Currently unused
    Locale locale = Locale.getDefault();

    @Override
	public void create () {
		batch = new SpriteBatch();
        mainMenuScene = new MainMenuScene(this);
        optionScene = new OptionsScene(this);
        gameScene = new GameScene(this);
        openMainMenu();
	}

	public void openMainMenu() {
        Gdx.input.setInputProcessor(mainMenuScene.getStage());
        setScreen(mainMenuScene);
    }

    public void openOptions() {
        Gdx.input.setInputProcessor(optionScene.getStage());
        setScreen(optionScene);
    }

    public void openGame() {
        Gdx.input.setInputProcessor(gameScene.getStage());
        setScreen(gameScene);
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