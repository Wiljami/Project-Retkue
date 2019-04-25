package fi.tuni.retkue;


import com.badlogic.gdx.math.MathUtils;

/**
 * Quest class. This should hold information about the different quests in the game.
 * TODO: This thing
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Quest {
    private int id;
    private String titleKey = "";
    private String briefingKey = "";
    private String descriptionKey = "";
    private String acceptTextKey = "";
    private String completeTextKey = "";
    private String enemyKey = "";
    private Reward reward;
    private long questLength;
    private float difficulty;
    private QuestGiver questGiver;
    private Boolean mainQuest = false;

    enum QuestGiver {oldMan}

    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver) {
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
        setDifficulty(generateDifficulty());
        generateBundleKeys();
    }

    public Quest (int id, Reward reward, long questLength, QuestGiver questGiver, Boolean mainQuest) {
        this.mainQuest = mainQuest;
        this.id = id;
        this.reward = reward;
        this.questLength = questLength;
        this.questGiver = questGiver;
        this.difficulty = 1;
        addMainQuestTagToBundleKeys();
    }

    private float generateDifficulty() {
        float random = MathUtils.random(0.2f, 1f);
        return random;
    }

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

    private void addMainQuestTagToBundleKeys() {
        titleKey += "MAIN_";
        briefingKey += "MAIN_";
        acceptTextKey += "MAIN_";
        descriptionKey += "MAIN_";
        completeTextKey += "MAIN_";
        enemyKey += "MAIN_";
        generateBundleKeys();
    }

    public long getQuestLength() {
        return questLength;
    }

    public String getTitle() {
        return Utils.readBundle(Scene.getBundle(), titleKey);
    }

    public String getBriefing() {
        return Utils.readBundle(Scene.getBundle(), briefingKey);
    }

    public String getDescription() {
        return Utils.readBundle(Scene.getBundle(), descriptionKey);
    }

    public String getAcceptText() {
        return Utils.readBundle(Scene.getBundle(), acceptTextKey);
    }

    public String getCompleteText() {
        return Utils.readBundle(Scene.getBundle(), completeTextKey);
    }

    public Reward getReward() {
        return reward;
    }

    public int getId() {
        if (mainQuest) return id + 1000;
        return id;
    }

    public String getEnemyName() {
        return Utils.readBundle(Scene.getBundle(), enemyKey);
    }

    public Boolean getMainQuest() {
        return mainQuest;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
        if (!mainQuest) {
            questLength = (long)(questLength / difficulty);
            reward.gold = (int)(reward.gold / difficulty);
        }
    }
}