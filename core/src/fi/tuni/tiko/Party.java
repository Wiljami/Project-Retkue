package fi.tuni.tiko;

import java.util.ArrayList;

/**
 * Party is a class that will hold the functions and information of the player's group
 * It holds the resources the player has and the information of different Retkus.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Party {
    /**
     * Array of the Retkus in the party
     */
    private Retku[] retkus;

    /**
     * Array of the items in the party
     * TODO: Add this to SAVE AND LOAD
     */
    private ArrayList<Item> inventory;

    private static int inventorySize = 10;

    /**
     * Player gold
     */
    private int gold;

    /**
     * The chosen quest
     */
    private Quest quest;

    /**
     * Player steps
     */
    private int steps;

    //TODO: These values need some thinking through
    /**
     * The amount of steps per conversion
     */
    private static final int STEPSTOGOLD = 100;
    /**
     * The amount of gold gained per conversion
     */
    private static final int GOLDFROMSTEPS = 5;

    /**
     * Reference to main
     */
    private Main main;

    //TODO: Save
    private Quest currentQuest;

    //TODO: Save
    private long questStarted;

    //TODO: Save
    private long questLeft;

    /**
     * Party constructor. atm has dummy values for the retkus and gold and steps
     * TODO: Change this.
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
    }

    /**
     * Spend n amount of gold
     * @param n gold amount
     */
    public void spendGold(int n) {
        setGold(getGold() - n);
    }

    public void earnGold(int n) {
        setGold(getGold() + n);
    }

    /**
     * Convert steps to gold. If the conversion is not possible, then return false.
     * @return boolean wether the conversion was possible.
     */
    public boolean convert() {
        if (getSteps() < STEPSTOGOLD) {
            return false;
        }
        setSteps(getSteps() - STEPSTOGOLD);
        setGold(getGold() + GOLDFROMSTEPS);
        return true;
    }

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

    private void notifyWatchers() {
        if (main.getCurrentScene() != null) main.getCurrentScene().updateValues();
    }

    /**
     * newGame sets the starting values to the party.
     */
    public void newGame() {
        retkus[0] = new Retku("Bill", 100, "bill_c.png", this);
        retkus[1] = new Retku("Mei", 100, "mei.png", this);
        retkus[2] = new Retku("Mik'ed", 100, "horze_white.png", this);
        steps = 1000;
        gold = 99;
    }

    public void addRetku(Retku retku, int slot) {
        retkus[slot] = retku;
    }

    public void beginQuest() {
        questStarted = System.currentTimeMillis();
    }

    public long timeLeft() {
        return questStarted - System.currentTimeMillis() + getQuestLeft();
    }

    public void boostQuest() {
        long temp = timeLeft()/2 + System.currentTimeMillis() - questStarted;
        setQuestLeft(temp);
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
        setQuestLeft(currentQuest.getQuestLength());
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public void setQuestLeft(long questLeft) {
        this.questLeft = questLeft;
    }

    public long getQuestLeft() {
        return questLeft;
    }

    public long getQuestStarted() {
        return questStarted;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public boolean isThereInventorySpace() {
        if (inventory.size() >= inventorySize) {
            return false;
        } else {
            return true;
        }
    }

    public void healParty() {
        for (Retku retku : retkus) {
            retku.healRetku(100);
        }
    }

    public void sellItem(Item item) {
        earnGold(item.getPrice()/2);
        removeItem(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}