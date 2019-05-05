package fi.tuni.retkue;

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
 * @version 2019.0505
 */

public class ForestScene extends Scene{
    /**
     * Label timer
     */
    private Label timer;

    /**
     * Label timerToEncounter
     */
    private Label timerToEncounter;

    /**
     * Reference to the party of the game
     */
    private Party party;

    /**
     * partyBar is the bar showing the party's current situation
     */
    private PartyBar partyBar;

    /**
     * Boolean wether the quest is over
     */
    private Boolean questOver;

    /**
     * Boolean wether the timer is paused
     */
    private Boolean paused;

    /**
     * ForestScene constructor
     */
    public ForestScene() {
        super();
        party = getGame().getParty();
        createMenu();
        setupBackground("forest.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("foresttheme.ogg"));
        questOver = false;
        paused = false;
    }

    /**
     * createMenu creates the UI features on the screen.
     */
    private void createMenu() {
        generateLogTable();

        float[] heightArray = {1/6.4f, 1/6f, 1/9.6f, 1/6.4f, 1/16f, 1/9.6f, 1/(5f + 1f/3f), 1/16f};

        Utils.convertToPixels(heightArray);

        float partyBarHeight = Main.WORLDPIXELWIDTH / 1080f * 444f;

        partyBar = new PartyBar(partyBarHeight, party, getStage());

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

    /**
     * Table logTable
     */
    private Table logTable;

    /**
     * RetkueLabel textLog
     */
    private RetkueLabel textLog;

    /**
     * ScrollPane scrollLog
     */
    private ScrollPane scrollLog;

    /**
     * RetkueLabel steps
     */
    private RetkueLabel steps;

    /**
     * generateLogTable creates the log table UI elements
     */
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

        timer = new Label("00:00:00", getLabelSmallHeadLine());
        timerToEncounter = new Label("00:00:00", getLabelHeadline());

        logTable.add(timerToEncounter).center();
        logTable.row();
        logTable.add(timer).center();

        logTable.row();

        logTable.add(scrollLog).expandY().pad(5);


        logTable.background(Utils.loadButtonImage("log.png", 0, 0));
    }

    /**
     * heal() is called when the heal button is pressed within the screen.
     *
     * Heal will increase the health of the Retkus, assuming they have enough gold
     */
    private void heal() {
        HealDialog healDialog = new HealDialog(party);
        healDialog.show(getStage());
    }

    /**
     * faster() is called when the boost button is pressed within the screen
     *
     * Faster will decrease the time left of the quest.
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
        if (party.timeLeft() < 0 && !questOver && !paused) {
            questOver = true;
            if(party.getCurrentQuest().isMainQuest()) party.completedMainQuest();
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
    private final float CHECKTIME = 5f;

    /**
     * float of time since last random event check
     */
    private float timeSinceLastCheck;

    /**
     * float that determines the chance of events, higher means less frequently
     */
    private final float EVENTCHANCE = 100f;

    /**
     * float to determine how often we autosave the game while in forestscene.
     */
    private final float AUTOSAVETIMER = 300f;

    /**
     * float timeSinceLastAutoSave
     */
    private float timeSinceLastAutoSave;

    /**
     * boolean encounter - wether you're currently in an encounter
     */
    private boolean encounter = false;

    /**
     * float timeSincelastEncounterTurn - time passed since last encounter turn
     */
    private float timeSinceLastEncounterTurn;

    /**
     * Time between encounter turns
     */
    private final float ENCOUNTERTURNTIMER = 5f;

    /**
     * events triggers random events based on time spent
     *
     * events runs on every frame and adds DeltaTime to the helper variables. Every CHECKTIME
     * seconds it randomizes a value between 0 and eventChance. If it is less than
     * timeSinceLastEvent an event will trigger
     */
    private void events() {
        if (party.timeLeftToEncounter() < 0 && !party.isQuestEncounter()) {
            paused = true;
            encounter();
        }
        timeSinceLastEvent += Gdx.graphics.getDeltaTime();
        timeSinceLastCheck += Gdx.graphics.getDeltaTime();
        timeSinceLastAutoSave += Gdx.graphics.getDeltaTime();
        if (encounter) {
            timeSinceLastEncounterTurn += Gdx.graphics.getDeltaTime();
            if (timeSinceLastEncounterTurn > ENCOUNTERTURNTIMER) {
                fighting();
                timeSinceLastEncounterTurn = 0;
            }
        }
        if (timeSinceLastCheck > CHECKTIME) {
            float n = MathUtils.random(EVENTCHANCE);
            if (n < timeSinceLastEvent) {
                randomEvent();
                timeSinceLastEvent = 0;
            }
            timeSinceLastCheck = 0;
        }
        if (timeSinceLastAutoSave > AUTOSAVETIMER) {
            getGame().saveGame();
            timeSinceLastAutoSave = 0;
        }
    }

    /**
     * Time spent in the encounter
     */
    private long encounterTime;

    /**
     * localized name of the enemy
     */
    String enemy;

    /**
     * Life of the enemey
     */
    private int enemyLife;

    /**
     * attack value of the enemy
     */
    private int enemyAttack;

    /**
     * defence value of the enemy
     */
    private int enemyDefence;

    /**
     * initalises the encounter
     */
    private void encounter() {
        encounterTime = System.currentTimeMillis();
        enemy = party.getQuest().getEnemyName();
        enemyLife = 100;
        enemyDefence = 5;
        enemyAttack = 10;
        party.setQuestEncounter(true);
        paused = true;
        EncounterPopUp encounterPopUp = new EncounterPopUp(enemy, this);
        encounterPopUp.show(getStage());
    }

    /**
     * begins the encounter
     */
    public void beginEncounter() {
        encounter = true;
    }

    /**
     * when in encounter fighting is called to do encounter turns
     */
    private void fighting() {
        String logLine = "";
        String attacks = readLine("attacks");
        String hits = readLine("hits");
        Retku retku = party.getRandomConsciousRetku();
        String time = Utils.convertToTimeStamp(party.timeSpent()) + " - ";
        int damage = enemyAttack + combatRandomizer() - retku.getDefence();
        if (damage < 0) damage = 0;
        logLine += time + enemy + " " + hits + " " + retku.getRetkuAsTarget() + "\n";

        retku.damageRetku(damage);
        partyBar.updateHealthBars();

        if (!party.checkForConsciousness()) {
            failQuest();
        }

        addToLog(logLine);

        logLine = retku.getName() + " " + readLine("takes") + " " + damage + " " + readLine("damage") + ".\n";
        addToLog(logLine);

        for (int n = 0; n < 3; n++) {
            retku = party.findRetku(n);
            if (retku.isConscious()) {
                damage = retku.getAttack() + combatRandomizer() - enemyDefence;
                if (damage < 0) damage = 0;
                enemyLife = enemyLife - damage;
                time = Utils.convertToTimeStamp(party.timeSpent()) + " - ";
                logLine = time + retku.getName() + " " + attacks + " " + enemy + "!\n";
            }
            addToLog(logLine);
            logLine = enemy + " " + readLine("takes") + " " + damage + " " + readLine("damage") + ".\n";
            addToLog(logLine);
        }

        if (enemyLife < 0) {
            encounterOver();
        }
    }

    /**
     * encounterOver is run when the encounter is over. Resumes the quest.
     */
    private void encounterOver() {
        encounter = false;
        long timePassed = System.currentTimeMillis() - encounterTime;
        party.increaseQuestTime(timePassed);
        paused = false;
        String time = Utils.convertToTimeStamp(party.timeSpent()) + " - ";
        String logText = time;
        logText += enemy + " " + readLine("defeated") + "\n";
        addToLog(logText);
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

    /**
     * randomEvent picks a random event from the pool of events
     */
    private void randomEvent() {
        int event = MathUtils.random(28) + 1;
        String eventString = "QUEST_EVENT_RANDOM_";
        eventString += Utils.convertToId(event);
        Retku retku = party.getRandomConsciousRetku();
        String eventLine = Utils.convertToTimeStamp(party.timeSpent()) + " - ";
        eventLine += retku.getName();
        eventLine += " " + readLine(eventString) + "\n";
        addToLog(eventLine);
        if (Utils.intArrayContains(damageEvents, event)) {
            retku.damageRetku(eventDamage);
            partyBar.updateHealthBars();
            if (!party.checkForConsciousness()) {
                failQuest();
            }
        } else if (Utils.intArrayContains(healEvents, event)) {
            retku.healRetku(eventHeal);
            partyBar.updateHealthBars();
        }
    }

    /**
     * Randomizer for combat.
     * @return random element for combat rolls
     */
    private int combatRandomizer() {
        int random = MathUtils.random(6);
        return random;
    }

    /**
     * failQuest runs the functions and elements of the failQuest
     */
    private void failQuest() {
        setQuestOver(true);
        QuestFailPopUp questFailPopUp = new QuestFailPopUp(party);
        questFailPopUp.show(getStage());
    }

    /**
     * rawLog holds the log of the quest in String format
     */
    private String rawLog;

    /**
     * adds text to rawLog and sets textLog to it
     * @param text text to add to the rawLog
     */
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
        events();
        if (!paused) {
            long timeLeft = party.timeLeft();
            String timerText = Utils.convertToTimeStamp(timeLeft);
            if (!party.isQuestEncounter()) {
                timer.setText(timerText);
                generateEncounterTimer();
            } else {
                timerToEncounter.setText(timerText);
                timer.setText("00:00:00");
            }
            updateSteps();
        }
    }

    /**
     * generates the encounterTimer Label
     */
    private void generateEncounterTimer() {
        long timeLeft = party.timeLeftToEncounter();
        String timerText = Utils.convertToTimeStamp(timeLeft);
        if (timeLeft < 0) timerText = "00:00:00";
        timerToEncounter.setText(timerText);
    }

    /**
     * updates the steps displayed on the screen
     */
    private void updateSteps() {
        String stepsString = readLine("steps") + ": " + party.getSteps();
        steps.setText(stepsString);
    }

    /**
     * override to update the healthbars of the party
     */
    @Override
    public void updateValues() {
        partyBar.updateHealthBars();
    }

    /**
     * setter for questOver
     * @param questOver new questOver value
     */
    public void setQuestOver(Boolean questOver) {
        this.questOver = questOver;
    }
}