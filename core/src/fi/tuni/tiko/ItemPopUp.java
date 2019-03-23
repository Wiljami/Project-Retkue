package fi.tuni.tiko;

public class ItemPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    public ItemPopUp(String title, Item item) {
        super(title, skin, windowStyle);
        button(readLine("return"), false);
    }
}