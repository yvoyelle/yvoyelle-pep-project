package Services;

import DAO.MessageDao;
import Model.Message;

import java.util.List;

public class MessageService {

    public MessageDao messageDao;

    public  MessageService (){
        messageDao= new MessageDao();
    }


    public MessageService (MessageDao messageDao){
        this.messageDao=messageDao;
    }

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages(); // Calls DAO method to get all messages
    }
    

    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }
    

    public Message updateMessage (int  messageId, Message message){

        Message messageObj=messageDao.UpdateMessage(messageId);

        return messageObj;
    }




  


    public Message deleteMessageById(int messageId) {
        return messageDao.deleteMessage(messageId);
    }
    
    public Message createMessage(Message message){

        return messageDao.createMessage(message);
    }
  
}
