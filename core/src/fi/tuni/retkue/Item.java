package fi.tuni.retkue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Item class will hold the information specific to each item
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class Item {
    /**
     * bundle key for the Item's name
     */
    private String nameKey;

    /**
     * bundle key for the Item's description
     */
    private String descriptionKey;

    /**
     * power of the effect of the Item
     */
    private int effect;

    /**
     * Slot of the Item
     */
    private Slot slot;

    /**
     * Rarity of the Item
     */
    private Rarity rarity;

    /**
     * Price of the Item
     */
    private int price;

    /**
     * Id of the Item
     */
    private int id;


    /**
     * enum Slot. Holds different possible Item slots
     */
    public enum Slot {
        TOOL, GARB, TRINKET
    }

    /**
     * enum Location. Holds different locations for the Item.
     */
    public enum Location {
        RETKUA, RETKUB, RETKUC, PARTY, SHOP, OTHER
    }

    /**
     * enum Rarity. Holds different rarities for the Item.
     */
    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }

    /**
     * Texture of the Item
     */
    private Texture picture;

    /**
     * Texture of the Item's frame
     */
    private Texture frame;

    /**
     * Location of the Item
     */
    private Location location;

    /**
     * class Weapon is a platform for holding information of different Weapons
     */
    private class Weapon extends ProtoItem {
        /**
         * attack value of the Weapon
         */
        public int attack;

        /**
         * Constructor of the Weapon
         * @param id id of the Weapon
         * @param price price of the Weapon
         * @param attack attack of the Weapon
         */
        public Weapon(int id, int price, int attack) {
            super(id, price, Slot.TOOL);
            this.attack = attack;
        }

        /**
         * getEffect returns the attack value of the weapon
         * @return attack value
         */
        @Override
        public int getEffect() {
            return attack;
        }
    }

    /**
     * class Armor is a platform for holding information of different Armors
     */
    private class Armor extends ProtoItem {
        /**
         * defence value of the Armor
         */
        public int defense;

        /**
         * Constructor of the Armor
         * @param id id of the Armor
         * @param price price of the Armor
         * @param defense defence valur of the Armor
         */
        public Armor(int id, int price, int defense) {
            super(id, price, Slot.GARB);
            this.defense = defense;
        }

        /**
         * getEffect returns the defence value of the armor
         * @return defence value
         */
        @Override
        public int getEffect() {
            return defense;
        }
    }
    /**
     * class Trinket is a platform for holding information of different Trinkets
     */
    private class Trinket extends ProtoItem {
        /**
         * id of the trinketEffect
         */
        public int trinketEffect;

        /**
         * Constructor of Trinket
         * @param id id of the Trinket
         * @param price price of the Trinket
         * @param trinketEffect id of the trinketEffect
         */
        public Trinket(int id, int price, int trinketEffect) {
            super(id, price, Slot.TRINKET);
            this.trinketEffect = trinketEffect;
        }

        /**
         * getEffect returns the id of the trinketEffect
         * @return trinketEffect
         */
        @Override
        public int getEffect() {
            return trinketEffect;
        }
    }

    /**
     * class ProtoItem is a abstract class from which rest of the item classes extend
     */
    private abstract class ProtoItem {
        /**
         * id of the item
         */
        public int id;
        /**
         * price of the item
         */
        public int price;
        /**
         * Slot of the item
         */
        public Slot slot;

        /**
         * ProtoItem constuctor
         * @param id id of the item
         * @param price price of the item
         * @param slot slot of the item
         */
        public ProtoItem(int id, int price, Slot slot) {
            this.id = id;
            this.price = price;
            this.slot = slot;
        }

        /**
         * abstract getEffect class for getting the effect of the item
         * @return effect of the item
         */
        public abstract int getEffect();
    }

    /**
     * Weapon[] holds the information of different weapons in the game
     */
    private Weapon[] weaponData = {
            new Weapon(1,10,1),
            new Weapon(2,40,2),
            new Weapon(3,90,3),
            new Weapon(4,160,4),
            new Weapon(101,250,5),
            new Weapon(102,360,6),
            new Weapon(103,490,7),
            new Weapon(104,640,8),
            new Weapon(201,810,9),
            new Weapon(202,1000,10),
            new Weapon(203,1210,11),
            new Weapon(204,1440,12)
    };

    /**
     * Armor[] holds the information of different armors in the game
     */
    private Armor[] armorData = {
            new Armor(1,10,1),
            new Armor(2,40,2),
            new Armor(3,90,3),
            new Armor(4,160,4),
            new Armor(101,250,5),
            new Armor(102,360,6),
            new Armor(103,490,7),
            new Armor(104,640,8),
            new Armor(201,810,9),
            new Armor(202,1000,10),
            new Armor(203,1210,11),
            new Armor(204,1440,12)
    };

    /**
     * Trinket[] holds the information of different trinkets in the game
     */
    private Trinket[] trinketData = {
            new Trinket(1, 2000, 1),
            new Trinket(2, 500, 2),
            new Trinket(3, 500, 3),
            new Trinket(4, 500, 4),
            new Trinket(5, 1000, 5),
            new Trinket(6, 1000, 6),
            new Trinket(7, 500, 7),
            new Trinket(8, 500, 8),
            new Trinket(9, 1000, 9),
            new Trinket(10, 500, 10),
            new Trinket(11, 2000, 11),
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

    /**
     * initiates item's stats, bundle keys, icon and applies the effect of rarity
     */
    private void initiateItem() {
        findItemStats();
        String bundle_id = Utils.convertToId(id);
        generateBundleKeys(bundle_id);
        generateIcon(bundle_id);
        applyRarityEffect();
    }

    /**
     * Constructor for Item. As given no parameters it will generate a random Item.
     */
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

    /**
     * picks a random Item from ProtoItem pools
     * @param array pool of items to choose from
     * @return id of the item
     */
    private int randomItem(ProtoItem[] array) {
        int random = MathUtils.random(array.length-1);
        ProtoItem item = array[random];
        return item.id;
    }

    /**
     * Randomizes rarity of the item
     */
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

    /**
     * Applies the effects of the rarity. Increases the effect and the price.
     */
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

    /**
     * Finds the stats of the item
     */
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

    /**
     * When given an id, sorts out the rarity of the item based on it. Returns the id without the
     * rarity identification
     * @param id id of the item
     * @return id without the rarity information
     */
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

    /**
     * When given id, sorts out the slot of the item based on it. Returns the id without the slot
     * information
     * @param id id of the item
     * @return id without the slot information
     */
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
        descriptionKey = halfKey + "_DESC";
    }

    /**
     * getName returns the localized rarity and name of the item
     * @return localized rarity + name of the item
     */
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

    /**
     * getDescription returns the localized description of the item
     * @return localized description of the item
     */
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

    /**
     * getter for effect
     * @return effect
     */
    public int getEffect() {
        return effect;
    }

    /**
     * getter for slot
     * @return slot
     */
    public Slot getSlot() {
        return slot;
    }

    /**
     * getter for price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * getter for picture
     * @return picture
     */
    public Texture getPicture() {
        return picture;
    }

    /**
     * getter for location
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * setter for location
     * @param location new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * readType returns the localized name of the item's type
     * @return localized name of the item's type
     */
    private String readType() {
        switch (slot) {
            case TOOL: return Utils.readBundle(Scene.getBundle(), "weapon");
            case GARB: return Utils.readBundle(Scene.getBundle(), "armor");
            case TRINKET: return Utils.readBundle(Scene.getBundle(), "trinket");
            default: throw new IllegalArgumentException("Missing slot for readtype! id: " + id);
        }
    }

    /**
     * returns localized stats and description of the Item
     * @return localized stats and description
     */
    public String itemText() {
        String text = "";
        text += readType() + ". ";
        text += getDescription();
        switch(slot) {
            case TOOL: text += "\nATT: " + getEffect(); break;
            case GARB: text += "\nDEF: " + getEffect(); break;
            case TRINKET: text += "\n"; break;
            default: throw new IllegalArgumentException("Missing slot for itemText! id: " + id);
        }
        return text;
    }
}