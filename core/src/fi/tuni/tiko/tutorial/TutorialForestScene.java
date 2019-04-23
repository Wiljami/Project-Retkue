package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.GameHeader;
import fi.tuni.tiko.Main;
import fi.tuni.tiko.Party;
import fi.tuni.tiko.PartyBar;
import fi.tuni.tiko.Scene;
import fi.tuni.tiko.Utils;

import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.BOTTOM;
import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.MIDDLE;
import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.TOP;

public class TutorialForestScene extends Scene implements TutorialScene{

    private TutorialController controller;
    private Main game;
    private Image mask;
    private Party party;
    private GameHeader header;
    private PartyBar partyBar;

    public TutorialForestScene(Main game, TutorialController controller) {
        super();
        this.controller = controller;
        this.game = game;

        party = controller.getTutorialParty();

        setupBackground("forest.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Chillage.ogg"));

        mask = new Image(Utils.loadTexture("mask.png"));
        getStage().addActor(mask);
        fadeIn();
        createMenu();
        continueTutorial(1);
    }

    private Button faster;
    private Button harder;
    private Label steps;
    private Label timer;

    private void createMenu() {
        //heightArray is given float values that represent the height of each element in the table
        //It is a percentage of the entire screen
        float[] heightArray = {1/6.4f, 1/6f, 1/9.6f, 1/6.4f, 1/16f, 1/9.6f, 1/(5f + 1f/3f), 1/16f};

        Utils.convertToPixels(heightArray);

        partyBar = new PartyBar(heightArray[6], party, getStage());

        generateLog();

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        float height = game.WORLDPIXELHEIGHT *3f/4f;
        table.add(log).colspan(2).pad(20).prefHeight(height);
        table.row();
        table.add(partyBar).colspan(2).expand().fill().prefHeight(heightArray[6]);
        table.row();
        getStage().addActor(table);
    }

    Table log;
    Label textLog;
    Button encounter;

    private void generateLog() {
        log = new Table();
        faster = new TextButton("Vauhdita", getSkin());
        harder = new TextButton("Heal Party", getSkin());
        steps = new Label("STEPS: 400", getSkin());
        timer = new Label("00:10:00", getSkin());
        encounter = new TextButton("Start Encounter", getSkin());
        textLog = new Label("The party is ready to adventure!", getSkin());
        log.background(Utils.loadButtonImage("log.png", 0, 0));
        log.add(harder).pad(5);
        log.add(faster).pad(5);
        log.add(steps).pad(5);
        log.row();
        log.add().prefHeight(50f);
        log.row();
        log.add(timer).colspan(3).pad(10);
        log.row();
        log.add(encounter).colspan(3).pad(10);
        log.row();
        float height = game.WORLDPIXELHEIGHT *3f/4f;
        float width = game.WORLDPIXELWIDTH * 6f/5f;
        log.add(textLog).prefWidth(width).prefHeight(height).colspan(3).pad(10);
        //steps.setVisible(false);
        //timer.setVisible(false);
        faster.setVisible(false);
        harder.setVisible(false);
        log.setVisible(false);
        encounter.setVisible(false);
    }

    private void tutorialPopUp(int text_id, int id, String image, TutorialPopUp.Position location) {
        TutorialPopUp popUp = new TutorialPopUp(text_id, id, image, location, this);
        popUp.show(getStage());
    }

    @Override
    public void continueTutorial(int id) {
        System.out.println("id: " + id);
        switch (id) {
            case 1: partyBar.toFront(); tutorialPopUp(8 ,2, "old_guy1.png", TOP); break;
            case 2: phase2(); break;
            case 3: break;
            case 4: phase4(); break;
            case 5: tutorialPopUp(13,6, "old_guy1.png", TOP); break;
            case 6: phase6(); break;
            case 7: System.out.println("phase7"); break;
            case 8: phase8(); break;
            case 9: phase9(); break;
            case 10: break;
            case 11: phase11(); break;
            case 12: System.out.println("phase12"); break;
            case 13: tutorialPopUp(19,14, "old_guy1.png", TOP); break;
            case 14: tutorialPopUp(20,15, "old_guy1.png", TOP); break;
            case 15: phase15(); break;
            case 16: phase16(); break;
            case 17: phase17(); break;
            case 18: System.out.println("phase18"); break;
            case 19: phase19(); break;
        }
    }

    private void phase2() {
        log.setVisible(true);
        log.toFront();
        tutorialPopUp(9 ,4, "old_guy1.png", BOTTOM);
    }

    String loggedText;

    private void phase4() {
        loggedText = "A rabid wolf emerges from the bushes.";
        textLog.setText(loggedText);
        tutorialPopUp(12,5, "old_guy1.png", TOP);
    }

    private void phase6() {
        encounter.setVisible(true);
        encounter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                encounter.setVisible(false);
                continueTutorial(8);
            }
        });

        tutorialPopUp(14,7, "old_guy1.png", TOP);
    }

    private void phase8() {
        loggedText += "\nA rabid wolf eyes Bill hungrily.";
        textLog.setText(loggedText);
        tutorialPopUp(15,9, "old_guy1.png", TOP);
    }

    private void phase9() {
        loggedText += "\nA rabid wolf takes a bite out of Bill!";
        textLog.setText(loggedText);
        party.findRetku(0).damageRetku(50);
        partyBar.updateHealthBars();
        tutorialPopUp(16, 11,"old_guy1.png", TOP);
    }

    private void phase11() {
        harder.setVisible(true);
        harder.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                party.findRetku(0).healRetku(50);
                partyBar.updateHealthBars();
                harder.removeListener(this);
                continueTutorial(13);
            }
        });
        tutorialPopUp(18,12, "old_guy1.png", MIDDLE);
    }

    private void phase15() {
        loggedText += "\nBill pummels the rabid wolf!";
        loggedText += "\nMei ninjachops the rabid wolf!";
        loggedText += "\nMik'ed reads poetry to the rabid wolf!";
        textLog.setText(loggedText);
        tutorialPopUp(21,16, "old_guy1.png", TOP);
    }

    private void phase16() {
        loggedText += "\nThe rabid flees for its life!";
        textLog.setText(loggedText);
        tutorialPopUp(22,17, "old_guy1.png", TOP);
    }

    private void phase17() {
        TutorialResults tutorialResults = new TutorialResults(this);
        tutorialResults.show(getStage());
        tutorialPopUp(23,18, "old_guy1.png", TOP);
    }

    private void phase19() {
        controller.tutorialPhase(3);
    }

    @Override
    public void updateValues() {

    }

    public void fadeIn() {
        mask.toFront();
        mask.setColor(0,0,0, 0.4f);
    }

    public void fadeOut() {
        mask.setColor(0,0,0, 0.0f);
    }

}
