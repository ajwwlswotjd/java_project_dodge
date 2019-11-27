package views;

import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private MediaPlayer mp;
	private MediaPlayer player;
	@FXML private void initialize() {
		System.out.println("LoginController init");
		this.startMusic("login.mp3");
		this.pwdInput.addEventHandler(KeyEvent.KEY_PRESSED,e->{
			if(e.getCode() == KeyCode.ENTER) this.loginProcess();
		});
	}
	
	public void startMusic(String soundFile) {
		Media md = new Media(Paths.get(soundFile).toUri().toString());
		this.mp = new MediaPlayer(md);
		this.mp.setAutoPlay(true);
		this.mp.setCycleCount((int)Double.POSITIVE_INFINITY);
		this.mp.setVolume(0.4);
	}
	
	public void playMusic(String soundFile) {
		Media md = new Media(Paths.get(soundFile).toUri().toString());
		this.player = new MediaPlayer(md);
		this.player.setVolume(0.8);
		this.player.play();
	}
	
	public void stopMusic() {
		this.mp.stop();
	}
	
	@FXML private void loginProcess() {
		String id = this.idInput.getText().trim();
		String pwd = this.pwdInput.getText().trim();
		if(id.isEmpty() || pwd.isEmpty()) {
			Util.showAlert("에러","로그인에 필요한 필수값이 비어있습니다.",AlertType.ERROR);
			return;
		}
		
		UserVO user = this.checkLogin(id, pwd);
		if(user!=null) {
			this.stopMusic();
			MainApp.app.setLoginInfo(user);
			MainApp.app.slideOut(getRoot());
			this.idInput.setText("");
			this.pwdInput.setText("");
			this.startMusic("main.mp3");
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
				user.setMaxScore(rs.getInt("max_score"));
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
