package fi.tuni.tiko;

/**
 * Retku class holds information and functionality of a Retku.
 *
 * A retku is a character element in the game. A party is formed of three Retkus.
 *
 * @author Viljami Pietarila
 * @version 2019.0311
 */
public class Retku {
    /**
     * Name of the Retku
     */
    private String name;

    /**
     * Max health of Retku
     */
    private int maxHealth;

    /**
     * Current Health of Retku
     */
    private int currHealth;

    private AnimatedActor portrait;

    private Item slotA;

    private Item slotB;

    private Item slotC;

    private Party party;

    private int attack = 0;
    private int defence = 0;

    private final int BASESTAT = 10;

    /**
     * Helper array that holds the info of different Retkus using RetkuInfo class.
     */
    private RetkuInfo[] retkuInfos = {
            new RetkuInfo("Bill", "bill_sprite_sheet.png",
                    "bill_sprite_sheet_damage.png", 6, 1/2f, 1f),
            new RetkuInfo("Mei", "mei_sprite_sheet.png",
                    "mei_sprite_sheet_damage.png", 17, 1/4f, 4f/5f),
            new RetkuInfo("Mik'ed", "miked_sprite_sheet.png",
                    "miked_sprite_sheet_damage.png", 4, 1f, 4f/5f)
    };

    /**
     * Helper class that holds information of different Retkus
     */
    class RetkuInfo {
        public String name;
        public String animationFile;
        public String damagedFile;
        public int animationLength;
        public float animationSpeed;
        public float widthMultiplier;
        public RetkuInfo(String name, String animationFile, String damagedFile, int animationLength,
                         float animationSpeed, float widthMultiplier) {
            this.name = name;
            this.animationFile = animationFile;
            this.damagedFile = damagedFile;
            this.animationLength = animationLength;
            this.animationSpeed = animationSpeed;
            this.widthMultiplier = widthMultiplier;
        }
    }

    /**
     * Retku constructor
     * @param health max Health of Retku
     */
    public Retku(int id, int health, Party party) {
        setMaxHealth(100);
        setCurrHealth(health);
        RetkuInfo retkuInfo = retkuInfos[id];
        setName(retkuInfo.name);
        initPortrait(retkuInfo.animationFile, retkuInfo.damagedFile, retkuInfo.animationLength,
                retkuInfo.animationSpeed, retkuInfo.widthMultiplier);
        this.party = party;
        recalculateStats();
    }

    /**
     * get MaxHealth
     * @return maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * set maxHealth
     * @param maxHealth new maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * get currHealth
     * @return currHealth
     */
    public int getCurrHealth() {
        return currHealth;
    }

    /**
     * set currHealth
     * @param currHealth currHealth
     */
    public void setCurrHealth(int currHealth) {
        if (currHealth > maxHealth) currHealth = maxHealth;
        if (currHealth < 0) currHealth = 0;
        this.currHealth = currHealth;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get healthPercentage of the Retku
     * @return float between 0f and 1.0f as percentage
     */
    public float healthPercentage() {
        float healthPercentage = (float)getCurrHealth() / (float)getMaxHealth();
        return healthPercentage;
    }

    /**
     * damageRetku reduces the Retku's currHealth by the damage amount
     * @param damage damage done
     */
    public void damageRetku(int damage) {
        setCurrHealth(getCurrHealth() - damage);
        checkHealthForPortrait();
    }

    public String getRetkuDesc() {
        String key = name + "_desc";
        String desc = Utils.readBundle(Scene.getBundle(), key);
        return desc;
    }

    public String getRetkuAsTarget() {
        String key = name + "_target";
        String target = Utils.readBundle(Scene.getBundle(), key);
        return target;
    }

    /**
     * healRetku increases the Retku's currHealth by the heal amount
     * @param heal heal amount
     */
    public void healRetku(int heal) {
        setCurrHealth(getCurrHealth() + heal);
        checkHealthForPortrait();
    }

    public void initPortrait(String animationFile, String damagedFile, int cols, float speed,
                             float widthMultiplier) {
        float charSize = Main.WORLDPIXELHEIGHT*(1f/7f);
        portrait = new AnimatedActor(animationFile, cols , 1, speed,
                charSize*widthMultiplier, charSize);
        portrait.initateWoundedAnimation(damagedFile);
    }

    public void equipItem(Item item) {
        switch(item.getSlot()) {
            case TOOL: equipTool(item); break;
            case GARB: equipGarb(item); break;
            case TRINKET: equipTrinket(item); break;
        }
        recalculateStats();
    }

    private void equipTool(Item item) {
        if (slotA != null) {
            party.addItem(slotA);
        }
        slotA = item;
    }

    private void equipGarb(Item item) {
        if (slotB != null) {
            party.addItem(slotB);
        }
        slotB = item;
    }

    private void equipTrinket(Item item) {
        if (slotC != null) {
            party.addItem(slotC);
        }
        slotC = item;
    }

    public Item getSlotA() {
        return slotA;
    }

    public void setSlotA(Item slotA) {
        this.slotA = slotA;
    }

    public Item getSlotB() {
        return slotB;
    }

    public void setSlotB(Item slotB) {
        this.slotB = slotB;
    }

    public Item getSlotC() {
        return slotC;
    }

    public void setSlotC(Item slotC) {
        this.slotC = slotC;
    }

    public void removeItem(Item item) {
        Item.Slot slot = item.getSlot();
        switch (slot) {
            case TOOL: setSlotA(null);
            case GARB: setSlotB(null);
            case TRINKET: setSlotC(null);
        }
        recalculateStats();
    }

    public void giveItemById(int id, int retkuId) {
        if (id != 0) {
            Item item = new Item(id, findRetkuLocation(retkuId));
            equipItem(item);
        }
    }

    private void recalculateStats() {
        if (slotA != null) {
            attack = BASESTAT + slotA.getEffect();
        } else {
            attack = BASESTAT;
        }
        if (slotB != null) {
            defence = BASESTAT + slotB.getEffect();
        } else {
            defence = BASESTAT;
        }
    }

    private Item.Location findRetkuLocation(int n) {
        Item.Location location = Item.Location.OTHER;
        switch (n) {
            case (0): location = Item.Location.RETKUA; break;
            case (1): location = Item.Location.RETKUB; break;
            case (2): location = Item.Location.RETKUC; break;
        }
        return location;
    }

    private void checkHealthForPortrait() {
        if (currHealth < 50) {
            portrait.setWoundedAnimation();
        } else {
            portrait.setNormalAnimation();
        }

    }

    public AnimatedActor getPortrait() {
        checkHealthForPortrait();
        return portrait;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public boolean isConscious() {
        return currHealth > 0;
    }
}