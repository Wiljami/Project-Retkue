package fi.tuni.retkue;

/**
 * Retku class holds information and functionality of a Retku.
 *
 * A retku is a character element in the game. A party is formed of three Retkus.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
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

    /**
     * Animated actor portrait of the Retku
     */
    private AnimatedActor portrait;

    /**
     * Item in slotA
     */
    private Item slotA;

    /**
     * Item in slotB
     */
    private Item slotB;

    /**
     * Item in slotC
     */
    private Item slotC;

    /**
     * Reference to the player's party
     */
    private Party party;

    /**
     * Retku's attack value
     */
    private int attack = 0;

    /**
     * Retku's defense value
     */
    private int defence = 0;

    /**
     * Base value for both attack and defense that is used whenever calculating the stats
     */
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
        /**
         * Name of the Retku
         */
        public String name;
        /**
         * Animation filename
         */
        public String animationFile;
        /**
         * Damaged animation filename
         */
        public String damagedFile;
        /**
         * Length of the animation in frames
         */
        public int animationLength;
        /**
         * Speed of the animation
         */
        public float animationSpeed;
        /**
         * Width of the portrait
         */
        public float widthMultiplier;

        /**
         * Constructor for RetkuInfo
         * @param name String name
         * @param animationFile String animationFile
         * @param damagedFile String damagedFile
         * @param animationLength int animationLength
         * @param animationSpeed float animationSpeed
         * @param widthMultiplier float widthMultiplier
         */
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
     * @param id id of the Retku
     * @param health current health of the Retku
     * @param party party of the Retku
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

    /**
     * getRetkuDesc returns a localized text description of the Retku.
     * @return String, description of the Retku
     */
    public String getRetkuDesc() {
        String key = name + "_desc";
        String desc = Utils.readBundle(Scene.getBundle(), key);
        return desc;
    }

    /**
     * getRetkuAsTarget returns the localized form of the Retku being targeted in textlog
     * @return String, retku as target
     */
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

    /**
     * initPortrait initializes the portrait animation of the Retku
     * @param animationFile filename of the animation
     * @param damagedFile filename of the damaged animation
     * @param cols length of the animation in frames
     * @param speed speed of the animation
     * @param widthMultiplier width of the portrait
     */
    public void initPortrait(String animationFile, String damagedFile, int cols, float speed,
                             float widthMultiplier) {
        float charSize = Main.WORLDPIXELHEIGHT*(1f/7f);
        portrait = new AnimatedActor(animationFile, cols , 1, speed,
                charSize*widthMultiplier, charSize);
        portrait.initateWoundedAnimation(damagedFile);
    }

    /**
     * equipItem equips an Item for the retku.
     * @param item Item to be equipped.
     */
    public void equipItem(Item item) {
        switch(item.getSlot()) {
            case TOOL: equipTool(item); break;
            case GARB: equipGarb(item); break;
            case TRINKET: equipTrinket(item); break;
        }
        recalculateStats();
    }

    /**
     * equipTool equips a Tool(Weapon) to the retku
     * @param item Weapon to be equipped
     */
    private void equipTool(Item item) {
        if (slotA != null) {
            party.addItem(slotA);
        }
        slotA = item;
    }

    /**
     * equipGarb equips a Garb(Armor) to the Retku
     * @param item Armor to be equipped
     */
    private void equipGarb(Item item) {
        if (slotB != null) {
            party.addItem(slotB);
        }
        slotB = item;
    }

    /**
     * equipTrinket equips a trinket to the Retku
     * @param item Trinket to be equipped
     */
    private void equipTrinket(Item item) {
        if (slotC != null) {
            party.addItem(slotC);
        }
        slotC = item;
    }

    /**
     * return item in slotA
     * @return slotA Item
     */
    public Item getSlotA() {
        return slotA;
    }

    /**
     * set slotA to a specific item
     * @param slotA item to be equipped
     */
    public void setSlotA(Item slotA) {
        this.slotA = slotA;
    }
    /**
     * return item in slotB
     * @return slotB Item
     */
    public Item getSlotB() {
        return slotB;
    }

    /**
     * set slotB to a specific item
     * @param slotB item to be equipped
     */
    public void setSlotB(Item slotB) {
        this.slotB = slotB;
    }
    /**
     * return item in slotC
     * @return slotC Item
     */
    public Item getSlotC() {
        return slotC;
    }

    /**
     * set slotC to a specific item
     * @param slotC item to be equipped
     */
    public void setSlotC(Item slotC) {
        this.slotC = slotC;
    }

    /**
     * Remove a specific item from the Retku
     * @param item Item to be removed
     */
    public void removeItem(Item item) {
        Item.Slot slot = item.getSlot();
        switch (slot) {
            case TOOL: setSlotA(null);
            case GARB: setSlotB(null);
            case TRINKET: setSlotC(null);
        }
        recalculateStats();
    }

    /**
     * Give item by id to the Retku. This is used when the game is loaded.
     * @param id id of the item
     * @param retkuId id of the retku
     */
    public void giveItemById(int id, int retkuId) {
        if (id != 0) {
            Item item = new Item(id, findRetkuLocation(retkuId));
            equipItem(item);
        }
    }

    /**
     * recalculateStats method recalculates the stats of the Retku based on the items and base stat.
     */
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

    /**
     * findRetkuLocation solves the location of the item and returns it
     * @param n the id of the Retku
     * @return location of the item based on the id
     */
    private Item.Location findRetkuLocation(int n) {
        Item.Location location = Item.Location.OTHER;
        switch (n) {
            case (0): location = Item.Location.RETKUA; break;
            case (1): location = Item.Location.RETKUB; break;
            case (2): location = Item.Location.RETKUC; break;
        }
        return location;
    }

    /**
     * checkHealthForPortrait checks the Retku's current health. If it is below threshold it will then
     * tell the portrait to use the damaged animation.
     */
    private void checkHealthForPortrait() {
        if (currHealth < 50) {
            portrait.setWoundedAnimation();
        } else {
            portrait.setNormalAnimation();
        }

    }

    /**
     * getter for the portrait, checks if damaged portrait is used or not first
     * @return Portrait of the Retku
     */
    public AnimatedActor getPortrait() {
        checkHealthForPortrait();
        return portrait;
    }

    /**
     * getter for attack
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * getter for defence
     * @return defence
     */
    public int getDefence() {
        return defence;
    }

    /**
     * isConscious returns true if the Retku's hp is over 0
     * @return boolean wether health is above 0
     */
    public boolean isConscious() {
        return currHealth > 0;
    }
}