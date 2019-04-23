package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * PartyBar holds UI and functionality of the party display in forest scene.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class PartyBar extends Table {
    /**
     * HealthBar of the leftmost Retku
     */
    private HealthBar bar0;
    /**
     * HealthBar of the middle Retku
     */
    private HealthBar bar1;
    /**
     * HealthBar of the rightmost Retku
     */
    private HealthBar bar2;

    /**
     * Reference to the party
     */
    private Party party;

    private Stage stage;

    private AnimatedActor retkuA;
    private AnimatedActor retkuB;
    private AnimatedActor retkuC;

    /**
     * Constructor for the PartyBar
     * Height is used to tell this element how much height does it have in pixels
     *
     * @param height height of this element
     * @param party reference to the party
     */
    public PartyBar(float height, Party party, Stage stage) {
        this.party = party;
        this.stage = stage;

        float healthBarWidth = Main.WORLDPIXELWIDTH/3f - 10f;
        float healthBarHeight = height / 6f;
        float charSize = height - healthBarHeight - 20f;

        generateRetkuPortraits();

        bar0 = new HealthBar(healthBarWidth, healthBarHeight);
        bar1 = new HealthBar(healthBarWidth, healthBarHeight);
        bar2 = new HealthBar(healthBarWidth, healthBarHeight);

        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());


        add(retkuA).prefHeight(charSize).prefWidth(charSize);
        add(retkuB).prefHeight(charSize).prefWidth(charSize/5f*4f);
        add(retkuC).prefHeight(charSize).prefWidth(charSize/5f*4f);

        row();

        add(bar0).width(healthBarWidth).padLeft(2).padRight(2);
        add(bar1).width(healthBarWidth).padLeft(2).padRight(2);
        add(bar2).width(healthBarWidth).padLeft(2).padRight(2);


        background(Utils.loadButtonImage("partybar.png", 0, 0));
    }

    private void generateRetkuPortraits() {
        retkuA = party.findRetku(0).getPortrait();
        retkuA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(0));
                retkuPopUp.show(stage);
            }
        });

        retkuB = party.findRetku(1).getPortrait();
        retkuB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(1));
                retkuPopUp.show(stage);
            }
        });

        retkuC = party.findRetku(2).getPortrait();
        retkuC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(2));
                retkuPopUp.show(stage);
            }
        });
    }

    /**
     * updateHealthBars updates the healthBars of the retkus with new values of health.
     */
    public void updateHealthBars() {
        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());
    }
}