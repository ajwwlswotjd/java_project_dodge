package views;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.MainApp;

public class MainController extends MasterController {
	@FXML private Label userLbl;
	private UserVO user;
	@FXML private void initialize() {
		System.out.println("MainController init");
	}
	
	public void setLoginInfo(UserVO user) {
		// 메인레이아웃에서 로그인 정보 표시할떄 사용
		this.user = user;
		this.userLbl.setText(this.user.getName()+"("+this.user.getId()+") 님 반갑습니다.");
	}
	
	@FXML private void logout() {
		System.out.println("로그아웃 누름");
		MainApp.app.slideIn("login");
	}
	
	@FXML private void showRank() {
		System.out.println("랭킹 보여주기 싫음 ㅋ");
	}
	
	@FXML private void startGame() {
		
	}
	
}