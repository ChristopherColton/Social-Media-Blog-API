package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;
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
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createMessage(Context context)
    {

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