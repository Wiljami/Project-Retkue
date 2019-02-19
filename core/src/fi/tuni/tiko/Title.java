package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Title extends Actor {
    private Texture texture;
    public Title() {
        texture = new Texture(Gdx.files.internal("retkue_title.png"));
        setWidth(texture.getWidth()/3f);
        setHeight(texture.getHeight()/3f);
        setBounds(0,0, getWidth(), getHeight());
        setX(100);
        setY(100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
