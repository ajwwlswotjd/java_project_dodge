package views;

import javafx.fxml.FXML;
import main.MainApp;

public class HelpController extends MasterController {
	@FXML private void initialize() {
		System.out.println("HelpController init");
	}
	@FXML private void close() {
		MainApp.app.fadeOut(getRoot());
	}
}
