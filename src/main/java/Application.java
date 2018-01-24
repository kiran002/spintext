import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.HttpResponse;

import static spark.Spark.get;
import static spark.Spark.post;


public class Application {

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        get("/hello", (request, response) -> {
            return "world";
        });
        post("/sg", (request, response) -> {
            Session session = new Session();
            int gameid = session.startGame();
            System.out.println("Mystery word is : "+session.getMysteryWord());
            //response.redirect("/sg/game/" + gameid);
            return gameid;
        });

        get("/sg/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Session ss = Session.getGame(id);
            return "Hello: " + ss.getMysteryWord();
        });

        post("/sg/game/input", (request, response) -> {
            request.queryParams();
            request.raw();
            //System.out.println(request.queryParams());
            String guess = request.queryParams("input");
            return dict.wordExists(guess.toLowerCase());
        });

    }


}
