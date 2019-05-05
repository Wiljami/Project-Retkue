package fi.tuni.retkue;


import com.badlogic.gdx.math.MathUtils;

/**
 * Quest class. This should hold information about the different quests in the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class Quest {
    /**
     * id of the quest
     */
    private int id;

    /**
     * localization key for title
     */
    private String titleKey = "";

    /**
     * localization key for briefing
     */
    private String briefingKey = "";

    /**
     * localization key for description
     */
    private String descriptionKey = "";

    /**
     * localization key for accepting quest
     */
    private String acceptTextKey = "";

    /**
     * localization key for completing quest
     */
    private String completeTextKey = "";

    /**
     * localization key for the quest enemy
     */
    private String enemyKey = "";

    /**
     * Reward of the quest
     */
    private Reward reward;

    /**
     * Length of the quest in ms
     */
    private long questLength;

    /**
     * randomized difficulty of the quest
     */
    private float difficulty;

    /**
     * Questgiver of the quest
     */
    private QuestGiver questGiver;

    /**
     * Wether the quest is a mainQuest
     */
    private Boolean mainQuest = false;

    /**
     * enum QuestGiver, currently only oldMan
     */
    enum QuestGiver {oldMan}

    /**
     * Quest constructor
     * @param id id of the quest
     * @param reward Reward of the quest
     * @param questLength length of the quest
     * @param questGiver who gives the quest
     */
    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver) {
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
        setDifficulty(generateDifficulty());
        generateBundleKeys();
    }

    /**
     * Quest constructor when it is a mainQuest
     * @param id id of the quest
     * @param reward Reward of the quest
     * @param questLength length of the quest
     * @param questGiver who gives the quest
     * @param mainQuest boolean of being mainQuest
     */
    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver, Boolean mainQuest) {
        this.mainQuest = mainQuest;
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
        this.difficulty = 1;
        addMainQuestTagToBundleKeys();
    }

    /**
     * Randomizes a difficulty between two values
     * @return float of the difficulty
     */
    private float generateDifficulty() {
        float random = MathUtils.random(0.2f, 2f);
        return random;
    }

    /**
     * generateBundleKeys creates the keys from the quest id
     */
    private void generateBundleKeys() {
        String bundle_id = Utils.convertToId(id);
        String halfKey = "QUEST_" + bundle_id;

        titleKey += halfKey + "_TITLE";
        briefingKey += halfKey + "_TEXT";
        acceptTextKey += halfKey + "_ACCEPT";
        descriptionKey += halfKey + "_DESC";
        completeTextKey += halfKey + "_COMPLETE";
        enemyKey += halfKey + "_ENEMY";
    }

    /**
     * Adds the mainQuest tag to the bundlekeys
     */
    private void addMainQuestTagToBundleKeys() {
        titleKey += "MAIN_";
        briefingKey += "MAIN_";
        acceptTextKey += "MAIN_";
        descriptionKey += "MAIN_";
        completeTextKey += "MAIN_";
        enemyKey += "MAIN_";
        generateBundleKeys();
    }

    /**
     * getter for questLength
     * @return questLength
     */
    public long getQuestLength() {
        return questLength;
    }

    /**
     * returns localization for the title
     * @return localized title String
     */
    public String getTitle() {
        return Utils.readBundle(Scene.getBundle(), titleKey);
    }

    /**
     * returns localization for the briefing
     * @return localized briefing String
     */
    public String getBriefing() {
        return Utils.readBundle(Scene.getBundle(), briefingKey);
    }

    /**
     * returns localization for the description
     * @return localized description String
     */
    public String getDescription() {
        return Utils.readBundle(Scene.getBundle(), descriptionKey);
    }

    /**
     * returns localization for the acceptText
     * @return localized acceptText String
     */
    public String getAcceptText() {
        return Utils.readBundle(Scene.getBundle(), acceptTextKey);
    }

    /**
     * returns localization for the completeText
     * @return localized completeText String
     */
    public String getCompleteText() {
        return Utils.readBundle(Scene.getBundle(), completeTextKey);
    }

    /**
     * getter for Reward
     * @return Reward
     */
    public Reward getReward() {
        return reward;
    }

    /**
     * getter for Id
     * @return quest id
     */
    public int getId() {
        if (mainQuest) return id + 1000;
        return id;
    }

    /**
     * returns localization for the quest enemy
     * @return localized quest enemy String
     */
    public String getEnemyName() {
        return Utils.readBundle(Scene.getBundle(), enemyKey);
    }

    /**
     * returns wether this quest is a mainQuest
     * @return isMainQuest
     */
    public Boolean isMainQuest() {
        return mainQuest;
    }

    /**
     * returns the difficulty of the quest
     * @return float difficulty
     */
    public float getDifficulty() {
        return difficulty;
    }

    /**
     * adjusts the parameters of the quest based on the difficulty of the quest
     * @param difficulty float difficulty
     */
    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
        if (!mainQuest) {
            questLength = (long)(questLength / difficulty);
            reward.gold = (int)(reward.gold / difficulty);
        }
    }
}