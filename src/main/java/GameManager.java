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

	private int sendAttackWordId;
	private int userId;
	private String getword(){
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://random-word-api.herokuapp.com/word")
				.build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private GameManager() {
		randomWord = getword();
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

	public void setSendAttackWord(int wordID) {
		sendAttackWordId = wordID;
	}
	public int getSendAttackWordId() {
		return sendAttackWordId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
