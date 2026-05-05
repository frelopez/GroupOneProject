/**
 * GameManager.java
 * stores words between scenes
 * @author dennis strots
 * @version 0.1.0
 * @since 5/3/26
 */

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GameManager {
	private static GameManager instance;

	private String randomWord;
	//-1 means no attacking words
	private int attackWordID0;
	private int attackWordID1;
	private int attackWordID2;
	private int earnedScore;
	private final static char[] COMMON_LETTERS = {'e','t','a','o','n','r','i','s','h','d'};

	private int selectedAttackWordId;
	private int userId;

	public void fetchWord(){
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://random-word-api.herokuapp.com/word?diff=2")
				.build();
		try (Response response = client.newCall(request).execute()) {
			String s = response.body().string().trim().toLowerCase();
			setRandomWord(s.substring(2,s.length()-2));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private GameManager() {
		randomWord = null;
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

	public void setRandomWord(String word) {
		randomWord = word;
		calculateEarnedScore();
	}

	public String getRandomWord() {
		if(randomWord==null) {
			fetchWord();
		}
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

	private void calculateEarnedScore() {
		int s = 20;
		for(char c: randomWord.toCharArray()) {
			for(char d: COMMON_LETTERS) {
				if(c==d) {
					s--;
					break;
				}
			}
		}
		if(randomWord.length()==4) {
			s*=2;
		} else if(randomWord.length()==5 || randomWord.length()==3) {
			s=(int)((double)s*1.5);
		} else if(randomWord.length()==6) {
			s=(int)((double)s*1.2);
		}
		earnedScore = s;
	}

	public void loseScore() { earnedScore-=50; }

	public int getEarnedScore() { return earnedScore; }

	public void setSendAttackWord(int wordID) {
		selectedAttackWordId = wordID;
	}
	public int getSendAttackWordId() {
		return selectedAttackWordId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
