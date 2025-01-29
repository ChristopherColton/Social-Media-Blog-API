package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO
{
    public Message createMessage(String message_text, int posted_by)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message msgCreate = null;

        try
        {
            //confirm if user is real
            String confirmUserSQL = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement confirmUserPS = connection.prepareStatement(confirmUserSQL);

            confirmUserPS.setInt(1, posted_by);
            ResultSet confirmUserRS = confirmUserPS.executeQuery();

            if(!confirmUserRS.next())
            {
                return null;
            }

            //create-insert message
            String messageSQL = "INSERT INTO message (message_text, posted_by) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(messageSQL);
            ResultSet rs;
            ps.setString(1, message_text);
            ps.setInt(2, posted_by);

            int numInsert = ps.executeUpdate();

            if(numInsert > 0)
            {
                rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    int msgNum = rs.getInt(1);
                    msgCreate = new Message(msgNum, message_text, posted_by);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return msgCreate;
    }

    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> message = new ArrayList<>();

        try
        {
        String sql = "SELECT * FROM message;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            Message msg = new Message(rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch")
            );
            message.add(msg);
        }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return message;
    }
}