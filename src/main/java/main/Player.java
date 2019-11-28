package main;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Player {
	public double speed;
	public double size; 
	public double x;
	public double y;
	private double cvW;
	private double cvH;
	private Image leftImg;
	private Image rightImg;
	public boolean shell;
	private boolean dir;
	public Map<KeyCode, Boolean> keyMap = new HashMap<>(); 
	
	public Player(double speed, double size,double w, double h) {
		this.shell = false;
		this.dir = true; // 오른쪽을 바라본다.
		this.speed = speed;
		this.size = size;
		this.leftImg = new Image("/imgs/left.gif");
		this.rightImg = new Image("/imgs/right.gif");
		this.cvH = h;
		this.cvW = w;
		this.x = this.cvW/2-this.size/2;
		this.y = this.cvH/2-this.size/2;
		this.keyMap.put(KeyCode.LEFT, false);
		this.keyMap.put(KeyCode.RIGHT, false);
		this.keyMap.put(KeyCode.UP, false);
		this.keyMap.put(KeyCode.DOWN, false);
	}
	
	public void render(GraphicsContext gc) {
		if(this.keyMap.get(KeyCode.LEFT)) {
			this.x-=this.speed;
			this.dir = false;
		}
		if(this.keyMap.get(KeyCode.RIGHT)) {
			this.x+=this.speed;
			this.dir = true;
		}
		if(this.keyMap.get(KeyCode.UP)) this.y-=this.speed;
		if(this.keyMap.get(KeyCode.DOWN)) this.y+=this.speed;
		this.replacOutScreen();
		gc.drawImage(this.dir ? this.rightImg : this.leftImg, this.x, this.y,this.size,this.size);
		gc.setLineWidth(this.shell ? 3 : 1);
		gc.setStroke(this.shell ? Color.LIGHTGOLDENRODYELLOW : Color.WHITE);
		gc.strokeOval(this.x, this.y, this.size, this.size);
	}
	
	public void replacOutScreen() {
		if(this.y+this.size+15>=this.cvH) this.y=this.cvH-this.size-15; // 아래가 벽일때
		if(this.y<=15) this.y=15; // 위쪽이 벽일때 
		if(this.x+this.size+5>=this.cvW) this.x=this.cvW-this.size-5; // 오른쪽이 벽일떄 해결완료
		if(this.x<=5) this.x=5; // 왼쪽이 벽일떄 해결완료
	}
	
	public boolean checkCollision(Bullet bullet) {
		double dx = Math.abs(this.x-bullet.x);
		double dy = Math.abs(this.y-bullet.y);
		double distance = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2));
		double radiusSum = (this.size/2+bullet.size/2);
		if(this.y <= bullet.y) distance+=15;
		return distance <= radiusSum; 
	}
	
	public void KeyHandler(KeyEvent e) {
		
	}
}
