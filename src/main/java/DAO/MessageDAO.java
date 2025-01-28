package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO
{
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