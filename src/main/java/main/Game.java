package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game {
	private GraphicsContext gc;
	private final double width;
	private final double height;
	private AnimationTimer mainLoop;
	private long before;
	private ArrayList<Bullet> bulletList;
	public Player player;
	private double bulletSize;
	private double bulletSpeed;
	private double playerSize;
	private double playerSpeed;
	private double newBullet=0;
	private Image img;
	
	public Game(Canvas canvas) {
		this.img = new Image("/imgs/ground1.png");
		this.bulletList = new ArrayList<Bullet>();
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.bulletSize = 25;
		this.bulletSpeed = 5;
		this.playerSize = 55;
		this.playerSpeed = 5;
		this.player = new Player(this.playerSpeed,this.playerSize);
		canvas.setFocusTraversable(true);
		canvas.addEventHandler(KeyEvent.KEY_PRESSED,e->{
			KeyCode code = e.getCode();
			if(code!=KeyCode.LEFT && code!=KeyCode.RIGHT && code!=KeyCode.UP && code!=KeyCode.DOWN) return;
			this.player.keyMap.put(code, true);
		});
		canvas.addEventHandler(KeyEvent.KEY_RELEASED,e->{
			KeyCode code = e.getCode();
			if(code!=KeyCode.LEFT && code!=KeyCode.RIGHT && code!=KeyCode.UP && code!=KeyCode.DOWN) return;
			this.player.keyMap.put(code, false);
		});
		this.gc = canvas.getGraphicsContext2D();
		this.mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update((now-before)/1000000000d);
				before = now;
				render();
			}
		};
		this.before = System.nanoTime();
		this.mainLoop.start();
		for(int i=0; i < 10; i++) {
			this.bulletList.add(new Bullet(this.bulletSize,this.bulletSpeed));
		}
		
		
		System.out.println("게임생성 완료 : Game.java");
	}
	
	public void update(double delta) {
		this.newBullet+=delta;
		if(this.newBullet >= 5) { // 시작하고 5초가 지날때마다
			this.bulletList.add(new Bullet(this.bulletSize,this.bulletSpeed));
			this.newBullet=0;
		}
	}
	
	public void render() {
		this.gc.clearRect(0,0,this.width,this.height);
		this.gc.drawImage(this.img, 0, 0,this.width,this.height);
		this.player.render(this.gc);
		int size = this.bulletList.size();
		for(int i=0; i < size;i++) {
			Bullet bullet = this.bulletList.get(i);
			bullet.render(this.gc);
			// 플레이어와 불렛을 그려준다.
		}
	}
	
	public void keyHandler(KeyEvent e) {
		this.player.KeyHandler(e);
	}
}

