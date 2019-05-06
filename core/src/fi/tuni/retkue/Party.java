package fi.tuni.retkue;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Party is a class that will hold the functions and information of the player's group
 * It holds the resources the player has and the information of different Retkus.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class Party {
    /**
     * Array of the Retkus in the party
     */
    private Retku[] retkus;

    /**
     * Array of the items in the party
     */
    private ArrayList<Item> inventory;

    /**
     * Max size of the party inventory
     */
    private static int MAXINVENTORYSIZE = 10;

    /**
     * Player gold
     */
    private int gold;

    /**
     * The chosen quest
     */
    private Quest quest;

    /**
     * wether the party is on a quest
     */
    private boolean onQuest = false;

    /**
     * Player steps
     */
    private int steps;

    /**
     * The amount of gold resting in the inn costs
     */
    private static final int RESTCOST = 100;

    /**
     * The amount of gold you gain from one step to gold conversion
     */
    private static final int CONVGOLD = 50;

    /**
     * Current mainQuest step
     */
    private int currentMainQuest = 0;

    /**
     * Reference to main
     */
    private Main main;

    /**
     * Reference to the current Quest
     */
    private Quest currentQuest;

    /**
     * Time when the Quest was started
     */
    private long questStarted;

    private long questLeft;

    /**
     * Party constructor. atm has dummy values for the retkus and gold and steps
     */
    public Party(Main main) {
        inventory = new ArrayList<Item>();
        this.main = main;
        retkus = new Retku[3];
    }

    /**
     * Find and return the Retku at spot n
     * @param n the spot we wish to check
     * @return Retku of that position
     */
    public Retku findRetku(int n) {
        return retkus[n];
    }

    /**
     * Player gold
     * @return gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Player steps
     * @return steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Set gold. Does not allow negative gold.
     * @param gold new gold value
     */
    public void setGold(int gold) {
        this.gold = gold;
        if(this.gold < 0) {
            throw new IllegalArgumentException("gold can't be smaller than 0.");
        }
        notifyWatchers();
    }

    /**
     * Spend n amount of steps
     * @param n step amount
     */
    public void spendSteps(int n) {
        setSteps(getSteps() - n);
    }

    /**
     * Spend n amount of gold
     * @param n gold amount
     */
    public void spendGold(int n) {
        setGold(getGold() - n);
    }

    /**
     * Increate the party gold by n
     * @param n the amount of gold added
     */
    public void earnGold(int n) {
        setGold(getGold() + n);
    }

    /**
     * Convert steps to gold. If the conversion is not possible, then return false.
     */
    public void convert() {
        setSteps(getSteps() - getConvCost());
        setGold(getGold() + CONVGOLD);
        convCost = convCost * 2;
    }

    /**
     * Add an Item to the inventory
     * @param item Item to be added
     */
    public void addItem(Item item) {
        inventory.add(item);
        item.setLocation(Item.Location.PARTY);
    }

    /**
     * addSteps. Adds a number of steps to the total steps
     * @param s int steps to be added to the total
     */
    public void addSteps(int s) {
        setSteps(getSteps() + s);
    }

    /**
     * steps setter
     * @param steps new steps value
     */
    public void setSteps(int steps) {
        if (steps < 0) {
            throw new IllegalArgumentException("steps can't be smaller than 0.");
        }
        this.steps = steps;
        notifyWatchers();
    }

    /**
     * notifyWatchers tells current Scene to update things.
     */
    private void notifyWatchers() {
        if (main.getCurrentScene() != null) main.getCurrentScene().updateValues();
    }

    /**
     * newGame sets the starting values to the party.
     */
    public void newGame() {
        retkus[0] = new Retku(0, 100,this);
        retkus[1] = new Retku(1, 100, this);
        retkus[2] = new Retku(2, 100,this);
        gold = 100;
    }

    /**
     * loadRetku is used when loading a save
     * @param retku Retku to be loaded
     * @param slot slot in the party
     */
    public void loadRetku(Retku retku, int slot) {
         retkus[slot] = retku;
    }

    /**
     * beginQuest sets questStarted timer to the current system time and sets the quest started
     */
    public void beginQuest() {
        setOnQuest(true);
        questStarted = System.currentTimeMillis();
    }

    /**
     * Returns the time left on the current quest based on the quest started time
     * @return long questStarted - current time + questLeft
     */
    public long timeLeft() {
        return questStarted - System.currentTimeMillis() + getQuestLeft();
    }

    /**
     * sets currentQuest
     * @param currentQuest Quest
     */
    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
        setQuestLeft(currentQuest.getQuestLength());
    }

    /**
     * gets currentQuest
     * @return Quest
     */
    public Quest getCurrentQuest() {
        return currentQuest;
    }

    /**
     * setQuestLeft
     * @param questLeft time in ms
     */
    public void setQuestLeft(long questLeft) {
        this.questLeft = questLeft;
    }

    /**
     * getQuestLeft
     * @return questLeft
     */
    public long getQuestLeft() {
        return questLeft;
    }

    /**
     * get questStarted
     * @return questStarted
     */
    public long getQuestStarted() {
        return questStarted;
    }

    /**
     * get Inventory
     * @return inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * is there space left in the inventory
     * @return boolean wether there is space left in the inventory
     */
    public boolean isThereInventorySpace() {
        return inventory.size() < MAXINVENTORYSIZE;
    }

    /**
     * Resets different costs for actions
     */
    public void resetCosts() {
        setOnQuest(false);
        healCost = HEALDEFAULT;
        fasterCost = FASTERDEFAULT;
        convCost = CONVDEFAULT;
    }

    /**
     * Default base cost for faster action
     */
    private final int FASTERDEFAULT = 50;

    /**
     * Default base cost for heal action
     */
    private final int HEALDEFAULT = 50;

    /**
     * Default base cost for converting steps to gold
     */
    private final int CONVDEFAULT = 100;

    /**
     * fasterCost is the cost of speeding your quest.
     */
    private int fasterCost = FASTERDEFAULT;

    /**
     * healCost is the cost of healing your party.
     */
    private int healCost = HEALDEFAULT;

    /**
     * get healCost
     * @return healCost
     */
    public int getHealCost() {
        return healCost;
    }

    /**
     * Default amount healed with heal action
     */
    private final int HEALAMOUNT = 20;

    /**
     * Heals party for a set amount
     */
    public void healParty() {
        for (Retku retku : retkus) {
            retku.healRetku(HEALAMOUNT);
        }
        spendSteps(healCost);
        healCost = healCost * 2;
    }

    /**
     * Wether player can afford to heal
     * @return boolean if you can afford to heal
     */
    public boolean canAffordToHeal() {
        return getSteps() > getHealCost();
    }

    /**
     * Wether play can afford to rest
     * @return boolean if you can afford to rest
     */
    public boolean canAffordToRest() {
        return getGold() > RESTCOST;
    }

    /**
     * convCost is the cost of converting steps to gold
     */
    private int convCost = CONVDEFAULT;

    /**
     * getConvGold
     * @return CONVGOLD
     */
    public int getConvGold() {
        return CONVGOLD;
    }

    /**
     * get convCost
     * @return convCost
     */
    public int getConvCost() {
        return convCost;
    }

    /**
     * Wether the player can afford to convert steps to gold
     * @return boolean wether you can afford to convert steps to gold
     */
    public boolean canAffordToConvert() {
        return getSteps() > getConvCost();
    }

    /**
     * get fasterCost
     * @return fasterCost
     */
    public int getFasterCost() {
        return fasterCost;
    }

    /**
     * fasterQuest shortens the time of the quest by 10%
     */
    public void fasterQuest() {
        spendSteps(fasterCost);
        long temp = (long) (timeLeft()/1.1 + System.currentTimeMillis() - questStarted);
        fasterEncounter();
        setQuestLeft(temp);
        fasterCost = fasterCost * 2;
    }

    /**
     * Time left to the encounter
     */
    private long timeToEncounter;

    /**
     * fasterEncounter shortens the time to the encounter
     */
    private void fasterEncounter() {
        if (timeToEncounter > 0) {
            long temp = (long) (timeLeftToEncounter()/1.1 + System.currentTimeMillis() - questStarted);
            setTimeToEncounter(temp);
        }
    }

    /**
     * Wether the player can afford to go faster
     * @return boolean if you can afford to go faster
     */
    public boolean canAffordToFaster() {
        return getSteps() > getFasterCost();
    }

    /**
     * Setter for fasterCost
     * @param fasterCost int new fasterCost
     */
    public void setFasterCost(int fasterCost) {
        this.fasterCost = fasterCost;
    }

    /**
     * Setter for healCost
     * @param healCost int new healCost
     */
    public void setHealCost(int healCost) {
        this.healCost = healCost;
    }

    /**
     * Setter for convCost
     * @param convCost int new convCost
     */
    public void setConvCost(int convCost) {
        this.convCost = convCost;
    }

    /**
     * restParty heals the party
     */
    public void restParty() {
        for (Retku retku : retkus) {
            retku.healRetku(200);
        }
        spendGold(RESTCOST);
    }

    /**
     * sellItem removes an item from the inventory and returns half its value in gold
     * @param item Item to be sold
     */
    public void sellItem(Item item) {
        earnGold(item.getPrice()/2);
        removeItem(item);
    }

    /**
     * removes Item item from the inventory
     * @param item Item to be removed
     */
    public void removeItem(Item item) {
        inventory.remove(item);
    }

    /**
     * getter for Quest
     * @return Quest
     */
    public Quest getQuest() {
        return quest;
    }

    /**
     * setter for Quest
     * @param quest Quest
     */
    public void setQuest(Quest quest) {
        this.quest = quest;
        setCurrentQuest(quest);
        timeToEncounter = quest.getQuestLength()/2;
        questEncounter = false;
    }

    /**
     * getter for MAXINVENTORYSIZE
     * @return MAXINVENTORYSIZE
     */
    public static int getMAXINVENTORYSIZE() {
        return MAXINVENTORYSIZE;
    }

    /**
     * Add item by id. This is used when loading a game
     * @param itemId id of the item
     */
    public void addItemById(int itemId) {
        Item item = new Item(itemId, Item.Location.PARTY);
        addItem(item);
    }

    /**
     * Return a member of the party that is still conscious
     * @return random conscious Retku
     */
    public Retku getRandomConsciousRetku() {
        boolean foundOk = false;
        int random = 0;
        while (!foundOk) {
            random = MathUtils.random(2);
            if (retkus[random].getCurrHealth() != 0) {
                foundOk = true;
            }
        }
        return retkus[random];
    }

    /**
     * How much time is left to the encounter
     * @return questStarted - currentTime + timeToEncounter
     */
    public long timeLeftToEncounter() {
        return questStarted - System.currentTimeMillis() + getTimeToEncounter();
    }

    /**
     * get time to the encounter, if it is under 0, then return 0
     * @return timeToEncounter or 0
     */
    public long getTimeToEncounter() {
        if (timeToEncounter < 0) return 0;
        return timeToEncounter;
    }

    /**
     * setter timeToEncounter
     * @param timeToEncounter new timeToEncounter
     */
    public void setTimeToEncounter(long timeToEncounter) {
        this.timeToEncounter = timeToEncounter;
    }

    /**
     * Check if there is a Retku with at least some health.
     * As long as any Retku has at least 1 health, return true. Otherwise return false.
     * @return boolean if whether there is healthy Retkus left.
     */
    public boolean checkForConsciousness() {
        if (retkus[0].getCurrHealth() != 0) return true;
        if (retkus[1].getCurrHealth() != 0) return true;
        return retkus[2].getCurrHealth() != 0;
    }

    /**
     * boolean wether the party is in an encounter
     */
    private boolean questEncounter;

    /**
     * isQuestEncounter
     * @return questEncounter
     */
    public boolean isQuestEncounter() {
        return questEncounter;
    }

    /**
     * setter for the questEncounter
     * @param questEncounter new questEncounter
     */
    public void setQuestEncounter(boolean questEncounter) {
        this.questEncounter = questEncounter;
    }

    /**
     * timeSpent in the quest
     * @return current time - questStarted
     */
    public long timeSpent() {
        return System.currentTimeMillis() - questStarted;
    }

    /**
     * Getter for current step of the mainQuest
     * @return currentMainQuest
     */
    public int getCurrentMainQuest() {
        return currentMainQuest;
    }

    /**
     * setter for currentMainQuest
     * @param currentMainQuest currentMainQuest
     */
    public void setCurrentMainQuest(int currentMainQuest) {
        this.currentMainQuest = currentMainQuest;
    }

    /**
     * wether party is on quest
     * @return onQuest
     */
    public boolean isOnQuest() {
        return onQuest;
    }

    /**
     * setter for onQuest
     * @param onQuest new onQuest
     */
    public void setOnQuest(boolean onQuest) {
        this.onQuest = onQuest;
    }

    /**
     * setter for questStarted
     * @param questStarted questStarted
     */
    public void setQuestStarted(long questStarted) {
        this.questStarted = questStarted;
    }

    /**
     * incraseQuestTime increases quest time by time
     * @param time time to increase the questTime
     */
    public void increaseQuestTime(long time) {
        setQuestLeft(getQuestLeft() + time);
    }

    /**
     * completedMainQuest increases the currentMainQuest step by 1
     */
    public void completedMainQuest() {
        setCurrentMainQuest(getCurrentMainQuest() + 1);
    }
}