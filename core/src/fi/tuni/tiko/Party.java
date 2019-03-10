package fi.tuni.tiko;

/**
 * Party is a class that will hold the functions and information of the player's group
 * It holds the resources the player has and the information of different Retkus.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class Party {
    private Retku[] retkus;

    public Party() {
        retkus = new Retku[3];
        retkus[0] = new Retku("Bill", 100);
        retkus[1] = new Retku("Mik'ed", 100);
        retkus[2] = new Retku("Mei", 100);
    }

    public Retku findRetku(int n) {
        return retkus[n];
    }
}