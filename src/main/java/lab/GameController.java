package lab;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;


public class GameController implements Initializable {

    private AnimationTimer animationTimer;
    private Level lvl;
    @FXML
    private Canvas canvas;
    @FXML
    private Button startGameBtn;
    @FXML
    private Button restartGameBtn;
    @FXML
    private Button highScoreBtn;
    @FXML
    private Button loadScoreBtn;
    @FXML
    private ListView<Result> scoreList;
    @FXML
    private Text scoreListText;
    @FXML
    private TextField playerNameField;
    @FXML
    private Text playerName;
    @FXML
    private Text manicMinerText;
    @FXML
    private Text byMeText;
    @FXML
    private Button showStatisticBtn;

    private ObservableList<Result> results;

    private GraphicsContext gc;
    private String name;

    public GameController() {results = FXCollections.observableList(new ArrayList<Result>());
    }

    public void startGame(){
        restartGameBtn.setVisible(false);
        highScoreBtn.setVisible(false);
        loadScoreBtn.setVisible(false);
        scoreListText.setVisible(false);
        scoreList.setVisible(false);
        byMeText.setVisible(false);
        manicMinerText.setVisible(false);
        showStatisticBtn.setVisible(false);

        startScene();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            lvl.getManic().setMovingRight(true);
        }
        else if (event.getCode() == KeyCode.LEFT) {
            lvl.getManic().setMovingLeft(true);
        }
        else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.SPACE) {
            lvl.getManic().jump();
        }
    }

    private void handleKeyReleased(KeyEvent event){
        if (event.getCode() == KeyCode.RIGHT) {
            lvl.getManic().setMovingRight(false);
        }
        else if (event.getCode() == KeyCode.LEFT) {
            lvl.getManic().setMovingLeft(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();

        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);

        startGame();
    }

    @FXML
    public void restartGame(){
        startGame();
        startGameBtn.setVisible(true);
        playerNameField.setVisible(true);
        playerName.setVisible(true);
    }

    @FXML
    public void start(){
        this.lvl = new Level(canvas.getWidth(), canvas.getHeight());
        animationTimer = new DrawingThread(canvas, lvl, this);

        name = playerNameField.getText();

        if(!name.isEmpty()){
            lvl.setName(name);
            animationTimer.start();
            scoreList.setItems(results);

            startGameBtn.setVisible(false);
            playerNameField.setVisible(false);
            playerName.setVisible(false);
            showStatisticBtn.setVisible(false);
        }
    }
    @FXML
    public void highScore(){
        HashSet<Result> unique = new HashSet<Result>();
        for(Result s : results){
            unique.add(s);
        }
        results.clear();
        results.addAll(unique);
        results.sort((x,y) -> y.getScore() - x.getScore());
    }

    @FXML
    public void loadScore(){
        results.clear();
        try(Scanner scanner = new Scanner(new java.io.File("result.csv"))){
            scanner.useDelimiter("[;\\n]");
            while(scanner.hasNext()){
                String name = scanner.next();
                int score = scanner.nextInt();
                results.add(new Result(name,score));
            }
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }

    @FXML
    public void showStatistics(){
        highScoreBtn.setVisible(true);
        loadScoreBtn.setVisible(true);
        scoreListText.setVisible(true);
        scoreList.setVisible(true);
        showStatisticBtn.setVisible(false);
    }

    public void startScene(){
        gc.setFill(Color.rgb(255,211,234));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.rgb(20,20,20));
        gc.setFont(new Font("Verdana", 40));
        gc.fillText("MANIC MINER", 250, 200);

        gc.setFont(new Font("Verdana", 20));
        gc.fillText("by Kseniia Blokhina", 300, 230);
    }

    private void printName(){
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Verdana", 10));
        gc.fillText("PLAYER NAME:", 580, 40);
        gc.setFont(new Font("Verdana", 14));
        gc.fillText(name, 580, 55);
    }

    private void win(){
        gc.clearRect(0,0, lvl.getWidth(), lvl.getHeight());
        gc.setFill(Color.rgb(255,211,234));
        gc.fillRect(0, 0, lvl.getWidth(), lvl.getHeight());

        gc.setFill(Color.rgb(20,20,20));
        gc.setFont(new Font("Verdana", 40));
        gc.fillText("MANIC MINER", 250, 200);
        gc.setFont(new Font("Verdana", 20));
        gc.fillText("  GAME OVER  ", 300, 250);
        gc.fillText("☺ YOU WIN ☺", 300, 270);
    }

    private void lose(){
        gc.clearRect(0,0, lvl.getWidth(), lvl.getHeight());
        gc.setFill(Color.rgb(255,211,234));
        gc.fillRect(0, 0, lvl.getWidth(), lvl.getHeight());

        gc.setFill(Color.rgb(20,20,20));
        gc.setFont(new Font("Verdana", 40));
        gc.fillText("MANIC MINER", 250, 200);
        gc.setFont(new Font("Verdana", 20));
        gc.fillText("  GAME OVER  ", 300, 250);
        gc.fillText("☹ YOU LOSE ☹", 300, 270);
    }

    public void check(){
        if(lvl.getScore() == 7){
            result();
            animationTimer.stop();
            win();
            vbage();
        }
        else if(lvl.getLive() == 0){
            result();
            animationTimer.stop();
            lose();
            vbage();
        }
        else {
            printName();
            byMeText.setVisible(true);
            manicMinerText.setVisible(true);
        }
    }

    private void vbage(){
        restartGameBtn.setVisible(true);
        showStatisticBtn.setVisible(true);
        byMeText.setVisible(false);
        manicMinerText.setVisible(false);
    }

    private void result(){
        Result result = lvl.getResult();
        results.toString();
        results.add(result);
        results.sort((x,y)-> y.getScore() - x.getScore());

        //save score
        try (FileWriter fw = new FileWriter("result.csv")){
            for(Result r : results){
                fw.write(r.getName() + ";" + r.getScore() + "\n");
            }
        }
        catch(IOException err)
        {
            err.printStackTrace();
        }
    }
}
