package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
	public static Connection getConnection() {
		Connection con = null;
		try {
			//MySQL 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			//java database connection
			String conStr = "jdbc:mysql://gondr.asuscomm.com/yy_10122?useUnicode=true"
						+ "&characterEncoding=utf8"  
						+ "&useSSL=false"
						+ "&serverTimezone=Asia/Seoul";
			String user = "yy_10122";
			String pw = "1234";
			
			con = DriverManager.getConnection(conStr, user, pw);
		} catch (Exception e) {
			System.out.println("에러발생");
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(ResultSet value) {
		if(value != null) {
			try {value.close();} catch (Exception e) {}
		}
	}
	
	public static void close(PreparedStatement value) {
		if(value != null) {
			try {value.close();} catch (Exception e) {}
		}
	}
	
	public static void close(Connection value) {
		if(value != null) {
			try {value.close();} catch (Exception e) {}
		}
	}
}

