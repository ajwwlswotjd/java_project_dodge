package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import domain.UserVO;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import util.JDBCUtil;
import util.Util;
import views.LoginController;

public class Game {
	private GraphicsContext gc;
	private final double width;
	private final double height;
	private AnimationTimer mainLoop;
	private long before;
	private ArrayList<Bullet> bulletList;
	private ArrayList<Item> itemList;
	public Player player;
	private double bulletSize;
	private double bulletSpeed;
	private double itemSize;
	private double playerSize;
	private double playerSpeed;
	private double newBullet = 0;
	public boolean gameover;
	private int scoreValue;
	private boolean playerShell;
	public int score;
	private Image img;

	public Game(Canvas canvas) {
		this.gameover = false;
		this.scoreValue = 1;
		this.img = new Image("/imgs/ground1.png");
		this.bulletList = new ArrayList<Bullet>();
		this.itemList = new ArrayList<Item>();
		this.playerShell = false;
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.bulletSize = 20;
		this.bulletSpeed = 3;
		this.itemSize = 35;
		this.playerSize = 55;
		this.playerSpeed = 5;
		this.player = new Player(this.playerSpeed, this.playerSize, this.width, this.height);
		canvas.setFocusTraversable(true);
		canvas.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			KeyCode code = e.getCode();
			if (code != KeyCode.LEFT && code != KeyCode.RIGHT && code != KeyCode.UP && code != KeyCode.DOWN)
				return;
			this.player.keyMap.put(code, true);
		});
		canvas.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			KeyCode code = e.getCode();
			if (code != KeyCode.LEFT && code != KeyCode.RIGHT && code != KeyCode.UP && code != KeyCode.DOWN)
				return;
			this.player.keyMap.put(code, false);
		});
		this.gc = canvas.getGraphicsContext2D();
		Font font = Font.loadFont(getClass().getResourceAsStream("/imgs/Maplestory Bold.ttf"), 30);
		this.gc.setFont(font);
		this.gc.setFontSmoothingType(FontSmoothingType.GRAY);
		this.mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update((now - before) / 1000000000d);
				before = now;
				render();
			}
		};
		this.before = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			this.bulletList.add(new Bullet(this.bulletSize, this.bulletSpeed, this.width, this.height));
		}
		this.mainLoop.start();
		System.out.println("게임생성 완료 : Game.java");
	}

	public void update(double delta) {
		this.newBullet += delta;
		if (this.newBullet >= 1) { // 시작하고 1초가 지날때마다
			this.bulletList.add(new Bullet(this.bulletSize, this.bulletSpeed, this.width, this.height));
			this.newBullet = 0;
			this.score += this.scoreValue;
			int rand = (int) (Math.random() * 100 + 1);
			if (rand <= 5) { // 5%확률이 성공하면
				rand = (int) (Math.random() * 3);
				String imgSrc = "/imgs/" + (rand == 0 ? "fast" : rand == 1 ? "shell" : "coupon") + ".png";
				System.out.println(imgSrc);
//				Image img = new Image(imgSrc);
				Image img;
				if(rand==0) {
					img = new Image("/imgs/fast.PNG");
				}else if(rand==1) {
					img = new Image("/imgs/shell.PNG");
				}else {
					img = new Image("/imgs/coupon.png");
				}
				Item item = new Item(img, this.width, this.height, this.itemSize, rand);
				this.itemList.add(item);
				System.out.println(item.getPost());
			}
			if(this.playerShell) {
				this.player.shell = false;
				this.playerShell = false;
			}
		}
	}

	public void render() {
		if (this.gameover)
			return;
		this.gc.clearRect(0, 0, this.width, this.height);
		this.gc.drawImage(this.img, 0, 0, this.width, this.height);
		this.gc.setFill(Color.web("#333030"));
		this.gc.fillRoundRect(20, 30, 200, 120, 20, 20);
		this.gc.setFill(Color.web("#f4f5f9"));
		this.gc.fillRoundRect(30, 45, 180, 40, 20, 20);
		this.gc.setFill(Color.web("#333030"));
		this.gc.fillText("SCORE", 70, 75, 1000);
		// 점수 텍스트 띄우기
		gc.setFill(Color.web("#f4f5f9"));
		this.gc.fillText("" + this.score, this.score >= 100 ? 90 : this.score >= 10 ? 100 : 110, 130, 1000);
		this.player.render(this.gc);

		int itemLength = this.itemList.size();
		for (int i = 0; i < this.itemList.size(); i++) {
			Item item = this.itemList.get(i);
			item.render(this.gc);
			if (item.checkCol(this.player)) { // 아이템을 먹음
				int status = item.getStatus();
				if (status == 0) { // 헤이스트
					this.player.speed += 2;
				} else if (status == 1) { // 쉘
					this.player.shell = true;
				} else if (status == 2) { // 쿠폰
					this.scoreValue++;
				} else {
					System.out.println("이게 나오면 큰일난겁니다. (Game.java)");
					return;
				}
				this.itemList.remove(i);
			}
		}
		int size = this.bulletList.size();

		for (int i = 0; i < size; i++) {
			Bullet bullet = this.bulletList.get(i);
			bullet.render(this.gc);
			if (this.player.checkCollision(bullet) && !bullet.shellPass) { // 충돌감지
				if (this.player.shell) {
					this.playerShell=true;
					bullet.shellPass = true;
					return;
				}
				this.setGameOver();
				break;
			}
		}

	}

	public void setGameOver() {
		this.gameover = true;
		LoginController lc = (LoginController) MainApp.app.controllerMap.get("login");
		lc.playMusic("gameover.mp3");
		lc.stopMusic();
		MainApp.app.fadeIn("menu");
		UserVO user = MainApp.app.getUser();
		if (this.score <= user.getMaxScore())
			return; // 신기록 달성시

		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE `dd_user` SET `max_score`= ? WHERE `id` = ?";
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, this.score);
			pstmt.setString(2, user.getId());
			int result = pstmt.executeUpdate();
			if (result != 1) {
				Util.showAlert("에러", "기록 갱신중 오류발생(Game.java) 재시도바람.", AlertType.ERROR);
				return;
			}

			int originMax = user.getMaxScore();
			user.setMaxScore(this.score);
			Util.showAlert("최고기록 갱신", "축하드립니다! 기존의 최고기록인 " + originMax + "점 보다 " + (user.getMaxScore() - originMax)
					+ "점 높은 " + user.getMaxScore() + "점을 기록하셨습니다. 랭킹에는 자동으로 등록됩니다.", AlertType.INFORMATION);

		} catch (Exception e) {
			Util.showAlert("에러", "기록 갱신중 오류발생(Game.java) 인터넷 상태 확인바람.", AlertType.ERROR);
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	public void keyHandler(KeyEvent e) {
		this.player.KeyHandler(e);
	}
}
