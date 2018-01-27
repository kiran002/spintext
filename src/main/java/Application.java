import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.HttpResponse;


import static spark.Spark.get;
import static spark.Spark.post;


public class Application {

    private static final Logger logger = LogManager.getLogger("Application");

    public static void main(String[] args) {
        logger.info("Starting spin text API");
        logger.info("Initializing dictionary");
        Dictionary dict = new Dictionary();
        try {
            dict.initialize();
            logger.info("Initialization successful");
        } catch (Exception e){
            logger.error("Initialization failed");
            logger.error(e);
        }

        get("/hello", (request, response) -> {
            logger.info("New request received");
            return "world";
        });
        post("/sg", (request, response) -> {
            Session session = new Session(dict.getWord());
            int gameID = session.startGame();
            System.out.println("Mystery word is : "+session.getMysteryWord());
            //response.redirect("/sg/game/" + gameid);
            return gameID;
        });

        get("/sg/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Session ss = Session.getGame(id);
            logger.info("Session ID : " + id);
            String word =Dictionary.shuffle(ss.getMysteryWord());
            logger.info("Word : " + word);
            return word;
        });

        post("/sg/input", (request, response) -> {
            String guess = request.queryParams("input");
            int id = Integer.parseInt(request.queryParams("id"));
            logger.info("Session ID : " + id);
            logger.info("Guess : " + guess);
            Session ss = Session.getGame(id);
            if (!ss.wordAlreadyGuessed(guess.toLowerCase()) && dict.wordExists(guess.toLowerCase())) {
                logger.info(guess + " Exists");
                ss.addNewGuess(guess.toLowerCase());
                ss.updateScore(guess.length());

            } else {
                logger.info("word does not exist");
            }
            return ss.getScore();
        });

        post("/sg/complete", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            logger.info("Session ID : " + id);
            Session ss = Session.getGame(id);
            boolean gameCompleted = ss.gameCompleted();
            logger.info("Game completed : " + gameCompleted);
            return gameCompleted;
        });

    }


}
