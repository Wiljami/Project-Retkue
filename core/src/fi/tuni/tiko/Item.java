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
    private int id;

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

    private ItemData[] items = {

    };

    private ItemData[] itemData = {
            new ItemData(0,0, Slot.TRINKET, 0, Rarity.COMMON),
            new ItemData(3,7, Slot.GARB, 10, Rarity.LEGENDARY),
            new ItemData(6,6, Slot.GARB, 20, Rarity.UNCOMMON),
            new ItemData(1,5, Slot.TRINKET, 30, Rarity.LEGENDARY),
            new ItemData(2,4, Slot.TOOL, 50, Rarity.LEGENDARY),
            new ItemData(4,2, Slot.GARB, 90, Rarity.RARE),
            new ItemData(2,1, Slot.GARB, 120, Rarity.EPIC),
            new ItemData(3,7, Slot.GARB, 10, Rarity.COMMON),
            new ItemData(6,6, Slot.GARB, 20, Rarity.LEGENDARY),
            new ItemData(1,5, Slot.TRINKET, 30, Rarity.LEGENDARY),
            new ItemData(2,4, Slot.TOOL, 50, Rarity.LEGENDARY),
            new ItemData(4,2, Slot.GARB, 90, Rarity.LEGENDARY),
            new ItemData(2,1, Slot.GARB, 120, Rarity.LEGENDARY)
    };

    private class ItemData {
        public int attack;
        public int defense;
        public Slot slot;
        public int price;
        public Rarity rarity;
        public int trinketEffect = 0;
        public ItemData(int attack, int defense, Slot slot, int price, Rarity rarity) {
            this.attack = attack;
            this.defense = defense;
            this.slot = slot;
            this.price = price;
            this.rarity = rarity;
        }
        public ItemData(int attack, int defense, Slot slot, int price, Rarity rarity, int trinketEffect) {
            this(attack, defense, slot, price, rarity);
        }
    }

    /**
     * TODO: Create the item constructor
     * Constructor should read the information to the name and description from localization files
     */
    public Item(int id, Location location) {
        this.id = id;
        this.slot = itemData[id].slot;
        this.attack = itemData[id].attack;
        this.defense = itemData[id].defense;
        this.price = itemData[id].price;
        this.rarity = itemData[id].rarity;
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

    public int getId() {
        return id;
    }
}