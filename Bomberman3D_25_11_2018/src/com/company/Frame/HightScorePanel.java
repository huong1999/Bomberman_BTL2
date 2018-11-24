package com.company.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import com.company.actor.HightScore;


public class HightScorePanel extends JPanel implements ActionListener{
	private MyContainer mContainer;
	private Image backgroundImage;
	private JButton OK;
	private JLabel lab;
	private JLabel lab1;
	private JLabel lab2;
	private ArrayList<HightScore> arrHightScore;

	public HightScorePanel(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.BLACK);
		setLayout(null);
		initCompts();
	}
	
	public void initCompts(){
		ReadFileHightScore();
		lab = new JLabel();
		lab.setText("NO.");
		lab.setBounds(222,80,140,50);
		lab.setFont(new Font("Arial", Font.BOLD, 22));
		lab.setForeground(Color.RED);
		add(lab);
		lab1 = new JLabel();
		lab1.setText("NAME");
		lab1.setBounds(410,80,140,50);
		lab1.setFont(new Font("Arial", Font.BOLD, 22));
		lab1.setForeground(Color.RED);
		add(lab1);
		lab2 = new JLabel();
		lab2.setText("SCORE");
		lab2.setBounds(610,80,140,50);
		lab2.setFont(new Font("Arial", Font.BOLD, 22));
		lab2.setForeground(Color.RED);
		add(lab2);
		OK = new JButton();
		OK.setText("OK");
		OK.setBounds(400, 520, 100, 40);
		OK.addActionListener(this);
		add(OK);
	}
	@Override
	protected void paintComponent(Graphics g) {
		ReadFileHightScore();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D )g;
		drawImage(g2d);
		drawHightScore(g2d);
	}
	
	public void drawImage(Graphics2D g2d){
		Image background = new ImageIcon(getClass().getResource("/Images/background_hightscore.png")).getImage();
		g2d.drawImage(background, 0, -9, null);
	}
	
	public void drawHightScore(Graphics2D g2d){
		g2d.setStroke(new java.awt.BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("Arial", Font.BOLD, 28));
		g2d.setColor(Color.RED);	
		//g2d.drawRect(250, 50, 400, 450);
		g2d.setColor(Color.YELLOW);
		int y=160;
		for(int i=0;i<arrHightScore.size();i++){
			if(i<9)
				g2d.drawString(""+(i+1), 230, y);
			else
				g2d.drawString(""+(i+1), 223, y);

			if(arrHightScore.get(i).getName().length()<=10)
				g2d.drawString(""+arrHightScore.get(i).getName(), 370, y);

			g2d.drawString(""+arrHightScore.get(i).getScore(), 638, y);
			y=y+35;
		}
		
	}
	
	public void ReadFileHightScore(){
		arrHightScore = new ArrayList<HightScore>();
		String line;
		int sizeHightScore=0;
		try {
			BufferedReader input = new BufferedReader(new FileReader("src/hightscore/HightScore.txt"));
			while (sizeHightScore < 10 ) {
				line = input.readLine();

				if(line == null)			//xử lí trường hợp highscore rỗng
					return;

				String str[] = line.split(":");
				String name = str[0];
				int score = Integer.parseInt(str[1]);
				HightScore hightScore = new HightScore(name, score);
				arrHightScore.add(hightScore);
				sizeHightScore++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==OK){
			mContainer.setShowMenu();
		}	
	}
}
