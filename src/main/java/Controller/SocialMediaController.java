package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("register", this::registerUser);

        return app;
    }

    private void registerUser(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Account author = mapper.readValue(context.body(), Account.class);
        Account newAccount = socialMediaService.addAuthor(author);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(400);
        }
    }
}