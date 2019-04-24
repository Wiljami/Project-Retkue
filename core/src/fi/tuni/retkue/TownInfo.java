package fi.tuni.retkue;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import static fi.tuni.retkue.Item.Location.*;
import static fi.tuni.retkue.Quest.*;

public class TownInfo {
    private Quest[] availableQuests;
    private Party party;
    private ArrayList<Item> availableItems;
    private final float MAINQUESTCHANCE = 0.2f;
    private Quest[] questPool = {
            new Quest(1, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(2, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(3, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(4, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(5, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(6, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(7, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(8, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(9, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(10, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(11, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(12, new Reward(100), 60000L, QuestGiver.oldMan),
            new Quest(13, new Reward(100), 60000L, QuestGiver.oldMan),
    };

    private Quest[] mainQuestPool = {
            new Quest(1, new Reward(500), 1800000L, QuestGiver.oldMan, true),
            new Quest(2, new Reward(1000), 3600000L, QuestGiver.oldMan, true),
            new Quest(3, new Reward(1500), 7200000L, QuestGiver.oldMan, true),
            new Quest(4, new Reward(2000), 10800000L, QuestGiver.oldMan, true),
            new Quest(5, new Reward(2500), 14400000L, QuestGiver.oldMan, true),
            new Quest(6, new Reward(3000), 18000000L, QuestGiver.oldMan, true),
            new Quest(7, new Reward(3500), 27000000L, QuestGiver.oldMan, true),
            new Quest(8, new Reward(4000), 32400000L, QuestGiver.oldMan, true),
            new Quest(9, new Reward(5000), 36000000L, QuestGiver.oldMan, true),
    };

    private int chosenQuest = -1;
    public TownInfo(Party party) {
        this.party = party;
        availableQuests = new Quest[3];
        availableItems = new ArrayList<Item>();
    }

    public void newGame() {
        generateQuests();
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
        float random = MathUtils.random(1f);
        if (random < MAINQUESTCHANCE && party.getCurrentMainQuest() != mainQuestPool.length) {
            availableQuests[0] = mainQuestPool[party.getCurrentMainQuest()];
        }
    }

    public void loadQuestPool(int questSlot, int questId) {
        if (questId == -1) {
             rollNewQuests();
        } else {
            availableQuests[questSlot] = loadQuest(questId);
        }
    }

    public Quest loadQuest(int id) {
        if (id > 1000) {
            return mainQuestPool[id-1001];
        } else {
            return questPool[id - 1];
        }
    }

    private void generateItems() {
        for (int x = 1; x < 9; x++) {
            Item item = new Item();
            availableItems.add(item);
        }
    }

    public void rollNewQuests() {
        clearChosenQuest();
        generateQuests();
    }

    public void rollNewItems() {
        generateItems();
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

    public void addItemById(int id) {
        if (id != -1) {
            Item item = new Item(id, SHOP);
            availableItems.add(item);
        } else {
            rollNewItems();
        }
    }

    public int noItemsLeft() {
        return availableItems.size();
    }

    public Quest[] getAvailableQuests() {
        return availableQuests;
    }

    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }
}