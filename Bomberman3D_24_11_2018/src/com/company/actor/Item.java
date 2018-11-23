package com.company.actor;

import java.awt.*;

import javax.swing.ImageIcon;

public class Item {
	private int x;
	private int y;
	private int type;
	private int w;
	private int h;
	private Image img;
	public Item (int x, int y, int type, String im){
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		this.img = new ImageIcon(getClass().getResource(im)).getImage();
		this.w = this.img.getWidth(null);
		this.h = this.img.getHeight(null);
	}
	public void drawItem (Graphics g2d){
		g2d.drawImage(img, x, y, null);
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return w;
	}


	public boolean isImpactItemVsBomber(Bomber bomber){
		Rectangle rec1 = new Rectangle(x, y, w, h);
		Rectangle rec2 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
		return rec1.intersects(rec2);
	}
}
