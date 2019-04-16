package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ForestScene. Here we see the progress of the party in their current quest. It currently has a
 * simple timer to keep track of the progress
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
//TODO: Everything

public class ForestScene extends Scene{
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

    private Boolean questOver = false;

    /**
     * ForestScene constructor
     */
    public ForestScene() {
        super();
        party = getGame().getParty();
        createMenu();
        setupBackground("forest.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("foresttheme.ogg"));
    }

    /**
     * createMenu creates the UI features on the screen.
     */
    private void createMenu() {
        generateLogTable();

        float[] heightArray = {1/6.4f, 1/6f, 1/9.6f, 1/6.4f, 1/16f, 1/9.6f, 1/(5f + 1f/3f), 1/16f};

        Utils.convertToPixels(heightArray);

        float partyBarHeight = Main.WORLDPIXELWIDTH / 1080f * 444f;

        partyBar = new PartyBar(partyBarHeight, party);

        Table table = new Table();
        table.setFillParent(true);

        float logTableHeight = Main.WORLDPIXELHEIGHT / 4f * 3f;
        float logtableWidth = Main.WORLDPIXELWIDTH;

        table.add(logTable).prefHeight(logTableHeight).prefWidth(logtableWidth).pad(10);
        table.row();

        table.add(partyBar).prefHeight(partyBarHeight);
        table.row();
        table.add().prefHeight(heightArray[7]);
        getStage().addActor(table);
    }

    private Table logTable;
    private RetkueLabel textLog;
    private ScrollPane scrollLog;
    private RetkueLabel steps;

    private void generateLogTable() {
        logTable = new Table();
        Button faster = new TextButton(readLine("faster"), getSkin());
        faster.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                faster();
            }
        });

        Button harder = new TextButton(readLine("harder"), getSkin());
        harder.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                heal();
            }
        });

        steps = new RetkueLabel("");
        steps.setAlignment(0);
        steps.setWrap(false);
        updateSteps();

        textLog = new RetkueLabel("", "log");
        textLog.setWrap(false);

        textLog.setAlignment(0);

        rawLog = "";
        textLog.setText(rawLog);

        scrollLog = new ScrollPane(textLog);

        scrollLog.layout();
        scrollLog.isForceScrollY();
        scrollLog.scrollTo(0,0,0,0);

        Table logTop = new Table();
        logTop.add(faster).pad(5);
        logTop.add(harder).pad(5);
        logTop.add(steps).pad(5);
        logTable.add(logTop).top();

        logTable.row();

        timer = new Label("00:00:00", getLabelHeadline());
        logTable.add(timer).center();

        logTable.row();

        logTable.add(scrollLog).expandY().pad(5);


        logTable.background(Utils.loadButtonImage("log.png", 0, 0));
    }

    /**
     * heal() is called when the heal button is pressed within the screen.
     *
     * Heal will increase the health of the Retkus, assuming they have enough gold
     * TODO: More and better here
     */
    private void heal() {
        HealDialog healDialog = new HealDialog(party);
        healDialog.show(getStage());
    }

    /**
     * faster() is called when the boost button is pressed within the screen
     *
     * Faster will decrease the time left of the quest.
     * TODO: More and better here
     */
    private void faster() {
        FasterDialog fasterDialog = new FasterDialog(party);
        fasterDialog.show(getStage());
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
            ResultsPopUp resultsPopUp = new ResultsPopUp(party);
            resultsPopUp.show(getStage());
            timer.setText("00:00:00");
        }

    }

    /**
     * float of time since last random event
     */
    private float timeSinceLastEvent;

    /**
     * How often we check with random wether there is an evernt or not
     */
    private float checkTime = 5f;

    /**
     * float of time since last random event check
     */
    private float timeSinceLastCheck;

    /**
     * float that determines the chance of events, higher means less frequently
     */
    private float eventChance = 2000f;

    /**
     * events triggers random events based on time spent
     *
     * events runs on every frame and adds DeltaTime to the helper variables. Every checkTime
     * seconds it randomizes a value between 0 and eventChance. If it is less than
     * timeSinceLastEvent an event will trigger
     */
    private void events() {
        timeSinceLastEvent += Gdx.graphics.getDeltaTime();
        timeSinceLastCheck += Gdx.graphics.getDeltaTime();
        if (timeSinceLastCheck > checkTime) {
            float n = MathUtils.random(eventChance);
            if (n < timeSinceLastEvent) {
                randomEvent();
                timeSinceLastEvent = 0;
            }
            timeSinceLastCheck = 0;
        }
    }

    /**
     * Array of event IDs that cause damage to the party
     */
    private int[] damageEvents = {1, 2, 6, 12, 15, 28, 29};

    /**
     * The amount of damage the events cause
     */
    private int eventDamage = 10;

    /**
     * Array of event IDs that heal the party
     */
    private int[] healEvents = {3, 11, 15, 26, 27};

    /**
     * The amount healed by the events
     */
    private int eventHeal = 10;

    private void randomEvent() {
        int event = MathUtils.random(28) + 1;
        String eventString = "QUEST_EVENT_RANDOM_";
        eventString += Utils.convertToId(event);
        int retku = MathUtils.random(2);
        String eventLine = Utils.convertToTimeStamp(party.timeSpent()) + " - ";
        eventLine += party.findRetku(retku).getName();
        eventLine += " " + readLine(eventString) + "\n";
        addToLog(eventLine);
        if (Utils.intArrayContains(damageEvents, event)) {
            party.findRetku(retku).damageRetku(eventDamage);
        } else if (Utils.intArrayContains(healEvents, event)) {
            party.findRetku(retku).healRetku(eventHeal);
        }
    }

    private String rawLog;

    private void addToLog(String text) {
        rawLog += text;
        textLog.setText(rawLog);
        scrollLog.layout();
        scrollLog.scrollTo(0,0,0,0);
    }

    /**
     * Update the timer label depending on how much time is left on the quest
     * Converts the milliseconds to hours, minutes and seconds.
     */
    private void updateTimer() {
        long timeLeft = party.timeLeft();
        events();

        String timerText = Utils.convertToTimeStamp(timeLeft);
        timer.setText(timerText);
        updateSteps();
    }

    private void updateSteps() {
        String stepsString = readLine("steps") + ": " + party.getSteps();
        steps.setText(stepsString);
    }

    @Override
    public void updateValues() {
        partyBar.updateHealthBars();
    }

    public void setQuestOver(Boolean questOver) {
        this.questOver = questOver;
    }
}