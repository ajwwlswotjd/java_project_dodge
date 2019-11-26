package views;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.AEADBadTagException;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class LoginController extends MasterController {
	@FXML private Button loginBtn;
	@FXML private TextField idInput;
	@FXML private PasswordField pwdInput;
	@FXML private AnchorPane root;
	MediaPlayer mp;
	@FXML private void initialize() {
		System.out.println("LoginController init");
		this.startLoginMusic();
	}
	
	private void startLoginMusic() {
		String soundFile = "login.mp3";
		Media md = new Media(Paths.get(soundFile).toUri().toString());
		this.mp = new MediaPlayer(md);
		this.mp.setAutoPlay(true);
		this.mp.setCycleCount((int)Double.POSITIVE_INFINITY);
		this.mp.setVolume(0.5);
	}
	private void stopLoginMusic() {
		this.mp.stop();
	}
	
	@FXML private void loginPrcoess() {
		String id = this.idInput.getText().trim();
		String pwd = this.pwdInput.getText().trim();
		if(id.isEmpty() || pwd.isEmpty()) {
			Util.showAlert("에러","로그인에 필요한 필수값이 비어있습니다.",AlertType.ERROR);
			return;
		}
		
		UserVO user = this.checkLogin(id, pwd);
		if(user!=null) {
			MainApp.app.slideOut(root);
			this.stopLoginMusic();
		}else {
			Util.showAlert("로그인 실패","존재하지 않는 아이디이거나 비밀번호가 맞지 않습니다.",AlertType.ERROR);
			return;
		}
	}
	
	@FXML private void shutdown() {
		Stage stage = (Stage)this.loginBtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void goHomePage() {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/ajwwlswotjd"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("인터넷 연결 오류");
		}
	}
	
	@FXML private void goRegister() {
		MainApp.app.fadeIn("register");
	}
	
	
	
	private UserVO checkLogin(String id, String pwd) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM `dd_user` WHERE `id` = ? AND `password` = PASSWORD(?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2,pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("id").toString());
				user.setName(rs.getString("name").toString());
				return user;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("데이터베이스 연결 오류 안내");
			Util.showAlert("에러", "데이터베이스 연결중 오류가 발생했습니다. 인터넷 연결상태를 확인해주세요.",AlertType.ERROR);
			return null;
		} finally {
			JDBCUtil.close(con);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(rs);
		}
	}
}
