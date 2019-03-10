package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ForestScene. Here we see the progress of the party in their current quest. It currently has a
 * simple timer to keep track of the progress
 *
 * @author Viljami Pietarila
 * @version 2019.0307
 */
//TODO: Everything

class ForestScene extends Scene{
    /**
     * Reference to the current quest. If null then we assume that there is no quest currently
     */
    private Quest quest;
    /**
     * Reference to the timer label so that it can be updated.
     */
    private Label timer;

    /**
     * ForestScene constructor
     * @param game reference to the Main
     */
    public ForestScene(Main game) {
        super(game);
        createMenu();
        setupBackground("forest.png");
    }

    /**
     * createMenu creates the UI features on the screen.
     */
    private void createMenu() {
        timer = new Label("00:00:00", getSkin());

        Button faster = new TextButton("Vauhdita", getSkin());
        faster.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quest.boost();
            }
        });

        Button harder = new TextButton("Voimista", getSkin());
        harder.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("You clicked Harder!");            }
        });

        Image retkue = new Image(Utils.loadTexture("retkue.png"));

        GameHeader header = new GameHeader();
        PartyBar partyBar = new PartyBar();

        //TODO: header.getHeight() to a smarter way.

        float temp1 = 170f/1920f;
        float temp2 = 140f/1920f;
        float temp3 = 310f/1920f;
        float temp4 = 115f/1920f;

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.top();
        table.add(header).colspan(2).expand().fill().prefHeight(header.getHeight());
        table.row();
        table.add().prefHeight(temp3*Main.WORLDPIXELHEIGHT);
        table.row();
        table.add(retkue).prefHeight(70).prefWidth(70).right().padRight(10);
        table.add(timer).left().padLeft(10);
        table.row();
        table.add().prefHeight(temp3*Main.WORLDPIXELHEIGHT);
        table.row();
        table.add(faster).right().padRight(15).prefHeight(temp4*Main.WORLDPIXELHEIGHT);
        table.add(harder).left().padLeft(15).prefHeight(temp4*Main.WORLDPIXELHEIGHT);
        table.row();
        table.add().prefHeight(temp1*Main.WORLDPIXELHEIGHT);
        table.row();
        table.add(partyBar).colspan(2).expand().fill().prefHeight(partyBar.getHeight());
        table.row();
        table.add().prefHeight(temp2*Main.WORLDPIXELHEIGHT);
        getStage().addActor(table);
    }

    /**
     * We Override the renderActions to add in the quest timer checkup.
     */
    @Override
    public void renderActions() {
        super.renderActions();
        if (quest != null) {
            long timeLeft = quest.timeLeft();
            if (timeLeft < 0) {
                ResultsPopUp resultsPopUp = new ResultsPopUp();
                resultsPopUp.show(getStage());
                quest = null;
                timer.setText("00:00:00");
            }
            updateTimer(timeLeft);
        }
    }

    /**
     * Update the timer label depending on how much time is left on the quest
     * Converts the milliseconds to hours, minutes and seconds.
     * @param time time left on the quest in ms
     */
    private void updateTimer(long time) {
        int hours   = (int) ((time / (1000*60*60)) / 24);
        int minutes = (int) ((time / (1000*60)) % 60);
        int seconds = (int) (time / 1000) % 60;
        timer.setText(toAddZero(hours) + ":" + toAddZero(minutes) + ":" + toAddZero(seconds));
    }

    /**
     * Check wether the int is a single digit or not. If single digit, then we add a 0 in front
     * of the String
     * @param number the int we check
     * @return the String we return
     * TODO: Move this to utils
     */
    private String toAddZero(int number) {
        String s;
        if (number < 10) {
            s = "0" + Integer.toString(number);
        } else {
            s = Integer.toString(number);
        }
        return s;
    }

    /**
     * Set the current quest
     * @param q the quest we have
     */
    public void setQuest(Quest q) {
        this.quest = q;
    }
}