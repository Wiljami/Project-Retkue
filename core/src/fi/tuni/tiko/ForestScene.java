package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

//TODO: Everything

class ForestScene extends Scene{

    private Quest quest;
    private Label timer;

    public ForestScene(Main game) {
        super(game);
        createMenu();
        setupBackground("forest.png");
    }

    private void createMenu() {
        timer = new Label("00:00:02", getSkin());

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Forest", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(timer);
        table.row();
        table.add().prefHeight(200);
        table.row();
        getStage().addActor(table);
    }

    @Override
    public void renderActions() {
        super.renderActions();
        if (quest != null) {
            long timeLeft = quest.timeLeft();
            if (timeLeft < 0) {
                ResultsPopUp resultsPopUp = new ResultsPopUp();
                resultsPopUp.show(getStage());
                quest = null;
                timer.setText("00:00:00");
            }
            updateTimer(timeLeft);
        }
    }

    private void updateTimer(long time) {
        int hours   = (int) ((time / (1000*60*60)) % 24);
        int minutes = (int) ((time / (1000*60)) % 60);
        int seconds = (int) (time / 1000) % 60;
        timer.setText(toAddZero(hours) + ":" + toAddZero(minutes) + ":" + toAddZero(seconds));
    }

    private String toAddZero(int number) {
        String s;
        if (number < 10) {
            s = "0" + Integer.toString(number);
        } else {
            s = Integer.toString(number);
        }
        return s;
    }

    public void setQuest(Quest q) {
        this.quest = q;
    }
}