package main;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player {
	private double speed;
	private double size; 
	private double x;
	private double y;
	private Image img;
	public Map<KeyCode, Boolean> keyMap = new HashMap<>(); 
	
	public Player(double speed, double size) {
		this.speed = speed;
		this.size = size;
		this.img = new Image("/imgs/player.png");
		this.x = 400-this.size/2;
		this.y = 300-this.size/2;
		this.keyMap.put(KeyCode.LEFT, false);
		this.keyMap.put(KeyCode.RIGHT, false);
		this.keyMap.put(KeyCode.UP, false);
		this.keyMap.put(KeyCode.DOWN, false);
	}
	
	public void render(GraphicsContext gc) {
		if(this.keyMap.get(KeyCode.LEFT)) this.x-=this.speed;
		if(this.keyMap.get(KeyCode.RIGHT)) this.x+=this.speed;
		if(this.keyMap.get(KeyCode.UP)) this.y-=this.speed;
		if(this.keyMap.get(KeyCode.DOWN)) this.y+=this.speed;
		this.replacOutScreen();
		gc.drawImage(this.img, this.x, this.y,this.size,this.size);
	}
	
	public void replacOutScreen() {
		if(this.y+this.size+15>=600) this.y=600-this.size-15; // 아래가 벽일때
		if(this.y<=15) this.y=15; // 위쪽이 벽일때 
		if(this.x+this.size+5>=800) this.x=800-this.size-5; // 오른쪽이 벽일떄 해결완료
		if(this.x<=5) this.x=5; // 왼쪽이 벽일떄 해결완료
	}
	
	public void KeyHandler(KeyEvent e) {
		
	}
}
