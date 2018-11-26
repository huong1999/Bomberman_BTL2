// package com.company.manager;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.company.actor.*;
import sound.GameSound;

public class Manager {
	private Random random = new Random();
	private Bomber mBomber;
	private ArrayList<Box> arrBox;
	private ArrayList<Bomb> arrBomb;
	private ArrayList<BombBang> arrBombBang;
	private ArrayList<Sprite> arrSprite;
	private ArrayList<Item> arrItem;
	private ArrayList<HightScore> arrHightScore;
	private String Background;
	private int level=1;
	private int nextRound = 0;
	private int status = 0;

	public Manager() {
		innitManager();
	}		//khởi tạo cho manager

	public void innitManager() {
		switch (level) {
			case 1:
				mBomber = new Bomber(315, 495, Actor.BOMBER, Actor.DOWN, 5, 1, 1);
				innit("src/Map1/BOX.txt", "src/Map1/MONSTER.txt", "src/Map1/ITEM.txt");
				nextRound = 0;
				status = 0;
				break;
			case 2:
				mBomber.setNewStatusBomber(315, 270);
				innit("src/Map2/BOX.txt", "src/Map2/MONSTER.txt", "src/Map2/ITEM.txt");
				nextRound = 0;
				status = 0;
				break;
			case 3:
				mBomber.setNewStatusBomber(315, 495);
				innit("src/Map3/BOX.txt", "src/Map3/MONSTER.txt", "src/Map3/ITEM.txt");
				nextRound = 0;
				status = 0;
				break;

			default:
				break;
		}

	}

	public void innit(String pathBox, String pathSprite,String pathItem) {
		arrBox = new ArrayList<Box>();
		
		arrBomb = new ArrayList<Bomb>();
		arrBombBang = new ArrayList<BombBang>();
		arrSprite = new ArrayList<Sprite>();
//		arrPirate = new ArrayList<Sprite>();
		arrItem = new ArrayList<Item>();
		arrHightScore = new ArrayList<HightScore>();

		innitArrBoxAndShawdow(pathBox);
		initarrSprite(pathSprite);
		initArrItem(pathItem);
		initArrHightScore("src/hightscore/HightScore.txt");
	}


	public void innitArrBoxAndShawdow(String pathBox) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(pathBox));
			Background = input.readLine();
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int type = Integer.parseInt(str[2]);
				String images = str[3];
				Box box = new Box(x, y, type, images);
				arrBox.add(box);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	public void initBomb() {
		if (mBomber.getStatus() == Bomber.DEAD) {
			return;
		}
		int x = mBomber.getX() + mBomber.getWidth() / 2;
		int y = mBomber.getY() + mBomber.getHeart() / 2;
		for (int i = 0; i < arrBomb.size(); i++) {
			if (arrBomb.get(i).isImpact(x, y)) {
				return;
			}
		}

		if (arrBomb.size() >= mBomber.getQuantityBomb()) {
			return;
		}
		GameSound.getIstance().getAudio(GameSound.BOMB).play();
		Bomb mBomb = new Bomb(x, y, mBomber.getSizeBomb(), 1000);			//khởi tạo bomb vs tgian nổ bomb là 1000 milis
		arrBomb.add(mBomb);
	}

	public void initArrItem(String path) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int type = Integer.parseInt(str[2]);
				String images = str[3];
				Item item = new Item(x, y, type, images);
				arrItem.add(item);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initarrSprite(String path) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));		//đọc file txt
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int typeSprite = Integer.parseInt(str[2]);
				int orient = Integer.parseInt(str[3]);
				int speed = Integer.parseInt(str[4]);
				int heart = Integer.parseInt(str[5]);
				String images = str[6];
				Sprite sprite = new Sprite(x, y, typeSprite, orient, speed, heart,images);
//				Sprite pirate = new Sprite(x, y, typeMonster, orient, speed, heart, images);
				arrSprite.add(sprite);
//				arrPirate.add(pirate);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initArrHightScore(String path){
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				String name = str[0];
				int score = Integer.parseInt(str[1]);
				arrHightScore.add(new HightScore(name, score));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawAllBox(Graphics2D g2d) {
		for (int i = 0; i < arrBox.size(); i++) {
			arrBox.get(i).drawBox(g2d);
		}
	}


	public void draWBackground(Graphics2D g2d) {
		Image imgBackground = new ImageIcon(getClass().getResource(Background)).getImage();
		g2d.drawImage(imgBackground, 0, 0, null);
	}
//vẽ score trên TextArea Infor
	public void drawInfo(Graphics2D g2d) {
		Image imgInfor = new ImageIcon(getClass().getResource(
				"/Images/background_Info.png")).getImage();
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.setColor(Color.RED);
		g2d.drawImage(imgInfor, 675, 0, null);
		g2d.drawString("YOUR HEART", 726, 100);
		Image heart = new ImageIcon(getClass().getResource("/Images/heart_1.png")).getImage();
		for (int i = 0; i < mBomber.getHeart(); i++) {
            		g2d.drawImage(heart, 750 + i * 25, 120, null);
        	}

		g2d.drawString("SCORE : " + mBomber.getScore(), 740, 200);
	}
//vẽ bomb
	public void drawAllBomb(Graphics2D grp2d) {
		for (int i = 0; i < arrBomb.size(); i++) {
			arrBomb.get(i).drawActor(grp2d);
		}
		for (int i = 0; i < arrBombBang.size(); i++) {
			arrBombBang.get(i).drawBombBang(grp2d);
		}
	}
	public void drawAllItem(Graphics2D g2d) {
		for (int i = 0; i < arrItem.size(); i++) {
			arrItem.get(i).drawItem(g2d);
		}
	}
//vẽ monster
	public void drawAllMonster(Graphics2D grp2d) {
		for (int i = 0; i < arrSprite.size(); i++) {
			arrSprite.get(i).drawActor(grp2d);
		}
	}
//vẽ boss
	public void drawBoss(Graphics2D grp2d) {
		for (int i = 0; i < arrSprite.size(); i++) {
			arrSprite.get(i).drawBoss(grp2d);
		}
	}
	//vẽ quái vật bồ hóng
	public void drawAllSoot(Graphics2D grp2d) {
		for (int i = 0; i < arrSprite.size(); i++) {
			arrSprite.get(i).drawAllSoot(grp2d);
		}
	}
	//vẽ quái vật cướp biển
	public void drawAllPirate(Graphics2D grp2d) {
		for (int i = 0; i < arrSprite.size(); i++) {
			arrSprite.get(i).drawAllPirate(grp2d);
		}
	}
//Label khi win và lose
	public void drawStatus(Graphics2D g2d, int type) {
		g2d.setFont(new Font("Arial", Font.BOLD, 70));
		g2d.setColor(Color.RED);
		if(type==1){
			g2d.drawString("You Lose !!!", 200, 250);
		}else{
			if(type==2){
				g2d.drawString("Level "+level, 200, 250);
			}else{
				g2d.drawString("You Win !!!", 200, 250);
			}
		}
	}
	//dead bomber
	public void checkDead() {
		for (int i = 0; i < arrBombBang.size(); i++) {
			if (arrBombBang.get(i).isImpactBombBangVsActor(mBomber)  && mBomber.getStatus()==Bomber.ALIVE ) {
				mBomber.setImg(new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage());
				if (mBomber.getStatus() == Bomber.DEAD) {
					return;
				}
				mBomber.setHeart(mBomber.getHeart() - 1);
				mBomber.setStatus(Bomber.DEAD);
				GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
			}
		}
		for (int i = 0; i < arrSprite.size(); i++) {
			if (mBomber.isImpactBomberVsActor(arrSprite.get(i))) {
				Image icon = new ImageIcon(getClass().getResource("/Images/ghost.png")).getImage();
				mBomber.setImg(icon);
				if (mBomber.getStatus() == Bomber.DEAD) {			//nếu bomber đã die thì sau va chạm tiếp giữa bomber và sprite sẽ ko die tiếp nữa
					return;
				}
				mBomber.setHeart(mBomber.getHeart() - 1);		//giảm mạng
				mBomber.setStatus(Bomber.DEAD);
				GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
			}
		}
	}
//check win or lose of bomber
	public void checkWinAndLose() {
		if (mBomber.getHeart() == 0 && nextRound == 0) {
			level = 1;
			status = 1;
			nextRound++;
			GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
			GameSound.getIstance().getAudio(GameSound.LOSE).play();
			saveScore();
		}
		if (arrSprite.size() == 0 && nextRound == 0) {
			if (level == 3) {
				status = 3;
				nextRound++;
				GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
				GameSound.getIstance().getAudio(GameSound.WIN).play();
				saveScore();
				level = 1;
				return;
			}
			if (checkImpactpt()) {
				level++;
				nextRound++;
				status = 2;
                                 
			}
		}

	}
	public void checkImpactItem() {
        for (int i = 0; i < arrItem.size(); i++) {
            if (arrItem.get(i).isImpactItemVsBomber(mBomber)) {

                if (arrItem.get(i).getType() == 1) {                    // them so luong bom
                    GameSound.instance.getAudio(GameSound.ITEM).play();
                    mBomber.setQuantityBomb(mBomber.getQuantityBomb() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == 2) {                // tang kich co bom
                    GameSound.instance.getAudio(GameSound.ITEM).play();
                    mBomber.setSizeBomb(mBomber.getSizeBomb() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == 3) {                //tang toc do bomber
                    GameSound.instance.getAudio(GameSound.ITEM).play();
                    mBomber.setSpeed(mBomber.getSpeed() - 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == 5) {                // them mang neu mang < 3
                    GameSound.instance.getAudio(GameSound.ITEM).play();
                    arrItem.remove(i);

                    if (mBomber.getHeart() < 3) {
                        mBomber.setHeart(mBomber.getHeart() + 1);
                    }

                    break;
                }
                if (arrItem.get(i).getType() == 6) {                // bẫy skull -> mất mạng

                    arrItem.remove(i);

                    Image icon = new ImageIcon(getClass().getResource("/Images/ghost.png")).getImage();
                    mBomber.setImg(icon);
                    if (mBomber.getStatus() == Bomber.DEAD) {			//nếu bomber đã die thì sau va chạm tiếp giữa bomber và sprite sẽ ko die tiếp nữa
                        return;
                    }
                    mBomber.setHeart(mBomber.getHeart() - 1);		//giảm mạng
                    mBomber.setStatus(Bomber.DEAD);
                    GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();

                    break;
                }
                /*//logic portal
				if (arrItem.get(i).getType() == Item.Item_Portal) {
					//mBomber.setSpeed(mBomber.getSpeed() - 1);
					arrItem.remove(i);
					break;
				}
                 */
            }
        }
    }

	//logic portal
  public boolean checkImpactpt(){
            int i = 0;
            for ( i = 0; i < arrItem.size(); i++) {
                if (arrItem.get(i).getType() == 4 && arrItem.get(i).isImpactItemVsBomber(mBomber) && arrSprite.isEmpty()){
                    arrItem.remove(i);
                    return true;
                }
            }
                 return false;
           
        }


	public void deadLineAllBomb() {
		for (int i = 0; i < arrBomb.size(); i++) {
			arrBomb.get(i).deadlineBomb();
			if (arrBomb.get(i).getTimeline() == 250) {
				GameSound.getIstance().getAudio(GameSound.BOMB_BANG).play();
				arrBombBang.add(new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize(), arrBox));
				arrBomb.remove(i);
			}
		}
		for(int j=0;j<arrSprite.size();j++){
			for(int i=0;i<arrBomb.size();i++){
				if(arrBomb.get(i).isImpactBombvsActor(arrSprite.get(j))==2){			//xử lí khi bomb nổ chạm vào boss
					GameSound.getIstance().getAudio(GameSound.BOMB_BANG).play();				//âm thanh
					arrBombBang.add(new BombBang(arrBomb.get(i).getX(), arrBomb.get(i).getY(), arrBomb.get(i).getSize(), arrBox));
					arrBomb.remove(i);
				}
			}
		}

		for (int i = 0; i < arrBombBang.size(); i++) {
			for (int j = 0; j < arrBomb.size(); j++) {			//xử lí khi bomb nổ gần bomb khác
				if (arrBombBang.get(i).isImpactBombBangvsBomb(arrBomb.get(j))) {
					arrBombBang.add(new BombBang(arrBomb.get(j).getX(), arrBomb.get(j).getY(), arrBomb.get(j).getSize(), arrBox));
					arrBomb.remove(j);
				}
			}
		}
		for (int k = 0; k < arrBombBang.size(); k++) {
			arrBombBang.get(k).deadlineBomb();
			for (int j = 0; j < arrSprite.size(); j++) {
				if (arrBombBang.get(k).isImpactBombBangVsActor(arrSprite.get(j))) {			// xử lí khi bomb nổ chạm vào các Actor

					if(arrSprite.get(j).getHeart()>1){
						arrSprite.get(j).setHeart(arrSprite.get(j).getHeart()-1);
					}
					else {
						//tăng điểm trước khi xóa sprite  ra khỏi arraylist
						if(arrSprite.get(j).getType()==Actor.BOSS){
							mBomber.setScore(mBomber.getScore() + 10);
						}
						else if(arrSprite.get(j).getType()==Actor.PIRATE){
							mBomber.setScore(mBomber.getScore() + 5);
						}
						else if(arrSprite.get(j).getType()==Actor.SOOT){
							mBomber.setScore(mBomber.getScore() + 2);
						}
						else{
							mBomber.setScore(mBomber.getScore() + 1);
						}
						GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
						arrSprite.remove(j);
					}
				}
			}
			if (arrBombBang.get(k).getTimeLine() == 0) {
				arrBombBang.remove(k);
			}
		}
		for (int i = 0; i < arrBombBang.size(); i++) {
			for (int j = 0; j < arrBox.size(); j++) {
				if (arrBombBang.get(i).isImpactBombBangvsBox(arrBox.get(j))) {
					arrBox.remove(j);
					
				}
			}
		}
		
// 		for (int i = 0; i < arrBombBang.size(); i++) {
// 			for (int j = 0; j < arrItem.size(); j++) {
// 				if (arrBombBang.get(i).isImpactBombBangvsItem(arrItem.get(j))) {
// 					arrItem.remove(j);
// 				}
// 			}
// 		}
		
	}

	public void setNewBomber() {
		switch (level) {
			case 1:
				mBomber.setNewStatusBomber(315, 495);
				break;
			case 2:
				mBomber.setNewStatusBomber(315, 270);
				break;
			case 3:
				mBomber.setNewStatusBomber(0, 540);
				break;

			default:
				break;
		}
	}
	//đặt lại vị trí bomber
	public void setRunBomber() {
		if (arrBomb.size() > 0) {
			if (arrBomb.get(arrBomb.size() - 1).setRun(mBomber) == false) {
				mBomber.setRunBomb(Bomber.DISALLOW_RUN);
			}
		}
	}

	public void changeOrientAll() {
		for (int i = 0; i < arrSprite.size(); i++) {
			int orient = random.nextInt(4) + 1;
			arrSprite.get(i).changeOrient(orient);
		}
	}

	public void moveSprite(int count) {
		for (int i = 0; i < arrSprite.size(); i++) {
			if (arrSprite.get(i).move(count, arrBomb, arrBox) == false) {
				int orient = random.nextInt(4) + 1;
				arrSprite.get(i).changeOrient(orient);
			}
		}
	}


	public void saveScore(){
		boolean tmp=true;
		System.out.println();
		//xử lí try-catch cho trường hợp kích close vào Frame Message nhập tên
		try {
			//xử lí thêm trường hợp điểm bomber =0(return;)
			if ((arrHightScore.size()<10 && mBomber.getScore() !=0) || mBomber.getScore() > arrHightScore.get(arrHightScore.size() - 1).getScore()) {
				//xử lí khi chưa nhập tên hoặc nhập tên quá dài
				while (tmp == true) {
						String name = JOptionPane.showInputDialog("Mời nhập tên của bạn");            //thong bao nhap ten diem cao
						if (name.length() <= 10 && name.length() > 0) {
							arrHightScore.add(new HightScore(name, mBomber.getScore()));        //them dong text vua nhap vao arrlist hightscore
							JOptionPane.showMessageDialog(null, "Tên của bạn đã được thêm vào HighScore!!!");
							tmp = false;
						} else if(name.length() > 10){
							JOptionPane.showMessageDialog(null, "Ban khong duoc nhap qua 10 ki tu");
							tmp = true;
						}
						else{
							JOptionPane.showMessageDialog(null, "Ban chu nhap ten\nMoi nhap lai!!!");
							tmp = true;
						}
				}
			}
		}catch (NullPointerException e) {
			e.fillInStackTrace();
		}
		Collections.sort(arrHightScore, new Comparator<HightScore>() {				//sap xep hightscore

			@Override
			public int compare(HightScore score1, HightScore score2) {
				if(score1.getScore()<score2.getScore()){
					return 1;
				}else{
					if(score1.getScore()==score2.getScore()){
						return 0;
					}else{
						return -1;
					}
				}
			}
		});

		if(arrHightScore.size()>10){
			arrHightScore.remove(arrHightScore.size()-1);
		}

		try {
			FileOutputStream fileOutput = new FileOutputStream("src/hightscore/HightScore.txt");
			if(arrHightScore.size() == 0){
				String content = arrHightScore.get(0).getName()+":"+arrHightScore.get(0).getScore()+"\n";
				if(arrHightScore.get(0).getScore()!=0)
					fileOutput.write(content.getBytes());
			}
			else
				for(int i=0;i<arrHightScore.size();i++){
					String content = arrHightScore.get(i).getName()+":"+arrHightScore.get(i).getScore()+"\n";
					fileOutput.write(content.getBytes());
				}
		} catch (IOException e ) {
			e.printStackTrace();
		}
	}

	public ArrayList<Box> getArrBox() {
		return arrBox;
	}

	public ArrayList<Bomb> getArrBomb() {
		return arrBomb;
	}

	public Bomber getmBomber() {
		return mBomber;
	}

	public int getStatus() {
		return status;
	}

	public void setRound(int round) {
		this.level = 1;
	}

}
