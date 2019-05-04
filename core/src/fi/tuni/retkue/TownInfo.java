package fi.tuni.retkue;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import static fi.tuni.retkue.Item.Location.*;
import static fi.tuni.retkue.Quest.*;

/**
 * TownInfo class contains information related to the town's status. The available quests and items
 * are stored here and functionality to sort out new ones is also here.
 *
 * TownInfo's variables are saved in SaveGame.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */

public class TownInfo {
    /**
     * Available quests within town
     */
    private Quest[] availableQuests;

    /**
     * Reference to the player party
     */
    private Party party;

    /**
     * Available items within town
     */
    private ArrayList<Item> availableItems;

    /**
     * Percentage chance that one of the quests is a main quest when a new pool is created.
     */
    private final float MAINQUESTCHANCE = 0.2f;

    /**
     * Pool of quests.
     */
    private Quest[] questPool = {
            new Quest(1, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(2, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(3, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(4, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(5, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(6, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(7, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(8, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(9, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(10, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(11, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(12, new Reward(100), 3600000L, QuestGiver.oldMan),
            new Quest(13, new Reward(100), 3600000L, QuestGiver.oldMan),
    };

    /**
     * Pool of main quests.
     */
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

    /**
     * chosenQuest is the id of the quest that has been chosen. If it is -1 then no quest is chosen.
     */
    private int chosenQuest = -1;

    /**
     * Constructor
     * @param party reference to the player party
     */
    public TownInfo(Party party) {
        this.party = party;
        availableQuests = new Quest[3];
        availableItems = new ArrayList<Item>();
    }

    /**
     * Method that controls what to do when new game is called.
     */
    public void newGame() {
        generateQuests();
        generateItems();
    }

    /**
     * generateQuests picks three quests from the quest pool randomly to fill it. It makes sure that
     * there are no duplicate quests and then randomizes wether one of the will be a main quest.
     */
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

    /**
     * loadQuestPool is a method for load functionality.
     * @param questSlot
     * @param questId
     */
    public void loadQuestPool(int questSlot, int questId) {
        if (questId == -1) {
             rollNewQuests();
        } else {
            availableQuests[questSlot] = loadQuest(questId);
        }
    }

    /**
     * loadQuest finds the quest from the pools with the id given. If the id is over 1000 then it is
     * a main quest. Otherwise it a normal quest.
     * @param id id of the quest
     * @return quest from the quest pool
     */
    public Quest loadQuest(int id) {
        if (id > 1000) {
            return mainQuestPool[id-1001];
        } else {
            return questPool[id - 1];
        }
    }

    /**
     * generateItems fills the item pool in the shop with random items
     */
    private void generateItems() {
        for (int x = 1; x < 9; x++) {
            Item item = new Item();
            availableItems.add(item);
        }
    }

    /**
     * rollNewQuests clears the pool of available quests and then fills the pool with a new set.
     */
    public void rollNewQuests() {
        clearChosenQuest();
        generateQuests();
    }

    /**
     * Method for telling the townInfo to generate new items
     */
    public void rollNewItems() {
        generateItems();
    }

    /**
     * Find a quest from the available pool depending on the slot number
     * @param n number of the slot
     * @return Quest requested
     */
    public Quest findQuest(int n) {
        return availableQuests[n];
    }

    /**
     * chooseQuest selects a quest and tells townInfo which quest it is within the available pool.
     * @param n the slot of the chosen quest
     */
    public void chooseQuest(int n) {
        chosenQuest = n;
    }

    /**
     * findChosenQuest returns the quest that has been chosen. If no quest is chosen, return null.
     * @return the chosen Quest, else null.
     */
    public Quest findChosenQuest() {
        if (chosenQuest == -1) return null;
        return findQuest(chosenQuest);
    }

    /**
     * findItem returns the Item from the pool of available items
     * @param n slot of the item
     * @return Item
     */
    public Item findItem(int n) {
        return availableItems.get(n);
    }

    /**
     * clearChosenQuest clears the chosen quest by setting it to -1
     */
    public void clearChosenQuest() {
        chosenQuest = -1;
    }

    /**
     * removeItem removes an item from the available items in the shop
     * @param item Item to be removed
     */
    public void removeItem(Item item) {
        availableItems.remove(item);
    }

    /**
     * addItemById adds an item using the id. This is used when a game is loaded.
     * @param id id of the item
     */
    public void addItemById(int id) {
        if (id != -1) {
            Item item = new Item(id, SHOP);
            availableItems.add(item);
        } else {
            rollNewItems();
        }
    }

    /**
     * Return the number of items left in store. As in size of availableItems array.
     * @return number of items left in store.
     */
    public int numberOfItemsLeft() {
        return availableItems.size();
    }

    /**
     * getAvailableQuests returns all the available quests in town
     * @return array of Quest
     */
    public Quest[] getAvailableQuests() {
        return availableQuests;
    }

    /**
     * getAvailableItems returns all the available items in town
     * @return ArrayList of Item
     */
    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }
}