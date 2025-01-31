package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService 
{
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(String username, String password)
    {
        return accountDAO.createAccount(username, password);
    }

    public Account login(String username, String password)
    {
        return accountDAO.login(username, password);
    }
}
