package views;

import domain.UserVO;
import javafx.fxml.FXML;

public class MainController extends MasterController {
	
	private UserVO user;
	@FXML private void initialize() {
		System.out.println("MainController init");
	}
	
	public void setLoginUser(UserVO user) {
		this.user = user;
		// 메인레이아웃에서 로그인 정보 표시할떄 사용
	}
	
}
