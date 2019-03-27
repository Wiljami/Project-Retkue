package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Item class will hold the information specific to each item
 *
 * @author Viljami Pietarila
 * @version 2019.0221
 */
public class Item {
    private String name;
    private String description;

    private int attack;
    private int defense;
    private Slot slot;
    private Rarity rarity;
    private int price;

    private Group icon;

    public enum Slot {
        TOOL, GARB, TRINKET
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }

    /**
     * TODO: Create the item constructor
     * Constructor should read the information to the name and description from localization files
     */
    public Item(int id, int attack, int defense, Slot slot, Rarity rarity, int price) {
        this.slot = slot;
        this.attack = attack;
        this.defense = defense;
        this.price = price;
        this.rarity = rarity;
        String bundle_id = Utils.convertToId(id);
        readDescriptions(bundle_id);
        generateIcon(bundle_id);
    }

    private void generateIcon(String id) {
        Image image = new Image (Utils.loadTexture("items/item"+ id + ".png"));
        String frameName = "items/border_";
        switch(rarity) {
            case COMMON: frameName += "white"; break;
            case UNCOMMON: frameName += "green"; break;
            case RARE: frameName += "rare"; break;
            case EPIC: frameName += "purple"; break;
            case LEGENDARY: frameName += "yellow"; break;
        }
        frameName += ".png";
        Image frame = new Image (Utils.loadTexture(frameName));
        icon = new Group();
        image.setSize(72f, 72f);
        icon.addActor(image);
        frame.setSize(72f, 72f);
        icon.addActor(frame);
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

    public Group getIcon() {
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