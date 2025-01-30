package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO
{
    public Message createMessage(String message_text, int posted_by, long time_posted_epoch)
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
            String messageSQL = "INSERT INTO message (message_text, posted_by, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(messageSQL, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, message_text);
            ps.setInt(2, posted_by);
            ps.setLong(3, time_posted_epoch);

            int numInsert = ps.executeUpdate();

            if(numInsert > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    int msgNum = rs.getInt(1);
                    msgCreate = new Message(msgNum, posted_by, message_text, time_posted_epoch);
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
            Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            message.add(msg);
        }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message getMessageById(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message msg = null;

        try
        {
        String sql = "SELECT * FROM message WHERE message_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, message_id);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
             msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
        }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return msg;
    }

    public Message deleteMessageById(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message msg = null;

        try 
        {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }

            if(msg != null)
            {
                String delete = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement psDelete = connection.prepareStatement(delete);

                psDelete.setInt(1, message_id);

                psDelete.executeUpdate();
            }
        } 
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }

        return msg;
    }
}

