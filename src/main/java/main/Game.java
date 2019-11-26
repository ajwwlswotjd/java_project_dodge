package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
	private Image img;
	
	public Game(Canvas canvas) {
		this.img = new Image("/imgs/ground1.png");
		this.bulletList = new ArrayList<Bullet>();
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.bulletSize = 40;
		this.bulletSpeed = 10;
		this.playerSize = 55;
		this.playerSpeed = 30;
		for(int i=0; i < 10; i++) {
			this.bulletList.add(new Bullet(this.bulletSize,this.bulletSpeed));
		}
		this.gc = canvas.getGraphicsContext2D();
		
		this.mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update((now-before)/10000000d);
				before = now;
				render();
			}
		};
		this.before = System.nanoTime();
		
		this.player = new Player(this.playerSpeed,this.playerSize);
		this.mainLoop.start();
		
		System.out.println("게임생성 완료 : Game.java");
	}
	
	public void update(double delta) {
		
	}
	
	public void render() {
		this.gc.clearRect(0,0,this.width,this.height);
		this.gc.drawImage(this.img, 0, 0,this.width,this.height);
		this.player.render(this.gc);
		
	}
	
	public void keyHandler(KeyEvent e) {
		this.player.KeyHandler(e);
	}
}

