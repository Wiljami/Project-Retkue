package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * Class for the title.
 * TODO: Figure out something better.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */
public class Title extends Actor {
    private Texture texture;
    public Title() {
        texture = RetkueUtils.loadTexture("retkue_title.png");
        setWidth(266f);
        setHeight(67f);
        setBounds(0,0, getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        texture.dispose();
    }
}