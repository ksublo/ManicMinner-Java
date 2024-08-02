package lab;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Canvas canvas;
	private AnimationTimer timer;
	private Level lvl = new Level(800,500);
	private GameController controller;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ManicMiner.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.setTitle("Manic Miner");
			primaryStage.show();

			controller = loader.getController();
			controller.startGame();

			primaryStage.setOnCloseRequest(this::exitProgram);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exitProgram(WindowEvent evt) {
		controller.startGame();
		System.exit(0);
	}

}