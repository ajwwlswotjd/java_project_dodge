package main;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item {
	private Image img;
	private double x;
	private double y;
	private double cvW;
	private double cvH;
	private Random rand;
	private double size;
	private int status; // 0이면 헤이스트, 1이면 홀리매직쉘, 2면 경쿠
	
	public Item(Image img,double w, double h,double size, int status) {
		this.rand = new Random();
		this.size = size;
		this.img = img;
		this.cvH = h;
		this.cvW = w;
		this.status = status;
		this.x = (double)(this.rand.nextInt((int)(this.cvW-this.size)));
		this.y = (double)(this.rand.nextInt((int)(this.cvH-this.size)));
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getPost() {
		return "("+this.x+" , "+this.y+")";
	}
	
	public boolean checkCol(Player player) {
		double dx = Math.abs(this.x-player.x)+5;
		double dy = Math.abs(this.y-player.y)+10;
		double distance = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2));
		double radiusSum = this.size/2+player.size/2;
		return distance <= radiusSum; 
	}
	
	
	public void render(GraphicsContext gc) {
		gc.drawImage(this.img, this.x, this.y,this.size,this.size);
	}
}
