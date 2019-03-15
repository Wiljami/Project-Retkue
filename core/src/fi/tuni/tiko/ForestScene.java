package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static fi.tuni.tiko.Utils.toAddZero;

/**
 * ForestScene. Here we see the progress of the party in their current quest. It currently has a
 * simple timer to keep track of the progress
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
//TODO: Everything

class ForestScene extends Scene{
    /**
     * Reference to the timer label so that it can be updated.
     */
    private Label timer;

    /**
     * Reference to the party of the game
     */
    private Party party;

    /**
     * partyBar is the bar showing the party's current situation
     */
    private PartyBar partyBar;

    /**
     * header is the bar on top of the screen showing information and providing its functionality
     */
    private GameHeader header;

    private Boolean questOver = false;

    /**
     * ForestScene constructor
     * @param game reference to the Main
     */
    public ForestScene(Main game) {
        super(game);
        party = getGame().getParty();
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
                faster();
            }
        });

        Button harder = new TextButton("Paranna", getSkin());
        harder.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                heal();
            }
        });

        //Image retkue = new Image(Utils.loadTexture("retkue_title.png"));

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/6.4f, 1/6f, 1/9.6f, 1/6.4f, 1/16f, 1/9.6f, 1/(5f + 1f/3f), 1/16f};

        Utils.convertToPixels(heightArray);

        header = new GameHeader(heightArray[0], party);
        partyBar = new PartyBar(heightArray[6], party);

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.top();
        table.add(header).colspan(2).expand().fill().prefHeight(heightArray[0]);
        table.row();
        table.add().prefHeight(heightArray[1]);
        table.row();
        //table.add(retkue).prefHeight(heightArray[2]).prefWidth(heightArray[2]).right().padRight(10);
        table.add(timer).left().padLeft(10).prefHeight(heightArray[2]).colspan(2).center();
        table.row();
        table.add().prefHeight(heightArray[3]);
        table.row();
        table.add(faster).right().padRight(15).prefHeight(heightArray[4]);
        table.add(harder).left().padLeft(15).prefHeight(heightArray[4]);
        table.row();
        table.add().prefHeight(heightArray[5]);
        table.row();
        table.add(partyBar).colspan(2).expand().fill().prefHeight(heightArray[6]);
        table.row();
        table.add().prefHeight(heightArray[7]);
        getStage().addActor(table);
    }

    /**
     * heal() is called when the heal button is pressed within the screen.
     *
     * Heal will increase the health of the Retkus, assuming they have enough gold
     * TODO: More and better here
     */
    private void heal() {
        if(party.getGold() >= 5) {
            party.spendGold(5);
            party.findRetku(0).healRetku(10);
            party.findRetku(1).healRetku(10);
            party.findRetku(2).healRetku(10);
        }
    }

    /**
     * faster() is called when the boost button is pressed within the screen
     *
     * Faster will decrease the time left of the quest.
     * TODO: More and better here
     */
    private void faster() {
        if(party.getGold() >= 5) {
            party.spendGold(5);
            party.boostQuest();
        }
    }

    /**
     * We Override the renderActions to add in the quest timer checkup.
     */
    @Override
    public void renderActions() {
        super.renderActions();
        if (party.timeLeft() > 0) {
            updateTimer();
        }
        if (party.timeLeft() < 0 && !questOver) {
            questOver = true;
            ResultsPopUp resultsPopUp = new ResultsPopUp();
            resultsPopUp.show(getStage());
            timer.setText("00:00:00");
        }

    }

    float timeSinceLastEvent;
    float meanTimeForEvent = 1;
    float timeSinceLastCheck;

    /**
     * events randomizes whether an event happens within x amount of seconds or not.
     * TODO: Create an EventManager class that handles the random events and such.
     */
    private void events() {
        timeSinceLastEvent += Gdx.graphics.getDeltaTime();
        timeSinceLastCheck += Gdx.graphics.getDeltaTime();
        if (timeSinceLastCheck > meanTimeForEvent) {
            int n = MathUtils.random(1);
            if (n == 1) {
                hitRetku();
                timeSinceLastEvent = 0;
            }
            timeSinceLastCheck = 0;
        }
    }

    /**
     * hitRetku method simulates some event etc. within game that damages a retku
     */
    private void hitRetku() {
        int retku = MathUtils.random(2);
        System.out.println("Hitting retku no. " + retku);
        party.findRetku(retku).damageRetku(10);
    }

    /**
     * Update the timer label depending on how much time is left on the quest
     * Converts the milliseconds to hours, minutes and seconds.
     */
    private void updateTimer() {
        long timeLeft = party.timeLeft();
        events();

        int hours   = (int) ((timeLeft / (1000*60*60)) / 24);
        int minutes = (int) ((timeLeft / (1000*60)) % 60);
        int seconds = (int) (timeLeft / 1000) % 60;
        String timerText = toAddZero(hours) + ":" + toAddZero(minutes) + ":" + toAddZero(seconds);
        timer.setText(timerText);
    }

    @Override
    public void updateValues() {
        header.updateValues();
        partyBar.updateHealthBars();
    }

    public void setQuestOver(Boolean questOver) {
        this.questOver = questOver;
    }
}