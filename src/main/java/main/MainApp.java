package main;

import java.util.HashMap;
import java.util.Map;

import domain.UserVO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.GameController;
import views.HelpController;
import views.LoginController;
import views.MainController;
import views.MasterController;
import views.MenuController;
import views.RankController;
import views.RegisterController;

public class MainApp extends Application {
	// 코딩 편의상 처음 로그인 부분을 스킵하였음. 제출시 수정 해야함. 저번처럼 까먹을수도 있음 (LoginController)
	// 곤달님 솔루션 => 렌더링 과정에서 키 검사 하기
	public static MainApp app;
	public Game game = null;
	private StackPane mainPage;
	private UserVO user;
	public Map<String, MasterController> controllerMap = new HashMap<>();

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
			
			FXMLLoader gameLoader = new FXMLLoader();
			gameLoader.setLocation(getClass().getResource("/views/GameLayout.fxml"));
			AnchorPane gamePage = gameLoader.load();
			GameController gc = gameLoader.getController();
			gc.setRoot(gamePage);
			this.controllerMap.put("game",gc);

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
			
			FXMLLoader menuLoader = new FXMLLoader();
			menuLoader.setLocation(getClass().getResource("/views/MenuLayout.fxml"));
			AnchorPane menuPage = menuLoader.load();
			MenuController mnc = menuLoader.getController();
			mnc.setRoot(menuPage);
			this.controllerMap.put("menu", mnc);
			
			FXMLLoader helpLoader = new FXMLLoader();
			helpLoader.setLocation(getClass().getResource("/views/HelpLayout.fxml"));
			AnchorPane helpPage = helpLoader.load();
			HelpController hc = helpLoader.getController();
			hc.setRoot(helpPage);
			this.controllerMap.put("help", hc);
			
			FXMLLoader rankLoader = new FXMLLoader();
			rankLoader.setLocation(getClass().getResource("/views/RankLayout.fxml"));
			AnchorPane rankPage = rankLoader.load();
			RankController rnc = rankLoader.getController();
			rnc.setRoot(rankPage);
			this.controllerMap.put("rank", rnc);

			Scene scene = new Scene(mainPage,1024,768);
			scene.addEventFilter(KeyEvent.KEY_PRESSED,e->{
				if(game!=null) this.game.keyHandler(e);
			});
			mainPage.getChildren().add(loginPage);

			Font.loadFont(getClass().getResourceAsStream("/imgs/Maplestory Bold.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("/imgs/Maplestory Light.ttf"), 14);
			primaryStage.setTitle("10122 정재성 닷지게임 (메이플스토리 Ver)");
			primaryStage.getIcons().add(new Image("/imgs/ico.png"));
			primaryStage.setScene(scene);
			primaryStage.setWidth(1024);
			primaryStage.setHeight(768);
			primaryStage.setMaxHeight(768);
			primaryStage.setMaxWidth(1024);
			primaryStage.setMinHeight(768);
			primaryStage.setMinWidth(1024);
//			primaryStage.setResizable(false);
//			primaryStage.setAlwaysOnTop(true);
//			primaryStage.setOpacity(0.5);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("자바FX 로딩중 오류발생");
			e.printStackTrace();
		}
	}
	
	public UserVO getUser() {
		return this.user;
	}
	
	public void setUser(UserVO us) {
		this.user = us;
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
		MasterController c = controllerMap.get(name);
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
		MasterController c = controllerMap.get(name);
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);
	}
	
	public void setLoginInfo(UserVO user) {
		this.user = user;
		MainController mc = (MainController)this.controllerMap.get("main");
		mc.setLoginInfo(user);
	}

	public void slideIn(String name) {
		MasterController c = controllerMap.get(name);
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);

		pane.setTranslateX(-800);
		pane.setOpacity(0);

		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}
