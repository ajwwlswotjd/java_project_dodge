package views;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import main.Game;
import main.MainApp;

public class GameController extends MasterController {
	@FXML private Canvas canvas;
	@FXML private void initialize() {
		System.out.println("GameController init");
	}
	
	public void startGame() {
		MainApp.app.game = new Game(this.canvas);
		MainApp.app.fadeIn("game");
	}
}
