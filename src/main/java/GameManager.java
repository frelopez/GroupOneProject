/**
 * GameManager.java
 * stores words between scenes
 * @author dennis strots
 * @version 0.1.0
 * @since 5/3/26
 */

public class GameManager {
	private static GameManager instance;

	private String randomWord;
	//-1 means no attacking words
	private int attackWordID0;
	private int attackWordID1;
	private int attackWordID2;
	private GameManager() {
		randomWord = "";
		attackWordID0 = -1;
		attackWordID1 = -1;
		attackWordID2 = -1;
	}

	public static GameManager getInstance() {
		if(instance==null) {
			instance=new GameManager();
		}
		return instance;
	}

	//TODO a fetchRandomWord method that gets a word from SOME external library.
	//TODO put that word into randomWord and return it

	public void setRandomWord(String word) {
		randomWord = word;
	}

	public String getRandomWord() {
		return randomWord;
	}

	public void setAttackWordID0(int id) {
		attackWordID0 = id;
	}

	public int getAttackWordID0() {
		return attackWordID0;
	}

	public void setAttackWordID1(int id) {
		attackWordID1 = id;
	}

	public int getAttackWordID1() {
		return attackWordID1;
	}

	public void setAttackWordID2(int id) {
		attackWordID2 = id;
	}

	public int getAttackWordID2() {
		return attackWordID2;
	}
}
