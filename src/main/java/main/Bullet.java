package main;

import java.util.Random;

public class Bullet {
	public double size;
	public double speed;
	public int dir;
	public double x;
	public double y;
	private Random rand = new Random();
	
	public Bullet(double size,double speed) {
		this.size = size;
		this.speed = speed;
		
		this.dir = rand.nextInt(4);
		if(this.dir==0) { // 왼쪽에서
			this.x = -30;
			this.y = rand.nextDouble()*600;
		}else if(this.dir==1) {	// 밑에서
			this.x = rand.nextDouble()*800;
			this.y = -30;
		}else if(this.dir==2) { // 오른쪽에서
			this.x = 830;
			this.y = rand.nextDouble()*600;
		}else if(this.dir==3) { // 위에서
			this.x = rand.nextDouble()*800;
			this.y = -630;
		}
		
		
	}
}
