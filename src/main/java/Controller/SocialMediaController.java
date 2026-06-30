package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
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
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessage);
        app.delete("messages/{message_id}", this::deleteMessage);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessageByAccount);

        app.start(8080);
        return app;
    }

    private void registerUser(Context context) throws JsonProcessingException  {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.registerUser(account);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(401);
        }
    }

    private void loginUser(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.loginUser(account);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(400);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else{
            context.status(400);
        }
    }

    private void getAllMessages(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        context.json(mapper.writeValueAsString(messages));
    }

    private void getMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.queryParam("message_id"));
        Message message = messageService.getMessage(messageId);
        context.json(mapper.writeValueAsString(message));
    }

    private void deleteMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.queryParam("message_id"));
        Message message = messageService.deleteMessage(messageId);
        context.json(mapper.writeValueAsString(message));
    }

    private void updateMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.queryParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.updateMessage(messageId, message);
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else{
            context.status(400);
        }
    }

    private void getMessageByAccount(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int accountId = Integer.parseInt(context.queryParam("account_id"));
        List<Message> messages = messageService.getMessageByAccount(accountId);
        context.json(mapper.writeValueAsString(messages));
    }
}