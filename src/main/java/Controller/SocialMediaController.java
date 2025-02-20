package Controller;

import Model.Account;
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


    public void createMessage( Context ctx){
        //Account account =ctx.bodyAsClass(Account.class);
        //Account createMessage= accountService.addUser(account);
        Message message = ctx.bodyAsClass(Message.class);
       Message createMessage= messageService.createMessa(message);


       if(createMessage !=null){
           ctx.status(200).json(createMessage);
       }else{
           ctx.status(400);
       }

    }
}