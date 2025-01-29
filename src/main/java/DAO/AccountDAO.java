package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO 
{
    public Account createAccount(String username, String password) //create new user account
    {
        Connection connection = ConnectionUtil.getConnection();
        
        //check if username or password null/invalid
        if(username == null || username.isEmpty() || password == null || password.length() < 4)
        {
            return null;
        }
        
        try
        {
            //check if username exists yet
            String exists = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement existsPS = connection.prepareStatement(exists);
            
            existsPS.setString(1, username);

            ResultSet rsE = existsPS.executeQuery();

            if(rsE.next())
            {
                return null;
            }

            //insert new account
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);

            int numInsert = ps.executeUpdate();

            if(numInsert > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    int id = rs.getInt(1);

                    return new Account(id, username, password);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }    

    public Account login(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        Account account = null;

        try
        {
            String loginSql = "SELECT account_id, username, password FROM account WHERE username = ? AND password = ?;";
            PreparedStatement psLogin = connection.prepareStatement(loginSql);

            psLogin.setString(1, username);
            psLogin.setString(2, password);

            ResultSet rs = psLogin.executeQuery();

            if(rs.next())
            {
                String user = rs.getString("username");
                String pass = rs.getString("password");
                int id = rs.getInt("account_id");

                account = new Account(id, user, pass);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return account;
    }
}
