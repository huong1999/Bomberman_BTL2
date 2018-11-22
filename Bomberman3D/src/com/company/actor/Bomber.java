package com.company.actor;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Bomber extends Actor{
	public static int ALLOW_RUN=0;					//cho phép di chuyển
	public static int DISALLOW_RUN=1;				//không cho phép di chuyển
	protected int sizeBomb,quantityBomb,status,score,heart;


	public Bomber(int x, int y, int type, int orient, int speed, int sizebomb, int quantityBomb) {
		this.x = x;
		this.y = y;
		this.typeActor = type;
		this.runBomb=DISALLOW_RUN;		//không cho phép bomber chạy qua bomb
		this.orient = orient;			//định hướng
		this.speed = speed;				//toc do
		this.sizeBomb = sizebomb;
		this.quantityBomb = quantityBomb;
		this.heart = 1;
		this.score=0;
		this.status = ALIVE;
		this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
		width = img.getWidth(null);
		height = img.getHeight(null)-20;
	}

	public void setNewStatusBomber(int x,int y) {
		this.x = x;
		this.y = y;
		this.status = ALIVE;
		this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getQuantityBomb() {
		return quantityBomb;
	}

	public void setQuantityBomb(int quantityBomb) {
		if(quantityBomb>5){
			return;
		}
		this.quantityBomb = quantityBomb;
	}


	public void setSizeBomb(int sizeBomb) {
		if(sizeBomb>3){
			return;
		}
		this.sizeBomb = sizeBomb;
	}

	public int getSizeBomb() {
		return sizeBomb;
	}

	public int getType() {
		return typeActor;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	@Override
	public boolean move(int count, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {
		if(status==DEAD){
			return false;
		}
		return super.move(count, arrBomb, arrBox);
	}

	@Override
	public void changeOrient(int orient) {
		if(this.status==DEAD){
			return;
		}
		super.changeOrient(orient);
		switch (orient) {
			case LEFT:
				img = new ImageIcon(getClass().getResource("/Images/bomber_left.png")).getImage();
				break;
			case RIGHT:
				img = new ImageIcon(getClass().getResource("/Images/bomber_right.png")).getImage();
				break;
			case UP:
				img = new ImageIcon(getClass().getResource("/Images/bomber_up.png")).getImage();
				break;
			case DOWN:
				img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
				break;
			default:
				break;
		}
	}

	public boolean isImpactBomberVsActor(Actor actor){
		if(status==DEAD){
			return false;
		}
		Rectangle rec1 = new Rectangle(x, y, width, height);
		Rectangle rec2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		return rec1.intersects(rec2);
	}

}
