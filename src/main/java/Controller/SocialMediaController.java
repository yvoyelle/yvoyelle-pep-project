package Controller;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Services.AccountService;
import Services.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegister);

        app.post("login", this::Login);
        app.post("messages", this::createMessage);
        app.get("messages", this::SelectAllMessage);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessageById);
        app.get("accounts/{account_id}/messages", this::SelectMessagesByAccountId);

        return app;
    }

    private void userRegister(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountRegistration = accountService.addUser(account);

        if (accountRegistration != null) {
            ctx.status(200).json(accountRegistration);
        } else {
            ctx.status(400);
        }

    }

    private void Login(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account authUser = accountService.getUser(account);

        if (authUser != null) {
            ctx.status(200).json(authUser);
        } else {
            ctx.status(401);
        }

    }

    private void createMessage(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class); 
        Message createdMessage = messageService.createMessage(message); 

        if (createdMessage == null) {
            ctx.status(400);
        } else {
            ctx.status(200).json(mapper.writeValueAsString(createdMessage)); 
        }

    }

    public void SelectAllMessage(Context ctx) {

        List<Message> messages = messageService.getAllMessages();

        if (messages != null) {
            ctx.status(200).json(messages); 
        } else {
            ctx.status(400);

        }

    }

    public void getMessageById(Context ctx) {

        int messageId = Integer.parseInt(ctx.pathParam("message_id")); // Extract message_id from URL
        Message message = messageService.getMessageById(messageId); 

        if (message != null) {
            ctx.json(message); 
        } else {
            ctx.json(""); 
    }}

    public void SelectMessagesByAccountId(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id")); // Extract account_id from URL
        List<Message> messages = messageService.SelectMessagesByAccountId(accountId); // Retrieve messages

        ctx.status(200).json(messages); 
    }

    private void deleteMessageById(Context ctx) {

        int messageId = Integer.parseInt(ctx.pathParam("message_id")); // Extract message_id from URL
        Message deleted = messageService.deleteMessageById(messageId);

        if (deleted != null) {
            ctx.status(200).json(deleted);
        } else {
            ctx.status(200).json("");
        }
    }

    public void updateMessageById(Context ctx)throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> requestBody = mapper.readValue(ctx.body(), Map.class);
            String newMessageText = (String) requestBody.get("message_text");

            Message updatedMessage = messageService.updateMessage(messageId, newMessageText);

            if (updatedMessage == null) {
                ctx.status(400);
            } else {
                ctx.json(mapper.writeValueAsString(updatedMessage));
            }

       
    }

}