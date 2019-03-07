package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ForestScene. Here we see the progress of the party in their current quest. It currently has a
 * simple timer to keep track of the progress
 *
 * @author Viljami Pietarila
 * @version 2019.0306
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

        Button faster = new TextButton("Boost", getSkin());
        faster.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quest.boost();
            }
        });

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label(readLine("forest"), getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(100);
        table.row();
        table.add(timer);
        table.row();
        table.add().prefHeight(100);
        table.row();
        table.add(faster);
        table.row();
        table.add().prefHeight(200);
        table.row();
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
     * @param time time left on the quest in ms
     */
    private void updateTimer(long time) {
        int hours   = (int) ((time / (1000*60*60)) % 24);
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