package main;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.LoginController;
import views.MainController;
import views.MasterController;
import views.RegisterController;

public class MainApp extends Application {
	// 배경음악 넣다가 말았음. 집가서 인터넷에서 검색하기
	public static MainApp app;
	private StackPane mainPage;
	private Map<String, MasterController> controllerMap = new HashMap<>();

	@Override
	public void start(Stage primaryStage) {
		app = this;
		try {
			FXMLLoader mainLoader = new FXMLLoader();
			mainLoader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			this.mainPage = mainLoader.load();
			
			MainController mc = mainLoader.getController();
			mc.setRoot(mainPage);
			this.controllerMap.put("main",mc);

			FXMLLoader loginLoader = new FXMLLoader();
			loginLoader.setLocation(getClass().getResource("/views/LoginLayout.fxml"));
			AnchorPane loginPage = loginLoader.load();
			LoginController lc = loginLoader.getController();
			lc.setRoot(loginPage);
			controllerMap.put("login", lc);

			FXMLLoader registerLoader = new FXMLLoader();
			registerLoader.setLocation(getClass().getResource("/views/RegisterLayout.fxml"));
			AnchorPane registerPage = registerLoader.load();
			RegisterController rc = registerLoader.getController();
			rc.setRoot(registerPage);
			this.controllerMap.put("register", rc);

			Scene scene = new Scene(mainPage);
			mainPage.getChildren().add(loginPage);

			Font.loadFont(getClass().getResourceAsStream("/imgs/Maplestory Bold.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("/imgs/Maplestory Light.ttf"), 14);
			primaryStage.setTitle("10122 정재성 닷지게임 (메이플스토리 Ver)");
			primaryStage.getIcons().add(new Image("/imgs/ico.png"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("./style.css").toExternalForm());
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("자바FX 로딩중 오류발생");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void slideOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 800);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 0);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e) -> {
			mainPage.getChildren().remove(pane);
		}, toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	public void fadeOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue fade = new KeyValue(pane.opacityProperty(), 0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e) -> {
			this.mainPage.getChildren().remove(pane);
		}, fade);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	public void fadeIn(String name) {
		MasterController c = controllerMap.get(name); // ������ ��Ʈ�ѷ��� �ʿ��� ������.
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);

		pane.setOpacity(0);

		Timeline timeline = new Timeline();
		KeyValue fadeIn = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), fadeIn);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	public void loadPane(String name) {
		MasterController c = controllerMap.get(name); // ������ ��Ʈ�ѷ��� �ʿ��� ������.
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);
	}

	public void slideIn(String name) {
		MasterController c = controllerMap.get(name); // ������ ��Ʈ�ѷ��� �ʿ��� ������.
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);

		pane.setTranslateX(-800); // �������� ������ ����ȭ��Ų �� �ִϸ��̼� ����
		pane.setOpacity(0);

		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}
