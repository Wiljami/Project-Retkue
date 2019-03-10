package fi.tuni.tiko;

public class Retku {
    private String name;

    private int maxHealth;
    private int currHealth;

    public Retku(String name, int health) {
        setMaxHealth(health);
        setName(name);
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
        float healthPercentage = currHealth / maxHealth;
        return healthPercentage;
    }
}