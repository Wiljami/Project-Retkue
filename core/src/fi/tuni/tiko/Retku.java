package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;

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

    /**
     * Texture of the Retku
     */
    private Texture texture;

    /**
     * FileName of the texture used
     */
    private String imageFile;

    private Item slotA;

    private Item slotB;

    private Item slotC;

    private Party party;

    /**
     * Retku constructor
     * TODO: Needs new Retku graphics
     * @param name name of the Retku
     * @param health max Health of Retku
     */
    public Retku(String name, int health, String portraitFile, Party party) {
        setMaxHealth(health);
        setCurrHealth(health);
        setName(name);
        initPortrait(portraitFile);
        this.party = party;
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
        if (currHealth <= 0) {
            System.out.println("Retku nimeltä " + getName() + " has deaded");
        }
    }

    /**
     * healRetku increases the Retku's currHealth by the heal amount
     * @param heal heal amount
     */
    public void healRetku(int heal) {
        setCurrHealth(getCurrHealth() + heal);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void initPortrait(String portraitFile) {
        this.imageFile = portraitFile;
        this.texture = Utils.loadTexture(portraitFile);
    }

    public void equipItem(Item item) {
        switch(item.getSlot()) {
            case TOOL: equipTool(item); break;
            case GARB: equipGarb(item); break;
            case TRINKET: equipTrinket(item); break;
        }
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
        System.out.println(slot);
        switch (slot) {
            case TOOL: setSlotA(null);
            case GARB: setSlotB(null);
            case TRINKET: setSlotC(null);
        }
    }
}