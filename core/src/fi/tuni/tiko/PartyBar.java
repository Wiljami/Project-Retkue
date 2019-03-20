package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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

    /**
     * Constructor for the PartyBar
     * Height is used to tell this element how much height does it have in pixels
     *
     * @param height height of this element
     * @param party reference to the party
     */
    public PartyBar(float height, Party party) {
        this.party = party;

        float healthBarWidth = Main.WORLDPIXELWIDTH/3f - 10f;
        float healthBarHeight = height / 6f;
        float charSize = height - healthBarHeight;

        AnimatedActor retkuA = new AnimatedActor("bill_sprite_sheet_new.png", 6 , 1, 1/2f, charSize, charSize);
        AnimatedActor retkuC = new AnimatedActor("miked_sprite_sheet.png", 5 , 1, 1/1.5f, charSize*4/5f, charSize);


        Image retku0 = new Image(party.findRetku(0).getTexture());
        Image retku1 = new Image(party.findRetku(1).getTexture());
        Image retku2 = new Image(party.findRetku(2).getTexture());

        bar0 = new HealthBar(healthBarWidth, healthBarHeight);
        bar1 = new HealthBar(healthBarWidth, healthBarHeight);
        bar2 = new HealthBar(healthBarWidth, healthBarHeight);

        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());

        add(bar0).width(healthBarWidth).padLeft(2).padRight(2);
        add(bar1).width(healthBarWidth).padLeft(2).padRight(2);
        add(bar2).width(healthBarWidth).padLeft(2).padRight(2);

        row();

        add(retkuA).prefHeight(charSize).prefWidth(charSize);
        add(retku1).prefHeight(charSize).prefWidth(charSize);
        add(retkuC).prefHeight(charSize).prefWidth(charSize/5f*4f);

       // background(Utils.loadButtonImage("partybar_old.png", 0, 0));
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