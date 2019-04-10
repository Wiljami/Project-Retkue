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

import java.util.Arrays;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;
import static fi.tuni.tiko.Utils.toAddZero;

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
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("foresttheme.ogg"));
    }

    /**
     * createMenu creates the UI features on the screen.
     */
    private void createMenu() {
        generateLogTable();

        //Image retkue = new Image(Utils.loadTexture("retkue_title.png"));

        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/6.4f, 1/6f, 1/9.6f, 1/6.4f, 1/16f, 1/9.6f, 1/(5f + 1f/3f), 1/16f};

        Utils.convertToPixels(heightArray);

        float partyBarHeight = Main.WORLDPIXELWIDTH / 1080f * 444f;

        header = new GameHeader(heightArray[0], party);
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
    private Label textLog;
    private ScrollPane scrollLog;

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

        Label steps = new Label("", getSkin());

        textLog = new Label("", getLabelTextLabel());

        rawLog = "";
        textLog.setText(rawLog);

        textLog.setAlignment(1);

        scrollLog = new ScrollPane(textLog);

        scrollLog.layout();
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

        logTable.add(scrollLog).expandY();


        logTable.background(Utils.loadButtonImage("log.png", 0, 0));
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
            ResultsPopUp resultsPopUp = new ResultsPopUp(party);
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
            int n = MathUtils.random(5);
            if (n == 0) {
                randomEvent();
                timeSinceLastEvent = 0;
            }
            timeSinceLastCheck = 0;
        }
    }

    private int[] damageEvents = {1, 2, 6, 12, 15, 28, 29};
    private int eventDamage = 10;
    private int[] healEvents = {3, 11, 15, 26, 27};
    private int eventHeal = 10;

    private void randomEvent() {
        int event = MathUtils.random(29) + 1;
        String eventString = "QUEST_EVENT_RANDOM_";
        eventString += Utils.convertToId(event);
        int retku = MathUtils.random(2);
        String eventLine = party.findRetku(retku).getName();
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