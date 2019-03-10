package fi.tuni.tiko;

public class Retku {
    private String name;

    private int maxHealth;
    private int currHealth;

    private String imageFile;

    public Retku(String name, int health) {
        setMaxHealth(health);
        setCurrHealth(health);
        setName(name);
        imageFile = "old_guy1.png";
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        if (currHealth > maxHealth) currHealth = maxHealth;
        this.currHealth = currHealth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float healthPercentage() {
        float healthPercentage = (float)getCurrHealth() / (float)getMaxHealth();
        return healthPercentage;
    }

    public String getImageFile() {
        return imageFile;
    }
}