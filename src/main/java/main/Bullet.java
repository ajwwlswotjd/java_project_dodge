package main;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
	public double size;
	public double speed;
	public int dir;
	public double x;
	public double y;
	public double targetX;
	public double targetY;
	private double disX;
	private double disY;
	private double time;
	private Image img;
	private Random rand = new Random();
	
	public Bullet(double size,double speed) {
		this.size = size;
		this.speed = speed;
		this.img = new Image("/imgs/shuricken.gif");
		this.dir = rand.nextInt(4);
		if(this.dir==0) { // 왼쪽에서 오른쪽으로
			this.x = -30;
			this.y = rand.nextDouble()*600;
			this.targetX = 830;
			this.targetY = rand.nextDouble()*600;
		}else if(this.dir==1) {	// 밑에서 위로
			this.x = rand.nextDouble()*800;
			this.y = -30;
			this.targetX = rand.nextDouble()*800;
			this.targetY = -30;
		}else if(this.dir==2) { // 오른쪽에서 왼쪽으로
			this.x = 830;
			this.y = rand.nextDouble()*600;
			this.targetX = -30;
			this.targetY = rand.nextDouble()*600;
		}else if(this.dir==3) { // 위에서 밑으로
			this.x = rand.nextDouble()*800;
			this.y = -30;
			this.targetX = rand.nextDouble()*800;
			this.targetY = 630;
		} else {
			System.out.println("이 텍스트가 나온다면, Bullet.js가 잘못된겁니다.");
			return;
		}
		this.disX = this.targetX-this.x;
		this.disY = this.targetY-this.y;
	}
	
	public void render(GraphicsContext gc) {
		this.time = 1000/this.speed;
		double dirX = this.disX/this.time;
		double dirY = this.disY/this.time;
		this.x+=dirX;
		this.y+=dirY;
		gc.drawImage(this.img,this.x,this.y,this.size,this.size);
	}
	
	private boolean checkOutScreen() {
		if(this.y < -30 || this.y > 630 || this.x < -30 || this.y > 830) return true;
		else return false;
	}
}