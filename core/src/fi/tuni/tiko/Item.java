package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Item class will hold the information specific to each item
 *
 * @author Viljami Pietarila
 * @version 2019.0327
 */
public class Item {
    private String name;
    private String description;

    private int attack;
    private int defense;
    private Slot slot;
    private Rarity rarity;
    private int price;

    public enum Slot {
        TOOL, GARB, TRINKET
    }

    public enum Location {
        RETKUA, RETKUB, RETKUC, PARTY, SHOP, OTHER
    }

    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }

    private Texture picture;

    private Texture frame;

    private Location location;

    /**
     * TODO: Create the item constructor
     * Constructor should read the information to the name and description from localization files
     */
    public Item(int id, int attack, int defense, Slot slot, Rarity rarity, int price, Location location) {
        this.slot = slot;
        this.attack = attack;
        this.defense = defense;
        this.price = price;
        this.rarity = rarity;
        this.location = location;
        String bundle_id = Utils.convertToId(id);
        readDescriptions(bundle_id);
        generateIcon(bundle_id);
    }

    private void generateIcon(String id) {
        picture = Utils.loadTexture("items/item"+ id + ".png");
        String frameName = "items/border_";
        switch(rarity) {
            case COMMON: frameName += "white"; break;
            case UNCOMMON: frameName += "green"; break;
            case RARE: frameName += "blue"; break;
            case EPIC: frameName += "purple"; break;
            case LEGENDARY: frameName += "yellow"; break;
        }
        frameName += ".png";
        frame = Utils.loadTexture(frameName);
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
        Group icon = getIcon(72f);
        return icon;
    }

    public Group getIcon(float size) {
        Image image = new Image(picture);
        Image frameImage = new Image(frame);
        image.setSize(size, size);
        frameImage.setSize(size, size);
        Group icon = new Group();
        icon.addActor(image);
        icon.addActor(frameImage);
        icon.setSize(size, size);
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

    public Texture getPicture() {
        return picture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}