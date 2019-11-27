package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.MainApp;

public class MenuController extends MasterController {
	@FXML private Button btn;
	@FXML private void initialize() {
		System.out.println("MenuController init");
	}
	@FXML private void ok() {
		MainApp.app.fadeOut(getRoot());
		MainApp.app.fadeOut(MainApp.app.controllerMap.get("game").getRoot());
		LoginController lc = (LoginController)MainApp.app.controllerMap.get("login");
		lc.startMusic("main.mp3");
	}
}
