package fi.tuni.tiko;

import static fi.tuni.tiko.Item.Slot.*;
import static fi.tuni.tiko.Item.Rarity.*;

//TODO: make this part of the save or not? Either that or include this stuff to the config or party

public class TownInfo {
    private Quest[] availableQuests;
    private Item[] availableItems;

    private int chosenQuest = -1;
    public TownInfo() {
        availableQuests = new Quest[3];
        availableQuests[0] = new Quest(1, new Reward(1), 100000L, Quest.QuestGiver.oldMan);
        availableQuests[1] = new Quest(2, new Reward(1), 100000L, Quest.QuestGiver.oldMan);
        availableQuests[2] = new Quest(3, new Reward(1), 100000L, Quest.QuestGiver.oldMan);
        availableItems = new Item[10];
        generateItems();
    }

    private void generateItems() {
        for (int x = 0; x < 10; x++) {
            Item item = new Item(x, 1, 2, TRINKET, UNCOMMON, x*100);
            availableItems[x] = item;
        }
    }

    public Quest findQuest(int n) {
        return availableQuests[n];
    }

    public void chooseQuest(int n) {
        chosenQuest = n;
    }

    public Quest findChosenQuest() {
        if (chosenQuest == -1) return null;
        return findQuest(chosenQuest);
    }

    public Item findItem(int n) {
        return availableItems[n];
    }

    public int getChosenQuest() {
        return chosenQuest;
    }

    public void clearChosenQuest() {
        chosenQuest = -1;
    }
}