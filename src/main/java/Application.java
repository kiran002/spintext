import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.HttpResponse;

import static spark.Spark.get;
import static spark.Spark.post;


public class Application {

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        get("/hello", (request, response) -> {
            return  "world";
        });
        post("/sg", (request, response) -> {
            try {
                dict.initialize();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return Dictionary.shuffle(dict.getNewWord());
        });
        post("/sg/game/input", (request, response) -> {
            request.queryParams();
            request.raw();
            //System.out.println(request.queryParams());
            String guess =  request.queryParams("input");
            return  dict.wordExists(guess.toLowerCase());
        });

    }


}
