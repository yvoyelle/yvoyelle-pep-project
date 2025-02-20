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

    public boolean getAllMessage(Message message){

        return messageDao.getAllMessage(message);
    }

    public boolean getAllMessageByAccountId(Message message){

        return messageDao.getAllMessageByUserAccountId(message);
    }

    public boolean updateMessage (Message message){

        return messageDao.UpdateMessage(message);
    }


    public Message createMessage(Message message){

        return messageDao.createMessage(message);
    }


}
