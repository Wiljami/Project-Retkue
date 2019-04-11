package fi.tuni.tiko;

public class Reward {
    public int gold;
    public Reward(int g) {
        gold = g;
    }

    public int getGold() {
        return gold;
    }

    @Override
    public String toString() {
        return gold + " " + Scene.readLine("golds");
    }
}
