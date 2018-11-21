package sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;

public class GameSound {
	public static GameSound instance;

	public static final String MENU = "NhocMaruko.wav";						//nhạc menu
	public static final String PLAYGAME = "playgame.wav";				//nhạc play
	public static final String BOMB = "newbomb.wav";					//nhạc đặt bom
	public static final String BOMBER_DIE = "bomber_die.wav";			//nhạc bomber die
	public static final String MONSTER_DIE = "monster_die.wav";		//nhạc monster die
	public static final String BOMB_BANG = "bomb_bang.wav";			//nhạc bom nổ
	public static final String ITEM = "item.wav";						//nhạc khi xuất hiện item
	public static final String WIN = "win.wav";						//nhạc khi win 1 level
	public static final String LOSE = "lose.mid";						//nhạc khi thua game

	private HashMap<String, AudioClip> audioMap;

	public GameSound() {
		audioMap = new HashMap<>();		//tao hashmap audioMap
		loadAllAudio();
	}

	public static GameSound getIstance() {
		if (instance == null) {
			instance = new GameSound();
		}

		return instance;
	}
//thêm tất cả các âm thah vào hashmap
	public void loadAllAudio() {
		setAudio(MENU);
		setAudio(PLAYGAME);
		setAudio(BOMB);
		setAudio(MONSTER_DIE);
		setAudio(BOMBER_DIE);
		setAudio(BOMB_BANG);
		setAudio(ITEM);
		setAudio(WIN);
		setAudio(LOSE);
	}

	public void stop() {
		getAudio(MENU).stop();
		getAudio(PLAYGAME).stop();
		getAudio(BOMB).stop();
		getAudio(BOMB_BANG).stop();
		getAudio(WIN).stop();
		getAudio(LOSE).stop();
	}

	public void setAudio(String name) {
		audioMap.put(name, Applet.newAudioClip(GameSound.class.getResource(name)));
	}

	public AudioClip getAudio(String name) {
		return audioMap.get(name);
	}
}
