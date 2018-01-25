import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.HttpResponse;

import static spark.Spark.get;
import static spark.Spark.post;


public class Application {

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        try {
            dict.initialize();
        } catch (Exception e){

        }
        get("/hello", (request, response) -> {
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
            return Dictionary.shuffle(ss.getMysteryWord());
        });

        post("/sg/input", (request, response) -> {
            //request.queryParams();

            String guess = request.queryParams("input");

            int id = Integer.parseInt(request.queryParams("id"));

            //System.out.println(request.queryParams());
           // String guess = request.queryParams("input");

            Session ss = Session.getGame(id);

            System.out.println("Actual word " + ss.getMysteryWord());
            if (dict.wordExists(guess.toLowerCase())) {
                System.out.println(guess + " Exists");
                ss.updateScore(guess.length());
            }
            return ss.getScore();
        });

    }


}
