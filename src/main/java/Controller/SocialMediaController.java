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
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService= new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
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


        
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void userRegister(Context ctx) {
        
        Account account =ctx.bodyAsClass(Account.class);
        Account accountRegistration= accountService.addUser(account);
        
        if(accountRegistration !=null ){
          
          
            ctx.status(200).json(accountRegistration); // Success response with 201 status
        }else {
            ctx.status(400);
        }
        
        
    }

    private void Login( Context ctx){

        Account account =ctx.bodyAsClass(Account.class);
        Account authUser= accountService.getUser(account);


        if( authUser != null){
            ctx.status(200).json(authUser);
        }else{
            ctx.status(401);
        }

    }

    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
            Message message = mapper.readValue(ctx.body(), Message.class);  // Read the message from the request body
            Message createdMessage = messageService.createMessage(message);  // Create the message using the service
    
            if (createdMessage == null) {
                ctx.status(400);  // If creation fails, return 400 status
            } else {
                ctx.status(200).json(mapper.writeValueAsString(createdMessage));  // Return the created message as JSON
            }
     
    }
    
      public void SelectAllMessage(Context ctx) {

        List<Message> messages = messageService.getAllMessages(); // Fetch all messages
        if (messages != null ){
            ctx.status(200).json(messages); // Jackson automatically serializes the list to JSON

        }else{

            ctx.status(401);
 
        }
        
    }

    
    public void getMessageById(Context ctx) {

        int messageId = Integer.parseInt(ctx.pathParam("message_id")); // Extract message_id from URL
        Message message = messageService.getMessageById(messageId); // Call service method
    
        if (message != null) {
            ctx.json(message); // Return the message as JSON
        } else {
            ctx.json(""); // Return an empty response if message not found
        }
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
  

  private void updateMessageById( Context ctx) throws JsonProcessingException{

    ObjectMapper mapper = new ObjectMapper();

    Message message = mapper.readValue(ctx.body(), Message.class);

    int messageId = Integer.parseInt(ctx.pathParam  ("message_id"));

Message updatMessage= messageService.deleteMessageById(messageId);

System.out.println(updatMessage);

    if(updatMessage !=null){
    ctx.status(200).json(message);

    }else{

        ctx.status(400).json(mapper.writeValueAsString(updatMessage));
    }
  }
     
     

    
}