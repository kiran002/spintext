import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private  static int counter = 1;
    private static Map<Integer,Session> allSessions = new HashMap<>();
    private int gameId;
    private String mysteryWord;
    private int score;

    private List<String> guessedWords;

    public void addNewGuess(String guess) {
        guessedWords.add(guess);
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

    public Session(String mysteryWord) {
        gameId= counter++;
        this.mysteryWord = mysteryWord;
        guessedWords = new ArrayList<>();
        score=0;
        allSessions.put(gameId,this);
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
