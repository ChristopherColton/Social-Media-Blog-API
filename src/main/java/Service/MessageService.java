package Service;

import Model.Message;
import DAO.MessageDAO;

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
}
