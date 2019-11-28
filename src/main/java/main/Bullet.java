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
	public boolean shellPass;
	private double disY;
	private double time;
	private Image img;
	public double waitTime;
	private double canvasWidth;
	private double canvasHeight;
	private Random rand = new Random();
	
	public Bullet(double size,double speed,double w,double h) {
		this.img = new Image("/imgs/shuricken.gif");
		this.size = size;
		this.shellPass = false;
		this.speed = speed;
		this.canvasWidth = w;
		this.canvasHeight = h;
		this.reset();
	}
	
	public void render(GraphicsContext gc) {
		this.time = 1000/this.speed;
		double dirX = this.disX/this.time;
		double dirY = this.disY/this.time;
		this.x+=dirX;
		this.y+=dirY;
		if(this.checkOutScreen()) this.reset();
		gc.drawImage(this.img,this.x,this.y,this.size,this.size);
	}
	
	
	private void reset() {
		this.dir = rand.nextInt(4);
		this.waitTime = (double)((this.rand.nextInt(100)+50));
		if(this.dir==0) { // 왼쪽에서 오른쪽으로
			this.x = -this.waitTime;
			this.y = rand.nextDouble()*this.canvasHeight;
			this.targetX = this.canvasWidth+this.waitTime;
			this.targetY = rand.nextDouble()*this.canvasHeight;
		}else if(this.dir==1) {	// 밑에서 위로
			this.x = rand.nextDouble()*this.canvasWidth;
			this.y = this.canvasHeight+this.waitTime;
			this.targetX = rand.nextDouble()*this.canvasWidth;
			this.targetY = -this.waitTime;
		}else if(this.dir==2) { // 오른쪽에서 왼쪽으로
			this.x = this.canvasWidth+this.waitTime;
			this.y = rand.nextDouble()*this.canvasHeight;
			this.targetX = -this.waitTime;
			this.targetY = rand.nextDouble()*this.canvasHeight;
		}else if(this.dir==3) { // 위에서 밑으로
			this.x = rand.nextDouble()*this.canvasWidth;
			this.y = -this.waitTime;
			this.targetX = rand.nextDouble()*this.canvasWidth;
			this.targetY = this.canvasHeight+this.waitTime;
		} else {
			System.out.println("이 텍스트가 나온다면, Bullet.java가 잘못된겁니다.");
			return;
		}
		this.disX = this.targetX-this.x;
		this.disY = this.targetY-this.y;
	}
	
	private boolean checkOutScreen() {
		if(this.y < -this.waitTime || this.y > this.canvasHeight+this.waitTime || this.x < -this.waitTime || this.y > this.canvasWidth+this.waitTime) return true;
		else return false;
	}
}