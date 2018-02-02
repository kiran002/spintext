import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private static int counter = 1;

    private static final Logger logger = LogManager.getLogger("Session");
    private static Map<Integer, Session> allSessions = new HashMap<>();
    private int gameId;
    private String mysteryWord;
    private int score;
    private List<String> guessedWords;

    Session(String mysteryWord) {
        gameId = counter++;
        this.mysteryWord = mysteryWord;
        guessedWords = new ArrayList<>();
        score = 0;
        allSessions.put(gameId, this);
    }

    public void addNewGuess(String guess) {
        guessedWords.add(guess);
    }
    // Game is completed, if either the mysterword is guessed or 20 points

    public boolean gameCompleted() {
        return guessedWords.contains(mysteryWord) || score >= 10;
    }


    public boolean wordAlreadyGuessed(String word) {
        return guessedWords.contains(word);
    }

    public String getMysteryWord() {
        return mysteryWord;
    }

    public int getGameId() {
        return gameId;
    }

    public int updateScore(int score) {
        this.score += score;
        return this.score;
    }

    public int startGame() {
        return gameId;
    }

    public static Session getGame(int sessionID) {
        return allSessions.get(sessionID);
    }

    public int getScore() {
        return score;
    }
}
