package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;

/**
 * Item class will hold the information specific to each item
 *
 * @author Viljami Pietarila
 * @version 2019.0221
 */
public class Item {
    private String name;
    private String description;
    private Texture icon;

    private int attack;
    private int defense;
    private Slot slot;
    private int price;

    public enum Slot {
        TOOL, GARB, TRINKET
    }

    /**
     * TODO: Create the item constructor
     * Constructor should read the information to the name and description from localization files
     */
    public Item(int id, int attack, int defense, Slot slot, int price) {
        this.slot = slot;
        this.attack = attack;
        this.defense = defense;
        this.price = price;
        String bundle_id = Utils.convertToId(id);
        readDescriptions(bundle_id);
        icon = Utils.loadTexture("items/item" + bundle_id + ".png");
    }

    private void readDescriptions(String id) {
        String halfKey = "ITEM" + id;
        String key = halfKey + "_NAME";
        name = Utils.readBundle(Scene.getBundle(), key);
        key = halfKey + "_DESCRIPTION";
        description = Utils.readBundle(Scene.getBundle(), key);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Texture getIcon() {
        return icon;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public Slot getSlot() {
        return slot;
    }

    public int getPrice() {
        return price;
    }
}