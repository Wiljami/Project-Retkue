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
    private int slot;

    /**
     * TODO: Create the item constructor
     * Constructor should read the information to the name and description from localization files
     */
    public Item() {

    }
}