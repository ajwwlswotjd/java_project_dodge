package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class RankController extends MasterController {
	@FXML private ListView<UserVO> topScoreList;
	private ObservableList<UserVO> list;
	private UserVO user;
	@FXML private Label userLabel;
	@FXML private Label scoreLabel;
	@FXML private Label rankLabel;
	@FXML private void initialize() {
		System.out.println("RankController init");
		this.list = FXCollections.observableArrayList();
		this.topScoreList.setItems(list);
	}
	
	private UserVO makeUser(ResultSet rs, int idx) throws Exception {
		UserVO temp = new UserVO();
		temp.setId(rs.getString("id"));
		temp.setName(rs.getString("name"));
		temp.setMaxScore(rs.getInt("max_score"));
		temp.setIdx(idx);
		return temp;
	}
	
	@FXML private void close() {
		MainApp.app.fadeOut(getRoot());
	}
	
	public void reload() {
		this.user = MainApp.app.getUser();
		String sql = "SELECT COUNT(*) +1 AS rank FROM `dd_user` WHERE `max_score` > ( SELECT `max_score` FROM `dd_user` WHERE `id` = ?)";
		Connection con2 = JDBCUtil.getConnection();
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try {
			pstmt2 = con2.prepareStatement(sql);
			pstmt2.setString(1,this.user.getId());
			rs2=pstmt2.executeQuery();
			while(rs2.next()) {
				this.user.setIdx(rs2.getInt("rank"));
			}
		} catch (Exception e) {
			Util.showAlert("에러","랭킹 로딩중 에러발생. sql문 확인바람 (RankController)",AlertType.ERROR);
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(con2);
		}
		this.userLabel.setText(user.getName()+" ( "+user.getId()+" ) ");
		this.scoreLabel.setText("최고점수 : "+user.getMaxScore()+"점");
		int idx = this.user.getIdx();
		this.rankLabel.setText("랭킹 : "+idx+"위");
		this.list.clear();
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		sql = "SELECT * FROM `dd_user` ORDER BY `max_score` DESC LIMIT 0,10";
		try {
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int index = 1;
			while(rs.next()) {
				list.add(this.makeUser(rs,index));
				index++;
			}
			
		} catch (Exception e) {
			Util.showAlert("에러","랭킹 로딩중 에러발생. sql문 확인바람 (RankController)",AlertType.ERROR);
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}
}
