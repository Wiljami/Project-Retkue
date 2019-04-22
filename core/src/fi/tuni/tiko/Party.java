package fi.tuni.tiko;

import com.badlogic.gdx.math.MathUtils;

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
     *
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

    private int currentMainQuest = 0;

    /**
     * Reference to main
     */
    private Main main;

    private Quest currentQuest;

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

    public void earnGold(int n) {
        setGold(getGold() + n);
    }

    /**
     * Convert steps to gold. If the conversion is not possible, then return false.
     * @return boolean wether the conversion was possible.
     */
    public void convert() {
        setSteps(getSteps() - getConvCost());
        setGold(getGold() + CONVGOLD);
        convCost = convCost * 2;
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
        retkus[0] = new Retku(0, 100,this);
        retkus[1] = new Retku(1, 100, this);
        retkus[2] = new Retku(2, 100,this);
        gold = 1000;
    }

    public void loadRetku(Retku retku, int slot) {
         retkus[slot] = retku;
    }

    public void beginQuest() {
        questStarted = System.currentTimeMillis();
    }

    public long timeLeft() {
        return questStarted - System.currentTimeMillis() + getQuestLeft();
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

    public void resetCosts() {
        healCost = 10;
        fasterCost = 10;
        convCost = 100;
    }

    private int fasterCost = 10;
    private int healCost = 10;

    public int getHealCost() {
        return healCost;
    }

    public void healParty() {
        for (Retku retku : retkus) {
            retku.healRetku(20);
        }
        spendSteps(healCost);
        healCost = healCost * 2;
    }

    public boolean canAffordToHeal() {
        if (getSteps() > getHealCost()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canAffordToRest() {
        if (getGold() > RESTCOST) {
            return true;
        } else {
            return false;
        }
    }

    private int convCost = 100;

    public int getConvGold() {
        return CONVGOLD;
    }

    public int getConvCost() {
        return convCost;
    }

    public boolean canAffordToConvert() {
        if (getSteps() > getConvCost()) {
            return true;
        } else {
            return false;
        }
    }

    public int getFasterCost() {
        return fasterCost;
    }

    public void fasterQuest() {
        spendSteps(fasterCost);
        long temp = (long) (timeLeft()/1.1 + System.currentTimeMillis() - questStarted);
        setQuestLeft(temp);
        fasterCost = fasterCost * 2;
    }

    public boolean canAffordToFaster() {
        if (getSteps() > getFasterCost()) {
            return true;
        } else {
            return false;
        }
    }

    public void setFasterCost(int fasterCost) {
        this.fasterCost = fasterCost;
    }

    public void setHealCost(int healCost) {
        this.healCost = healCost;
    }

    public void setConvCost(int convCost) {
        this.convCost = convCost;
    }

    public void restParty() {
        for (Retku retku : retkus) {
            retku.healRetku(200);
        }
        spendGold(RESTCOST);
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

    public static int getInventorySize() {
        return inventorySize;
    }

    public void addItemById(int itemId) {
        Item item = new Item(itemId, Item.Location.PARTY);
        addItem(item);
    }



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
     * Check if there is a Retku with at least some health.
     * As long as any Retku has at least 1 health, return true. Otherwise return false.
     * @return boolean if whether there is healthy Retkus left.
     */
    public boolean checkForConsciousness() {
        if (retkus[0].getCurrHealth() != 0) return true;
        if (retkus[1].getCurrHealth() != 0) return true;
        if (retkus[2].getCurrHealth() != 0) return true;
        return false;
    }

    public long timeSpent() {
        return System.currentTimeMillis() - questStarted;
    }

    public int getCurrentMainQuest() {
        return currentMainQuest;
    }

    public void setCurrentMainQuest(int currentMainQuest) {
        this.currentMainQuest = currentMainQuest;
    }

    public boolean isOnQuest() {
        return onQuest;
    }

    public void setOnQuest(boolean onQuest) {
        this.onQuest = onQuest;
    }

    public void setQuestStarted(long questStarted) {
        this.questStarted = questStarted;
    }
}