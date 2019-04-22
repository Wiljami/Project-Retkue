package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.I18NBundle;

import java.math.MathContext;

/**
 * Item class will hold the information specific to each item
 *
 * @author Viljami Pietarila
 * @version 2019.0327
 */
public class Item {
    private String nameKey;
    private String descriptionKey;

    private int effect;
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

    private class Weapon extends ProtoItem {
        public int attack;
        public Weapon(int id, int price, int attack) {
            super(id, price, Slot.TOOL);
            this.attack = attack;
        }

        @Override
        public int getEffect() {
            return attack;
        }
    }

    private class Armor extends ProtoItem {
        public int defense;
        public Armor(int id, int price, int defense) {
            super(id, price, Slot.GARB);
            this.defense = defense;
        }

        @Override
        public int getEffect() {
            return defense;
        }
    }

    private class Trinket extends ProtoItem {
        public int trinketEffect;
        public Trinket(int id, int price, int trinketEffect) {
            super(id, price, Slot.TRINKET);
            this.trinketEffect = trinketEffect;
        }

        @Override
        public int getEffect() {
            return trinketEffect;
        }
    }

    private abstract class ProtoItem {
        public int id;
        public int price;
        public Slot slot;

        public ProtoItem(int id, int price, Slot slot) {
            this.id = id;
            this.price = price;
            this.slot = slot;
        }
        public abstract int getEffect();
    }

    private Weapon[] weaponData = {
            new Weapon(1,10,1),
            new Weapon(2,20,2),
            new Weapon(3,40,3),
            new Weapon(4,80,4),
            new Weapon(101,160,5),
            new Weapon(102,320,6),
            new Weapon(103,640,7),
            new Weapon(104,1280,8),
            new Weapon(201,2560,9),
            new Weapon(202,5120,10),
            new Weapon(203,10240,11),
            new Weapon(204,20480,12)
    };

    private Armor[] armorData = {
            new Armor(1,10,1),
            new Armor(2,20,2),
            new Armor(3,40,3),
            new Armor(4,80,4),
            new Armor(101,160,5),
            new Armor(102,320,6),
            new Armor(103,640,7),
            new Armor(104,1280,8),
            new Armor(201,2560,9),
            new Armor(202,5120,10),
            new Armor(203,10240,11),
            new Armor(204,20480,12)
    };

    private Trinket[] trinketData = {
            new Trinket(1, 10, 1),
            new Trinket(2, 10, 2),
            new Trinket(3, 10, 3),
            new Trinket(4, 10, 4),
            new Trinket(5, 10, 5),
            new Trinket(6, 10, 6),
            new Trinket(7, 10, 7),
            new Trinket(8, 10, 8),
            new Trinket(9, 10, 9),
            new Trinket(10, 10, 10),
            new Trinket(11, 10, 11),
    };

    /**
     * saveableId generates an id that is used to identify items with their id, rarity and slot.
     * The saveableId is used for saving the game data.
     * @return saveableId integer
     */
    public int saveableId() {
        int saveableId = id;
        switch (slot) {
            case TOOL: saveableId += 10000; break;
            case GARB: saveableId += 20000; break;
            case TRINKET: saveableId += 30000; break;
            default: throw new IllegalArgumentException("Missing slot for saving id.");
        }
        switch (rarity) {
            case COMMON: break;
            case UNCOMMON: saveableId += 1000; break;
            case RARE: saveableId += 3000; break;
            case EPIC: saveableId += 5000; break;
            case LEGENDARY: saveableId += 8000; break;
            default: throw new IllegalArgumentException("Missing rarity for saving id.");
        }
        return saveableId;
    }

    /**
     * Constructor should read the information to the nameKey and descriptionKey from localization files
     */
    public Item(int id, Location location) {
        id = sortOutSlotFromId(id);
        id = sortOutRarityFromId(id);

        this.id = id;
        this.location = location;

        initiateItem();
    }

    private void initiateItem() {
        findItemStats();
        String bundle_id = Utils.convertToId(id);
        generateBundleKeys(bundle_id);
        generateIcon(bundle_id);
        applyRarityEffect();
    }

    public Item() {
        int random = MathUtils.random(10);
        if (random < 4) {
            this.id = randomItem(weaponData);
            this.slot = Slot.TOOL;
        } else if (random < 8) {
            this.id = randomItem(armorData);
            this.slot = Slot.GARB;
        } else {
            this.id = randomItem(trinketData);
            this.slot = Slot.TRINKET;
        }
        randomRarity();
        initiateItem();
    }

    private int randomItem(ProtoItem[] array) {
        int random = MathUtils.random(array.length-1);
        ProtoItem item = array[random];
        return item.id;
    }

    private void randomRarity() {
        int random = MathUtils.random(100);
        if (random == 100) {
            this.rarity = Rarity.LEGENDARY;
        } else if (random > 90) {
            this.rarity = Rarity.EPIC;
        } else if (random > 75) {
            this.rarity = Rarity.RARE;
        } else if (random > 50) {
            this.rarity = Rarity.UNCOMMON;
        } else {
            this.rarity = Rarity.COMMON;
        }
    }

    private void applyRarityEffect() {
        switch (rarity) {
            case COMMON: this.effect = this.effect + 0; price = price * 1; break;
            case UNCOMMON: this.effect = this.effect + 1; price = price * 2; break;
            case RARE: this.effect = this.effect + 3; price = price * 4; break;
            case EPIC: this.effect = this.effect + 5; price = price * 8; break;
            case LEGENDARY: this.effect = this.effect + 8; price = price * 16; break;
            default: throw new IllegalArgumentException("Missing rarity!");
        }
    }

    private void findItemStats () {
        ProtoItem[] dataToSearch;
        ProtoItem item;
        switch (slot) {
            case TOOL: dataToSearch = weaponData; break;
            case GARB: dataToSearch = armorData; break;
            case TRINKET: dataToSearch = trinketData; break;
            default: throw new IllegalArgumentException("Slot info is missing! id: " + id);
        }
        boolean found = false;
        for (int n = 0; n < dataToSearch.length && !found; n++) {
            if (dataToSearch[n].id == id) {
                item = dataToSearch[n];
                this.price = item.price;
                this.effect = item.getEffect();
                found = true;
            }
        }
        if (found == false) {
            throw new IllegalArgumentException("No item found with id: " + id);
        }
    }

    private int sortOutRarityFromId (int id) {
        if (id > 8000) {
            this.rarity = Rarity.LEGENDARY;
            return id - 8000;
        } else if (id > 5000) {
            this.rarity = Rarity.EPIC;
            return id - 5000;
        } else if (id > 3000) {
            this.rarity = Rarity.RARE;
            return id - 3000;
        } else if (id > 1000) {
            this.rarity = Rarity.UNCOMMON;
            return id - 1000;
        } else {
            this.rarity = Rarity.COMMON;
            return id;
        }
    }

    private int sortOutSlotFromId (int id) {
        if (id > 30000) {
            this.slot = Slot.TRINKET;
            return id - 30000;
        } else if (id > 20000) {
            this.slot = Slot.GARB;
            return id - 20000;
        } else if (id > 10000) {
            this.slot = Slot.TOOL;
            return id - 10000;
        } else {
            throw new IllegalArgumentException ("Item ID must have slot within it! id: " + id);
        }
    }

    /**
     * Generates the item icon Group with the item's id and its rarity.
     * @param id id of the item
     */
    private void generateIcon(String id) {
        String itemFileName = "items/";
        switch(slot) {
            case TOOL: itemFileName += "WEAPON"; break;
            case GARB: itemFileName += "ARMOR"; break;
            case TRINKET: itemFileName += "TRINKET"; break;
        }
        itemFileName += id + ".png";
        picture = Utils.loadTexture(itemFileName);
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

    /**
     * Finds the item descriptionKey from the bundle with the item id
     * @param id id of the item
     */
    private void generateBundleKeys(String id) {
        String halfKey = "";
        switch (slot) {
            case TOOL: halfKey += "WEAPON"; break;
            case GARB: halfKey += "ARMOR"; break;
            case TRINKET: halfKey += "TRINKET"; break;
            default: throw new IllegalArgumentException("No slot found for item! id: " + id);
        }
        halfKey += id;
        nameKey = halfKey + "_NAME";
        descriptionKey = halfKey + "_DESCRIPTION";
    }

    public String getName() {
        String name = "";
        switch (rarity) {
            case COMMON: name += Utils.readBundle(Scene.getBundle(), "common"); break;
            case UNCOMMON: name += Utils.readBundle(Scene.getBundle(), "uncommon"); break;
            case RARE: name += Utils.readBundle(Scene.getBundle(), "rare"); break;
            case EPIC: name += Utils.readBundle(Scene.getBundle(), "epic"); break;
            case LEGENDARY: name += Utils.readBundle(Scene.getBundle(), "legendary"); break;
            default: throw new IllegalArgumentException("Missing rarity!");
        }
        name += " " + Utils.readBundle(Scene.getBundle(), nameKey);
        return name;
    }

    public String getDescription() {
        return Utils.readBundle(Scene.getBundle(), descriptionKey);
    }

    /**
     * Get the item icon Group as in the frame and the icon picture in a default size 72px.
     * @return Group of the image icon.
     */
    public Group getIcon() {
        Group icon = getIcon(72f);
        return icon;
    }

    /**
     * Gets the image icon group in a set size.
     * @param size requested size of the icon
     * @return Group of the icon frame and picture
     */
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

    public int getEffect() {
        return effect;
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

    private String readType() {
        switch (slot) {
            case TOOL: return Utils.readBundle(Scene.getBundle(), "weapon");
            case GARB: return Utils.readBundle(Scene.getBundle(), "armor");
            case TRINKET: return Utils.readBundle(Scene.getBundle(), "trinket");
            default: throw new IllegalArgumentException("Missing slot for readtype! id: " + id);
        }
    }

    public String itemText() {
        String text = "";
        text += readType() + ". ";
        text += getDescription();
        switch(slot) {
            case TOOL: text += "\nATT: "; break;
            case GARB: text += "\nDEF: "; break;
            case TRINKET: text += "\n"; break;
            default: throw new IllegalArgumentException("Missing slot for itemText! id: " + id);
        }
        text += getEffect();
        return text;
    }
}