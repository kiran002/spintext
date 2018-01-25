import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private  static int counter = 1;
    private static Map<Integer,Session> allSessions = new HashMap<>();
    private int gameId;
    private String mysteryWord;
    private int score;


    public String getMysteryWord() {
        return mysteryWord;
    }

    public int getGameId() {
        return gameId;
    }

    public Session(String mysteryWord) {
        gameId= counter++;
        this.mysteryWord = mysteryWord;
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
