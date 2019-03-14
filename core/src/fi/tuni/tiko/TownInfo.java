package fi.tuni.tiko;

//TODO: make this part of the save or not? Either that or include this stuff to the config or party

public class TownInfo {
    Quest[] availableQuests;
    public TownInfo() {
        availableQuests = new Quest[3];
        availableQuests[0] = new Quest(1, new Reward(1), 10L, Quest.QuestGiver.oldMan);
        availableQuests[1] = new Quest(2, new Reward(1), 10L, Quest.QuestGiver.oldMan);
        availableQuests[2] = new Quest(3, new Reward(1), 10L, Quest.QuestGiver.oldMan);
    }

    public Quest findQuest(int n) {
        return availableQuests[n];
    }
}