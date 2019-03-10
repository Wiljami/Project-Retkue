package fi.tuni.tiko;

public class Party {
    private Retku[] retkus;

    public Party() {
        retkus = new Retku[3];
        retkus[0] = new Retku("Bill", 100);
        retkus[1] = new Retku("Mik'ed", 100);
        retkus[2] = new Retku("Mei", 100);
    }
}