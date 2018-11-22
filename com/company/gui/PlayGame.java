package com.company.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.*;

import com.company.actor.Bomber;
import com.company.actor.Manager;


public class PlayGame extends JPanel implements Runnable,ActionListener{
	public static boolean IS_RUNNING=true;
	private MyContainer mContainer;
	private BitSet traceKey = new BitSet();		//Mảng BitSet này có thể tăng giảm kích cỡ nếu cần
	private Manager mag = new Manager();
	private int count=0;
	private int move=0;
	private int timeDead=0;
	private int timeLose=0;
	private int timeNext=0;
	private JButton butt_Menu;

	public PlayGame(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.WHITE);
		setLayout(null);
		setFocusable(true);
		addKeyListener(keyAdapter);					//Bắt sự kiện key board
		Thread mytheard = new Thread(this);
		mytheard.start();							//bắt đầu khởi động
		innitCompts();
	}

	private void innitCompts(){
		butt_Menu = new JButton();
		butt_Menu.setText("Menu");									//quay ve menu
		butt_Menu.setBounds(750, 520, 100, 30);
		butt_Menu.addActionListener(this);
		add(butt_Menu);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new java.awt.BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mag.draWBackground(g2d);
		mag.drawAllItem(g2d);
		mag.drawAllBomb(g2d);
		mag.drawAllBox(g2d);
		//vẽ quái vật
		mag.drawAllMonster(g2d);
		mag.drawAllSoot(g2d);
		mag.getmBomber().drawActor(g2d);
		mag.drawAllShawDow(g2d);
		mag.drawAllPirate(g2d);		//vẽ pirate sau khi box và shawdow đã đc vẽ
        mag.drawBoss(g2d);			//vẽ boss sau khi box và shawdow đã đc vẽ
		mag.drawInfo(g2d);

		if(mag.getStatus()==1){
			mag.drawStatus(g2d, 1);
		}
		if(mag.getStatus()==2){
			mag.drawStatus(g2d, 2);
		}
		if(mag.getStatus()==3){
			mag.drawStatus(g2d, 3);
		}
	}

	private KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			traceKey.set(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			traceKey.clear(e.getKeyCode());		//Thiết lập tất cả bit được xác định bởi index về 0
		}
	};

	@Override
	public void run() {
		//bắt sự kiện keyboard
		while(IS_RUNNING){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(traceKey.get(KeyEvent.VK_LEFT) || traceKey.get(KeyEvent.VK_A)){
				mag.getmBomber().changeOrient(Bomber.LEFT);
				mag.getmBomber().move(count,mag.getArrBomb(),mag.getArrBox());

			}
			if(traceKey.get(KeyEvent.VK_RIGHT)|| traceKey.get(KeyEvent.VK_D)){
				mag.getmBomber().changeOrient(Bomber.RIGHT);
				mag.getmBomber().move(count,mag.getArrBomb(),mag.getArrBox());
			}
			if(traceKey.get(KeyEvent.VK_UP)|| traceKey.get(KeyEvent.VK_W)){
				mag.getmBomber().changeOrient(Bomber.UP);
				mag.getmBomber().move(count,mag.getArrBomb(),mag.getArrBox());;
			}
			if(traceKey.get(KeyEvent.VK_DOWN)|| traceKey.get(KeyEvent.VK_S)){
				mag.getmBomber().changeOrient(Bomber.DOWN);
				mag.getmBomber().move(count,mag.getArrBomb(),mag.getArrBox());
			}
			if(traceKey.get(KeyEvent.VK_SPACE)){
				mag.initBomb();
				mag.getmBomber().setRunBomb(Bomber.ALLOW_RUN);

			}
			mag.setRunBomber();				//di chuyển bomber
			mag.deadLineAllBomb();			//tgian bomb nổ
			mag.checkDead();					//dead bomber
			mag.checkImpactItem();
			mag.checkWinAndLose();
			//level 1
			if(mag.getStatus()==1){
				timeLose++;
				if(timeLose == 3000){
					mag.innitManager();
					mContainer.setShowMenu();
					timeLose=0;
				}
			}
			//level 2
			if(mag.getStatus()==2){
				timeNext++;
				if(timeNext==3000){
					mag.innitManager();
					timeNext=0;
				}
			}
			//level 3
			if(mag.getStatus()==3){
				timeNext++;
				if(timeNext==3000){
					mag.innitManager();
					mContainer.setShowMenu();
					timeNext=0;
				}
			}

			if(mag.getmBomber().getStatus()==Bomber.DEAD){
				timeDead++;
				if(timeDead==3000){
					mag.setNewBomber();
					timeDead=0;
				}
			}

			if(move==0){
				mag.changeOrientAll();
				move=5000;
			}
			if(move>0){
				move--;
			}
			mag.moveSprite(count);
			repaint();		//vẽ lại hình
			count++;
			if(count==1000000){
				count=0;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==butt_Menu){
			mag.setRound(1);
			mag.innitManager();
			mContainer.setShowMenu();
		}

	}
}
