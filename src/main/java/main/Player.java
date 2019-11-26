package main;

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
	
	public Player(double speed, double size) {
		this.speed = speed;
		this.size = size;
		this.img = new Image("/imgs/player.png");
		this.x = 400-this.size/2;
		this.y = 300-this.size/2;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(this.img, this.x, this.y,this.size,this.size);
	}
	
	public void KeyHandler(KeyEvent e) {
		KeyCode code = e.getCode();
		if(code==KeyCode.LEFT) {
			this.x-=this.speed;
			if(this.x<=0) this.x=0;
		}else if(code==KeyCode.RIGHT) {
			this.x+=this.speed;
			if(this.x+this.size>=800) this.x=800-this.size;
		}else if(code==KeyCode.UP) {
			this.y-=this.speed;
			if(this.y<=0) this.y=0;
		}else if(code==KeyCode.DOWN) {
			this.y+=this.speed;
			if(this.y+this.size>=600) this.y=600-this.size;
		}else return;
	}
}
