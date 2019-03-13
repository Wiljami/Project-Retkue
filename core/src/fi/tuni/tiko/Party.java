package fi.tuni.tiko;

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
     * Player gold
     */
    private int gold;
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

    /**
     * Party constructor. atm has dummy values for the retkus and gold and steps
     * TODO: Change this.
     */
    public Party(Main main) {
        this.main = main;
        retkus = new Retku[3];
        retkus[0] = new Retku("Bill", 100);
        retkus[1] = new Retku("Mik'ed", 100);
        retkus[2] = new Retku("Mei", 100);
        steps = 1000;
        gold = 99;
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
        main.getCurrentScene().updateValues();
    }
}