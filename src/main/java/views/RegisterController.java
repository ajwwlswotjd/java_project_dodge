package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class RegisterController extends MasterController {
	@FXML private Label mainLbl;
	@FXML private TextField idInput;
	@FXML private TextField nameInput;
	@FXML private PasswordField pwdInput1;
	@FXML private PasswordField pwdInput2; 
	@FXML private void initialize() {
		System.out.println("RegisterController init");
	}
	
	public void clear() {
		this.idInput.setText("");
		this.nameInput.setText("");
		this.pwdInput1.setText("");
		this.pwdInput2.setText("");
	}

	@FXML private void registerProcess() {
		
		String id = this.idInput.getText().trim();
		String name = this.nameInput.getText().trim();
		String pwd1 = this.pwdInput1.getText().trim();
		String pwd2 = this.pwdInput2.getText().trim();
		
		if(id.isEmpty() || name.isEmpty() || pwd1.isEmpty() || pwd2.isEmpty()) {
			Util.showAlert("에러","회원가입에 필요한 필수값이 비어있습니다.",AlertType.ERROR);
			return;
		}
		
		if(!pwd1.equals(pwd2)) {
			Util.showAlert("실패","비밀번호와 비밀번호 확인이 서로 다릅니다.",AlertType.WARNING);
			return;
		}
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlExist = "SELECT * FROM `dd_user` WHERE `id` = ?";
		String sqlInsert = "INSERT INTO `dd_user`(`num`,`id`,`password`,`name`,`max_score`) VALUES (null,?,PASSWORD(?),?,?)";
		try {
			pstmt = con.prepareStatement(sqlExist);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Util.showAlert("중복","이미 존재하는 아이디 입니다.",AlertType.INFORMATION);
				return;
			}
			
			JDBCUtil.close(pstmt);
			
			
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd1);
			pstmt.setString(3, name);
			pstmt.setInt(4,0);
			if(pstmt.executeUpdate() != 1) {
				Util.showAlert("에러","올바르지 않은 값이 입력되었습니다.",AlertType.ERROR);
				return;
			}
			
			Util.showAlert("회원가입 완료","회원가입이 완료되었습니다. 감사합니다.",AlertType.INFORMATION);
			MainApp.app.fadeOut(getRoot());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("데이터베이스 연결중 오류발생(RegisterController)");
		} finally {
			JDBCUtil.close(con);
			JDBCUtil.close(pstmt);
		}
	}
	
	@FXML private void close() {
		MainApp.app.fadeOut(getRoot());
	}
}