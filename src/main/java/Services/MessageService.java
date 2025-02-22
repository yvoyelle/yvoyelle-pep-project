package Services;

import DAO.CreateMessage;
import DAO.DeleteMessage;
import DAO.SelectAllMessage;
import DAO.UpdateMessage;
import DAO.selectMessaheById;
import Model.Message;

import java.util.List;

public class MessageService {

    public CreateMessage createMessageDao;
public SelectAllMessage selectAllMessageDao;
selectMessaheById selectMessaheByIdDao;
DeleteMessage deleteMessageDao;
UpdateMessage updateMessageDao;

    public  MessageService (){
        createMessageDao= new CreateMessage();
        selectAllMessageDao= new SelectAllMessage();
        selectMessaheByIdDao=new selectMessaheById();
        deleteMessageDao = new DeleteMessage();
        updateMessageDao = new UpdateMessage();

    }


    public MessageService (CreateMessage createMessageDao){
        this.createMessageDao=createMessageDao;
    }

  public List<Message> getAllMessages() {
        return selectAllMessageDao.getAllMessages(); // Calls DAO method to get all messages
}
    

   public Message getMessageById(int messageId) {
       return selectMessaheByIdDao.getMessageById(messageId);
}
    

    public Message updateMessage (int messageId, int postedBy, String messageText, long timePostedEpoch){

Message messageObj=updateMessageDao.UpdateMessage(messageId);

       return messageObj;
   }




  


    public Message deleteMessageById(int messageId) {
       return deleteMessageDao.deleteMessageById(messageId);
   }
    
    public Message createMessage(Message message){

        return createMessageDao.createMessage(message);
    }
  
}
