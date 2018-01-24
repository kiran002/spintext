import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private  static int counter = 1;
    private static Map<Integer,Session> allSessions = new HashMap<>();
    private int gameId;
    private Dictionary dictionary;
    private String mysteryWord;

    public Dictionary getDictionary() {
        return dictionary;
    }

    public String getMysteryWord() {
        return mysteryWord;
    }

    public int getGameId() {
        return gameId;
    }

    public Session() {
        dictionary = new Dictionary();
        gameId= counter++;
        allSessions.put(gameId,this);
    }

    public int startGame() {
        try {
            mysteryWord= dictionary.initialize();
            return gameId;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Session getGame(int sessionID) {
        return allSessions.get(sessionID);
    }
}
