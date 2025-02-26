package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService 
{
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(String message_text, int posted_by, long time_posted_epoch)
    {
        if(message_text == null || message_text.isEmpty() || message_text.length() > 255)
        {
            return null;
        }

        if(posted_by <= 0)
        {
            return null;
        }

        return messageDAO.createMessage(message_text, posted_by, time_posted_epoch);
    }

    public Message getMessageById(int message_id)
    {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id)
    {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, String message_text)
    {
        if(message_text == null || message_text.isEmpty() || message_text.length() > 255)
        {
            return null;
        }

        return messageDAO.updateMessageById(message_id, message_text);
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserId(int account_id)
    {
        return messageDAO.getAllMessagesByUserId(account_id);
    }
}
