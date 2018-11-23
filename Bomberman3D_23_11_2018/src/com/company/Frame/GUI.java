package com.company.Frame;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import sound.GameSound;

public class GUI extends JFrame{
	public static final int WIDTHJF = 905;
	public static final int HEIGHTJF = 610;
	private MyContainer mContainer;
//	private JLabel game;

	public GUI() {
		setSize(WIDTHJF, HEIGHTJF);
		setTitle("Game bomberman");
/*//nhãn game tại menu
		game = new JLabel("BOMBERMAN");
		game.setBounds(190, 80, 300, 30);
		game.setFont(new java.awt.Font("Showcard Gothic", 1, 24));
		game.setForeground(new java.awt.Color(255, 0, 0));
*/
		setLayout(new CardLayout());
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		mContainer = new MyContainer(this);
		add(mContainer);
		addWindowListener(mwindow);
	}

	private WindowAdapter mwindow = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			GameSound.getIstance().stop();
			PlayGame.IS_RUNNING = false;
		}
	};
}
