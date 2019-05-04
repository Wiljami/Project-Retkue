package fi.tuni.retkue;

/**
 * Reward class is an object for holding the quest rewards within it.
 *
 * Currently only has gold rewards
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class Reward {
    /**
     * The amount of gold reward
     */
    public int gold;

    /**
     * Reward constructor
     * @param g the amount of gold reward is
     */
    public Reward(int g) {
        gold = g;
    }

    /**
     * Getter for the amount of gold the reward is
     * @return int gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Override for the toString to get our own text String. It uses the Scene's bundle reading to
     * get localized String.
     * @return String of the localized text.
     */
    @Override
    public String toString() {
        return gold + " " + Scene.readLine("golds");
    }
}
