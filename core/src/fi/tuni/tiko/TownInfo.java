package fi.tuni.tiko;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import static fi.tuni.tiko.Item.Slot.*;
import static fi.tuni.tiko.Item.Rarity.*;
import static fi.tuni.tiko.Item.Location.*;

//TODO: make this part of the save or not? Either that or include this stuff to the config or party

public class TownInfo {
    private Quest[] availableQuests;
    private ArrayList<Item> availableItems;
    private Quest[] questPool = {
            new Quest(1, new Reward(10), 100000L, Quest.QuestGiver.oldMan),
            new Quest(2, new Reward(20), 100000L, Quest.QuestGiver.oldMan),
            new Quest(3, new Reward(30), 100000L, Quest.QuestGiver.oldMan),
            new Quest(4, new Reward(40), 100000L, Quest.QuestGiver.oldMan),
            new Quest(5, new Reward(50), 100000L, Quest.QuestGiver.oldMan),
            new Quest(6, new Reward(60), 100000L, Quest.QuestGiver.oldMan),
            new Quest(7, new Reward(70), 100000L, Quest.QuestGiver.oldMan),
            new Quest(8, new Reward(80), 100000L, Quest.QuestGiver.oldMan),
            new Quest(9, new Reward(90), 100000L, Quest.QuestGiver.oldMan),
            new Quest(10, new Reward(100), 100000L, Quest.QuestGiver.oldMan),
            new Quest(11, new Reward(110), 100000L, Quest.QuestGiver.oldMan),
            new Quest(12, new Reward(120), 100000L, Quest.QuestGiver.oldMan),
            new Quest(13, new Reward(130), 100000L, Quest.QuestGiver.oldMan),
    };

    private int chosenQuest = -1;
    public TownInfo() {
        availableQuests = new Quest[3];
        generateQuests();
        availableItems = new ArrayList<Item>();
        generateItems();
    }

    private void generateQuests () {
        int quest1;
        int quest2;
        int quest3;
        int numberOfQuests = questPool.length;
        quest1 = MathUtils.random(numberOfQuests-1);
        do {
            quest2 = MathUtils.random(numberOfQuests-1);
        } while (quest2 == quest1);
        do {
            quest3 = MathUtils.random(numberOfQuests-1);
        } while (quest3 == quest1 || quest3 == quest2);
        availableQuests[0] = questPool[quest1];
        availableQuests[1] = questPool[quest2];
        availableQuests[2] = questPool[quest3];
    }

    private void generateItems() {
        for (int x = 1; x < 9; x++) {
            Item item = new Item(x, SHOP);
            availableItems.add(item);
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
        return availableItems.get(n);
    }

    public int getChosenQuest() {
        return chosenQuest;
    }

    public void clearChosenQuest() {
        chosenQuest = -1;
    }

    public void removeItem(Item item) {
        availableItems.remove(item);
    }

    public int noItemsLeft() {
        return availableItems.size();
    }
}