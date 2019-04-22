package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RetkuPopUp extends RetkueDialog {
    public RetkuPopUp(Retku retku) {
        super(retku.getName());

        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        RetkueLabel desc = new RetkueLabel(retku.getRetkuDesc());

        String attackText = readLine("attack") + ": ";
        attackText += retku.getAttack();
        RetkueLabel attack = new RetkueLabel(attackText);

        String defenceText = readLine("defence") + ": ";
        defenceText += retku.getDefence();
        RetkueLabel defence = new RetkueLabel(defenceText);

        String healthText = readLine("health") + ": ";
        healthText += retku.getCurrHealth() + "/" + retku.getMaxHealth();
        RetkueLabel health = new RetkueLabel(healthText);

        getContentTable().add(desc);
        getContentTable().row();
        getContentTable().add(attack);
        getContentTable().row();
        getContentTable().add(defence);
        getContentTable().row();
        getContentTable().add(health).prefWidth(popUpWidth);
        getContentTable().row();

        Button ok = new TextButton(readLine("close"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });
        getContentTable().row();
        getContentTable().add(ok);
    }
    private void closeMe() {
        remove();
    }
}
