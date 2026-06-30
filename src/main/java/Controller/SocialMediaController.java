package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
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
        app.post("login", this::loginUser);
        app.post("messages", this::createMessage);
        app.get("messages", this::getMessages);
        app.get("messages/{message_id}", this::getMessage);
        app.delete("messages/{message_id}", this::deleteMessage);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessageByAccount);

        return app;
    }

    private void registerUser(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = socialMediaService.registerUser(account);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(401);
        }
    }

    private void loginUser(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = socialMediaService.loginUser(account);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(400);
        }
    }

    private void createMessage(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = socialMediaService.createMessage(message);
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else{
            context.status(400);
        }
    }

    private void getMessages(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Message[] messages = socialMediaService.getMessages();
        context.json(mapper.writeValueAsString(messages));
    }

    private void getMessage(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        String messageId = context.queryParam("message_id");
        Message message = socialMediaService.getMessage(messageId);
        context.json(mapper.writeValueAsString(message));
    }

    private void deleteMessage(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        String messageId = context.queryParam("message_id");
        Message message = socialMediaService.deleteMessage(messageId);
        context.json(mapper.writeValueAsString(message));
    }

    private void updateMessage(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        String messageId = context.queryParam("message_id");
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = socialMediaService.updateMessage(messageId, message);
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else{
            context.status(400);
        }
    }

    private void getMessageByAccount(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        String accountId = context.queryParam("account_id");
        Message[] messages = socialMediaService.getMessageByAccount(accountId);
        context.json(mapper.writeValueAsString(messages));
    }
}