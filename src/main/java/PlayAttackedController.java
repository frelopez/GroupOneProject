import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * PlayAttackedController.java
 * controller for the PLAY_ATTACKED scene, sister to PLAY scene
 * @author dennis strots
 * @version 0.1.0
 * @since 4/27/26
 */

public class PlayAttackedController {
	@FXML
	private SVGPath bgfire0;
	@FXML
	private SVGPath bgfire1;
	@FXML
	private SVGPath bgfire2;
	@FXML
	private Rectangle endTransitionTop;
	@FXML
	private Rectangle endTransitionBottom;
	private Timeline tles;
	private Timeline tlef;
	@FXML
	private Group startWarning;
	@FXML
	private Group buttonGroupHover;
	@FXML
	private Group buttonGroup;
	private SequentialTransition stbg;
	@FXML
	private Rectangle glow;
	private TranslateTransition ttg;
	@FXML
	private Label guessedLettersLabel;
	@FXML
	private SVGPath indicatorHead;
	private TranslateTransition tth;
	@FXML
	private SVGPath indicatorBody;
	private TranslateTransition ttb;
	@FXML
	private SVGPath indicatorArmL;
	private TranslateTransition ttal;
	@FXML
	private SVGPath indicatorArmR;
	private TranslateTransition ttar;
	@FXML
	private SVGPath indicatorLegL;
	private TranslateTransition ttll;
	@FXML
	private SVGPath indicatorLegR;
	private TranslateTransition ttlr;
	@FXML
	private Label guessWordDisplay;
	@FXML
	private Label attackWordDisplay0;
	@FXML
	private Label attackWordDisplay1;
	@FXML
	private Label attackWordDisplay2;
	@FXML
	private Rectangle pulse;
	private FadeTransition ft;
	@FXML
	private javafx.scene.control.TextField textInputField;
	@FXML
	private Label inputDisplay;

	private int guessesRemaining = 7;
	private String wordR = "";
	private String word0 = null;
	private String word1 = null;
	private String word2 = null;
	private ArrayList<Character> guessedLetters;
	private ArrayList<Character> unguessedLettersR;
	private ArrayList<Character> unguessedLetters0;
	private ArrayList<Character> unguessedLetters1;
	private ArrayList<Character> unguessedLetters2;

	public Scene buildScene() {
		URL fxmlURL = getClass().getResource("/PlayAttacked.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlURL);
		Scene scene = null;
		try {
			Parent root = loader.load();
			scene = new Scene(root);
		} catch (IOException e) {
			System.err.println("PlayAttackedController buildScene failed: "+e.getMessage());
		}
		return scene;
	}

	@FXML
	public void initialize() {
		guessesRemaining = 7;
		guessedLetters = new ArrayList<>();

		GameManager.getInstance().fetchWord();
		wordR = GameManager.getInstance().getRandomWord();
		unguessedLettersR = new ArrayList<>();
		for(char c : wordR.toCharArray()) {
			if(Character.isLetter(c) && !unguessedLettersR.contains(c)) {
				unguessedLettersR.add(c);
			}
		}

		int aid0 = GameManager.getInstance().getAttackWordID0();
		int wid0 = DatabaseManager.getInstance().getAttackWord(aid0);
		unguessedLetters0 = new ArrayList<>();
		if(true || aid0 !=-1 && wid0!=-1) {
			word0 = DatabaseManager.getInstance().getWordText(wid0);

			//TODO testing purposes
			word0 = "test";

			for(char c : word0.toCharArray()) {
				if (Character.isLetter(c) && !unguessedLetters0.contains(c)) {
					unguessedLetters0.add(c);
				}
			}
		}
		else { word0 = null; }

		int aid1 = GameManager.getInstance().getAttackWordID1();
		int wid1 = DatabaseManager.getInstance().getAttackWord(aid1);
		unguessedLetters1 = new ArrayList<>();
		if(true || aid1 !=-1 && wid1!=-1) {
			word1 = DatabaseManager.getInstance().getWordText(wid1);

			//TODO testing purposes
			word1 = "attack";

			for(char c : word1.toCharArray()) {
				if (Character.isLetter(c) && !unguessedLetters1.contains(c)) {
					unguessedLetters1.add(c);
				}
			}
		}
		else { word1 = null; }

		int aid2 = GameManager.getInstance().getAttackWordID2();
		int wid2 = DatabaseManager.getInstance().getAttackWord(aid2);
		unguessedLetters2 = new ArrayList<>();
		if(true || aid2 !=-1 && wid2!=-1) {
			word2 = DatabaseManager.getInstance().getWordText(wid2);

			//TODO testing purposes
			word2 = "buzz";

			for(char c : word2.toCharArray()) {
				if (Character.isLetter(c) && !unguessedLetters2.contains(c)) {
					unguessedLetters2.add(c);
				}
			}
		}
		else { word2 = null; }

		System.out.println(wordR+"\t"+word0+"\t"+word1+"\t"+word2);

		resetAnimations();
		guessedLettersLabel.setText("Letters Guessed: ");
		indicatorHead.setVisible(false);
		indicatorBody.setVisible(false);
		indicatorArmL.setVisible(false);
		indicatorArmR.setVisible(false);
		indicatorLegL.setVisible(false);
		indicatorLegR.setVisible(false);
		buttonGroupHover.setVisible(true);
		updateShownWord();
	}
	public void onMouseEntered(MouseEvent mouseEvent) {
		buttonGroupHover.setVisible(false);
	}

	public void onMouseExited(MouseEvent mouseEvent) {
		buttonGroupHover.setVisible(true);
	}

	public void onGuess(ActionEvent actionEvent) {
		String guess = textInputField.getText().trim().toLowerCase();
		boolean safe = false;
		if(guess.isBlank()) {
			System.out.println("Empty guess");
			safe = true;
			invalidGuess();
		}
		else if(guess.length()==1) {
			char c = guess.charAt(0);
			if(Character.isLetter(c)) {
				if(guessedLetters.contains(c)) {
					System.out.println("Already guessed "+c);
					safe = true;
					invalidGuess();
				} else {
					textInputField.clear();
					inputDisplay.setText("");
					guessedLetters.add(c);
					if(unguessedLettersR.contains(c)) {
						unguessedLettersR.remove((Character)c);
						safe = true;
					}
					if(unguessedLetters0.contains(c)) {
						unguessedLetters0.remove((Character)c);
						safe = true;
					}
					if(unguessedLetters1.contains(c)) {
						unguessedLetters1.remove((Character)c);
						safe = true;
					}
					if(unguessedLetters2.contains(c)) {
						unguessedLetters2.remove((Character)c);
						safe = true;
					}
					if(!safe) {
						guessedLettersLabel.setText(guessedLettersLabel.getText()+c+" ");
					}
				}
			} else {
				System.out.println(c+" is not a letter");
				safe = true;
				invalidGuess();
			}
		} else {
			if(guess.equals(wordR)) {
				unguessedLettersR.clear();
				safe = true;
			}
			if(guess.equals(word0)) {
				unguessedLetters0.clear();
				safe = true;
			}
			if(guess.equals(word1)) {
				unguessedLetters1.clear();
				safe = true;
			}
			if(guess.equals(word2)) {
				unguessedLetters2.clear();
				safe = true;
			}
			textInputField.clear();
			inputDisplay.setText("");
		}
		if(safe) {
			if(unguessedLettersR.isEmpty() && unguessedLetters0.isEmpty()
					&& unguessedLetters1.isEmpty() && unguessedLetters2.isEmpty()) {
				win();
			}
		} else {
			guessesRemaining--;
		}

		updateShownWord();
		switch (guessesRemaining) {
			case 0:
				lose();
			case 1:
				ft.play();
				indicatorLegR.setVisible(true);
			case 2:
				indicatorLegL.setVisible(true);
			case 3:
				indicatorArmR.setVisible(true);
			case 4:
				indicatorArmL.setVisible(true);
			case 5:
				indicatorBody.setVisible(true);
			case 6:
				indicatorHead.setVisible(true);
		}
	}

	public void onKeyTyped(KeyEvent keyEvent) {
		inputDisplay.setText(textInputField.getText());
	}

	private void invalidGuess() {
		stbg.playFromStart();
	}

	private void updateShownWord() {
		StringBuilder sbR = new StringBuilder();
		for(char c : wordR.toCharArray()) {
			if(Character.isLetter(c) && unguessedLettersR.contains(c)) {
				sbR.append('_');
			} else {
				sbR.append(Character.toUpperCase(c));
			}
		}
		guessWordDisplay.setText(sbR.toString());
		if(word0!=null) {
			StringBuilder sb0 = new StringBuilder();
			for(char c : word0.toCharArray()) {
				if(Character.isLetter(c) && unguessedLetters0.contains(c)) {
					sb0.append('_');
				} else {
					sb0.append(Character.toUpperCase(c));
				}
			}
			attackWordDisplay0.setText(sb0.toString());
		}
		else { attackWordDisplay0.setText(""); }
		if(word1!=null) {
			StringBuilder sb1 = new StringBuilder();
			for(char c : word1.toCharArray()) {
				if(Character.isLetter(c) && unguessedLetters1.contains(c)) {
					sb1.append('_');
				} else {
					sb1.append(Character.toUpperCase(c));
				}
			}
			attackWordDisplay1.setText(sb1.toString());
		}
		else { attackWordDisplay1.setText(""); }
		if(word2!=null) {
			StringBuilder sb2 = new StringBuilder();
			for(char c : word2.toCharArray()) {
				if(Character.isLetter(c) && unguessedLetters2.contains(c)) {
					sb2.append('_');
				} else {
					sb2.append(Character.toUpperCase(c));
				}
			}
			attackWordDisplay2.setText(sb2.toString());
		}
		else { attackWordDisplay2.setText(""); }
	}

	private void win() {
		tles.playFromStart();
	}

	private void lose() {
		GameManager.getInstance().loseScore();
		tlef.playFromStart();
	}

	private void resetAnimations() {
		Timeline tlbf = new Timeline(
				new KeyFrame(Duration.seconds(.5), event -> bgfire1.setVisible(true)),
				new KeyFrame(Duration.seconds(.5), event -> bgfire0.setVisible(false)),
				new KeyFrame(Duration.seconds(1), event -> bgfire2.setVisible(true)),
				new KeyFrame(Duration.seconds(1), event -> bgfire1.setVisible(false)),
				new KeyFrame(Duration.seconds(1.5), event -> bgfire0.setVisible(true)),
				new KeyFrame(Duration.seconds(1.5), event -> bgfire2.setVisible(false))
		);
		tlbf.setCycleCount(Animation.INDEFINITE);
		tlbf.playFromStart();
		if(stbg==null) {
			TranslateTransition ttButtonShake0 = new TranslateTransition(Duration.seconds(.05),buttonGroup);
			ttButtonShake0.setToX(10);
			TranslateTransition ttButtonShake1 = new TranslateTransition(Duration.seconds(.1),buttonGroup);
			ttButtonShake1.setToX(-10);
			TranslateTransition ttButtonShake2 = new TranslateTransition(Duration.seconds(.05),buttonGroup);
			ttButtonShake2.setToX(0);
			stbg = new SequentialTransition(ttButtonShake0, ttButtonShake1, ttButtonShake2);
		}
		if(tles==null || tlef==null) {
			TranslateTransition ttBarTop0 = new TranslateTransition(Duration.seconds(.25),endTransitionTop);
			ttBarTop0.setToY(250);
			TranslateTransition ttBarBottom0 = new TranslateTransition(Duration.seconds(.25),endTransitionBottom);
			ttBarBottom0.setToY(-300);
			TranslateTransition ttBarTop1 = new TranslateTransition(Duration.seconds(.25),endTransitionTop);
			ttBarTop1.setToY(400);
			TranslateTransition ttBarBottom1 = new TranslateTransition(Duration.seconds(.25),endTransitionBottom);
			ttBarBottom1.setToY(-400);
			tles = new Timeline(
					new KeyFrame(Duration.ZERO, event -> ttBarTop0.play()),
					new KeyFrame(Duration.ZERO, event -> ttBarBottom0.play()),
					new KeyFrame(Duration.seconds(.5), event -> ttBarTop1.play()),
					new KeyFrame(Duration.seconds(.5), event -> ttBarBottom1.play()),
					new KeyFrame(Duration.seconds(1), event -> SceneManager.getInstance().navigateTo(SceneType.SUCCESS))
			);
			tlef = new Timeline(
					new KeyFrame(Duration.ZERO, event -> ttBarTop0.play()),
					new KeyFrame(Duration.ZERO, event -> ttBarBottom0.play()),
					new KeyFrame(Duration.seconds(.5), event -> ttBarTop1.play()),
					new KeyFrame(Duration.seconds(.5), event -> ttBarBottom1.play()),
					new KeyFrame(Duration.seconds(1), event -> SceneManager.getInstance().navigateTo(SceneType.FAILURE))
			);
		}
		if(ft==null) {
			ft = new FadeTransition(Duration.seconds(.75), pulse);
			ft.setFromValue(0);
			ft.setToValue(.5);
			ft.setAutoReverse(true);
			ft.setCycleCount(Animation.INDEFINITE);
		}
		ft.jumpTo(Duration.ZERO);
		ft.stop();
		if(ttg==null) {
			ttg = new TranslateTransition(Duration.seconds(5), glow);
			ttg.setByY(20);
			ttg.setAutoReverse(true);
			ttg.setCycleCount(Animation.INDEFINITE);
		}
		ttg.playFromStart();
		final int WAVE_PERIOD = 2;
		final int WAVINESS = 10;
		if(tth==null) {
			tth = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorHead);
			tth.setByX(4);
			tth.setAutoReverse(true);
			tth.setCycleCount(Animation.INDEFINITE);
		}
		tth.playFromStart();
		if(ttb==null) {
			ttb = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorBody);
			ttb.setByX(4);
			ttb.setAutoReverse(true);
			ttb.setCycleCount(Animation.INDEFINITE);
			ttb.setDelay(Duration.millis(WAVINESS*(indicatorBody.getLayoutY()-indicatorHead.getLayoutY())));
		}
		ttb.playFromStart();
		if(ttal==null) {
			ttal = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorArmL);
			ttal.setByX(4);
			ttal.setAutoReverse(true);
			ttal.setCycleCount(Animation.INDEFINITE);
			ttal.setDelay(Duration.millis(WAVINESS*(indicatorArmL.getLayoutY()-indicatorHead.getLayoutY())));
		}
		ttal.playFromStart();
		if(ttar==null) {
			ttar = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorArmR);
			ttar.setByX(4);
			ttar.setAutoReverse(true);
			ttar.setCycleCount(Animation.INDEFINITE);
			ttar.setDelay(Duration.millis(WAVINESS*(indicatorArmR.getLayoutY()-indicatorHead.getLayoutY())));
		}
		ttar.playFromStart();
		if(ttll==null) {
			ttll = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorLegL);
			ttll.setByX(4);
			ttll.setAutoReverse(true);
			ttll.setCycleCount(Animation.INDEFINITE);
			ttll.setDelay(Duration.millis(WAVINESS*(indicatorLegL.getLayoutY()-indicatorHead.getLayoutY())));
		}
		ttll.playFromStart();
		if(ttlr==null) {
			ttlr = new TranslateTransition(Duration.seconds(WAVE_PERIOD), indicatorLegR);
			ttlr.setByX(4);
			ttlr.setAutoReverse(true);
			ttlr.setCycleCount(Animation.INDEFINITE);
			ttlr.setDelay(Duration.millis(WAVINESS*(indicatorLegR.getLayoutY()-indicatorHead.getLayoutY())));
		}
		ttlr.playFromStart();
	}
}
