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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * PlayController.java
 * controller for the PLAY scene, sister to PLAY_ATTACK scene
 * @author dennis strots
 * @version 0.1.0
 * @since 4/27/26
 */
public class PlayController {
	@FXML
	private Rectangle endTransitionTop;
	@FXML
	private Rectangle endTransitionBottom;
	private Timeline tles;
	private Timeline tlef;
	@FXML
	private Group background;
	private TranslateTransition ttbg;
	@FXML
	private Group startArrow;
	private SequentialTransition stsa;
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
	private Rectangle pulse;
	private FadeTransition ft;
	@FXML
	private javafx.scene.control.TextField textInputField;
	@FXML
	private Label inputDisplay;

	private int guessesRemaining = 7;
	private String word = "";
	private ArrayList<Character> guessedLetters;
	private ArrayList<Character> unguessedLetters;

	public Scene buildScene() {
		URL fxmlURL = getClass().getResource("/Play.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlURL);
		Scene scene = null;
		try {
			Parent root = loader.load();
			scene = new Scene(root);
		} catch (IOException e) {
			System.err.println("PlayController buildScene failed: "+e.getMessage());
		}
		return scene;
	}

	@FXML
	public void initialize() {
		GameManager.getInstance().fetchWord();
		word = GameManager.getInstance().getRandomWord();
		guessesRemaining = 7;
		guessedLetters = new ArrayList<>();
		unguessedLetters = new ArrayList<>();
		resetAnimations();
		guessedLettersLabel.setText("Letters Guessed: ");
		indicatorHead.setVisible(false);
		indicatorBody.setVisible(false);
		indicatorArmL.setVisible(false);
		indicatorArmR.setVisible(false);
		indicatorLegL.setVisible(false);
		indicatorLegR.setVisible(false);
		buttonGroupHover.setVisible(true);

		for(char c : word.toCharArray()) {
			if(Character.isLetter(c) && !unguessedLetters.contains(c)) {
				unguessedLetters.add(c);
			}
		}
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
		if(guess.isBlank()) {
			System.out.println("Empty guess");
			invalidGuess();
		}
		else if(guess.length()==1) {
			char c = guess.charAt(0);
			if(Character.isLetter(c)) {
				if(guessedLetters.contains(c)) {
					System.out.println("Already guessed "+c);
					invalidGuess();
				} else {
					textInputField.clear();
					inputDisplay.setText("");
					guessedLetters.add(c);
					if(unguessedLetters.contains(c)) {
						updateShownWord();
						unguessedLetters.remove((Character)c);
						if(unguessedLetters.isEmpty()) {
							win();
						}
					} else {
						guessedLettersLabel.setText(guessedLettersLabel.getText()+c+" ");
						guessesRemaining--;
					}
				}
			} else {
				System.out.println(c+" is not a letter");
				invalidGuess();
			}
		} else {
			if(guess.equals(word)) {
				win();
			} else {
				textInputField.clear();
				inputDisplay.setText("");
				guessesRemaining--;
			}
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
		StringBuilder sb = new StringBuilder();
		for(char c : word.toCharArray()) {
			if(Character.isLetter(c) && unguessedLetters.contains(c)) {
				sb.append('_');
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}
		guessWordDisplay.setText(sb.toString());
	}

	private void win() {
		tles.playFromStart();
	}

	private void lose() {
		GameManager.getInstance().loseScore();
		tlef.playFromStart();
	}

	private void resetAnimations() {
		if(stsa==null) {
			TranslateTransition ttArrow0 = new TranslateTransition(Duration.seconds(.25),startArrow);
			ttArrow0.setFromX(-750);
			ttArrow0.setToX(-50);
			TranslateTransition ttArrow1 = new TranslateTransition(Duration.seconds(1),startArrow);
			ttArrow1.setToX(50);
			TranslateTransition ttArrow2 = new TranslateTransition(Duration.seconds(.25),startArrow);
			ttArrow2.setToX(750);
			stsa = new SequentialTransition(ttArrow0, ttArrow1, ttArrow2);
		}
		stsa.playFromStart();
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
		if(ttbg==null) {
			ttbg = new TranslateTransition(Duration.seconds(20),background);
			ttbg.setFromX(0);
			ttbg.setToX(-900);
			ttbg.setCycleCount(Animation.INDEFINITE);
		}
		ttbg.play();
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
