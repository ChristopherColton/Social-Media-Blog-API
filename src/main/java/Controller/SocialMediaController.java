package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;
import Service.MessageService;
import java.util.List;

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
        app.get("/messages", this::getAllMessages);
        app.post("/register", this::createAccount);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        return app;
    }

    private void createMessage(Context context)
    {
        try
        {
            if(context.body().isEmpty())
            {
                context.status(400).json("");
                return;
            }
        
            
            Message message = context.bodyAsClass(Message.class);
            

            if(message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255)
            {
                context.status(400).json("");
                return;
            }

            if(message.getPosted_by() <= 0)
            {
                context.status(400).json("");
                return;
            }

            long time_posted_epoch = message.getTime_posted_epoch();
            MessageService messageService = new MessageService(new MessageDAO());
            Message createMsg = messageService.createMessage(message.getMessage_text(), message.getPosted_by(), time_posted_epoch);

            if(createMsg != null)
            {
                context.status(200).json(createMsg);
            }
            else
            {
                context.status(400).json("");
            }

        }
        catch(Exception e)
        {
            context.status(400).json("");
        }
    }

    private void getAllMessages(Context context) 
    {
        MessageDAO dao = new MessageDAO();
        List<Message> messages = dao.getAllMessages();
        context.json(messages);
    }

    private void createAccount(Context context) 
    {
        AccountDAO dao = new AccountDAO();
        Account newAccount = context.bodyAsClass(Account.class);

        String username = newAccount.getUsername();
        String password = newAccount.getPassword();

        if(username == null || password == null)
        {
            context.status(400).json("Please provide both a username or password");
            return;
        }

        Account addAccount = dao.createAccount(username, password);

        if(addAccount != null)
        {
            context.status(200).json(addAccount);
        }
        else
        {
            context.status(400).json("");
        }
    }

    private void login(Context context)
    {
        AccountDAO dao = new AccountDAO();
        Account login = context.bodyAsClass(Account.class);

        String username = login.getUsername();
        String password = login.getPassword();

        if(username == null || password == null)
        {
            context.status(400).json("Please provide both a username or password");
            return;
        }

        Account loginUser = dao.login(username, password);

        if(loginUser != null)
        {
            context.status(200).json(loginUser);
        }
        else
        {
            context.status(401).json("");
        }
    }
}