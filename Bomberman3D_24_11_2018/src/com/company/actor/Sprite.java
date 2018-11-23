package com.company.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Sprite extends Actor{
	private int heart;
	public Sprite(int x, int y, int typeSprite, int orient, int speed, int heart, String images) {
		this.x = x;
		this.y = y;
		this.typeActor = typeSprite;
		this.runBomb = Bomber.DISALLOW_RUN;
		this.orient = orient;
		this.speed = speed;
		this.heart=heart;
		this.img = new ImageIcon(getClass().getResource(images)).getImage();
		width = img.getWidth(null);
		if(typeActor == Actor.MONSTER || typeActor == Actor.SOOT || typeActor == Actor.PIRATE){
			height = img.getHeight(null)-23;			//phần shawdow che 1 phần chân của sprite vs độ dài 23 pixel
		}
		else {
			height = img.getHeight(null)-38;			//boss có thể đi qua shawdow nên ko bị che mất
		}

	}
	//vẽ quái vật bồ hóng
	public void drawAllSoot(Graphics2D g2d){
		if(typeActor == Actor.SOOT)
			g2d.drawImage(img, x, y - 23, null);
	}
//vẽ quái vật cướp biển
	public void drawAllPirate(Graphics2D g2d){
		if(typeActor == Actor.PIRATE)
			g2d.drawImage(img, x, y - 23, null);
	}


	public void drawBoss(Graphics2D grp2d){
		int h=0;
		if(typeActor == Actor.BOSS){
			grp2d.drawImage(img, x, y-38, null);
			grp2d.setColor(Color.WHITE);
			//vẽ tim boss
			grp2d.drawRect(x+16, y-54, width-32, 12);
			Image imgHeartBoss = new ImageIcon(getClass().getResource("/Images/heart_boss.png")).getImage();		//vẽ tym boss
			//xử lí mạng của boss
			for(int i=0;i<(heart-1)/250+1 ;i++){
				grp2d.drawImage(imgHeartBoss, x+18+h, y-52, null);
				h=h+10;
			}
		}
	}
	@Override
	public void changeOrient(int orient) {
		super.changeOrient(orient);
		if(typeActor == Actor.MONSTER){
			switch (orient) {
			case LEFT:
				img = new ImageIcon(getClass().getResource("/Images/monster_left.png")).getImage();
				break;
			case RIGHT:
				img = new ImageIcon(getClass().getResource("/Images/monster_right.png")).getImage();
				break;
			case UP:
				img = new ImageIcon(getClass().getResource("/Images/monster_up.png")).getImage();
				break;
			case DOWN:
				img = new ImageIcon(getClass().getResource("/Images/monster_down.png")).getImage();
				break;
			default:
				break;
			}
		}else if(typeActor == Actor.BOSS){
			switch (orient) {
				case LEFT:
					img = new ImageIcon(getClass().getResource("/Images/boss_left.png")).getImage();
					break;
				case RIGHT:
					img = new ImageIcon(getClass().getResource("/Images/boss_right.png")).getImage();
					break;
				case UP:
					img = new ImageIcon(getClass().getResource("/Images/boss_up.png")).getImage();
					break;
				case DOWN:
					img = new ImageIcon(getClass().getResource("/Images/boss_down.png")).getImage();
					break;
				default:
					break;
			}
		}else if(typeActor == Actor.PIRATE){
			switch (orient) {
				case LEFT:
					img = new ImageIcon(getClass().getResource("/Images/pirate_left.png")).getImage();
					break;
				case RIGHT:
					img = new ImageIcon(getClass().getResource("/Images/pirate_right.png")).getImage();
					break;
				case UP:
					img = new ImageIcon(getClass().getResource("/Images/pirate_up.png")).getImage();
					break;
				case DOWN:
					img = new ImageIcon(getClass().getResource("/Images/pirate_down.png")).getImage();
					break;
				default:
					break;
			}
		}else{
			//Soot có thể di chuyển xuyên tường vs tốc độ nhanh hơn monster
			switch (orient) {
				case LEFT:
					img = new ImageIcon(getClass().getResource("/Images/soot_left.png")).getImage();
					break;
				case RIGHT:
					img = new ImageIcon(getClass().getResource("/Images/soot_right.png")).getImage();
					break;
				case UP:
					img = new ImageIcon(getClass().getResource("/Images/soot_up.png")).getImage();
					break;
				case DOWN:
					img = new ImageIcon(getClass().getResource("/Images/soot_down.png")).getImage();
					break;
				default:
					break;
			}
		}
	}
	//khởi tạo giá trị của heart
	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

}
