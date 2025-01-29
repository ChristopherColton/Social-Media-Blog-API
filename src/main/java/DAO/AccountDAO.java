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

            int rows = ps.executeUpdate();

            if(rows > 0)
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
}
