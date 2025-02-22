package Services;

import DAO.CreateMessage;
import DAO.DeleteMessage;
import DAO.SelectAllMessage;
import DAO.SelectMessagesByAccountId;
import DAO.UpdateMessage;
import DAO.selectAllMessageByUserAccountId;
import DAO.selectMessaheById;
import Model.Message;

import java.util.*;

public class MessageService {

    public CreateMessage createMessageDao;
public SelectAllMessage selectAllMessageDao;
selectMessaheById selectMessaheByIdDao;
DeleteMessage deleteMessageDao;
UpdateMessage updateMessageDao;
SelectMessagesByAccountId selectMessagesByAccountIdDao;

    public  MessageService (){
        createMessageDao= new CreateMessage();
        selectAllMessageDao= new SelectAllMessage();
        selectMessaheByIdDao=new selectMessaheById();
        deleteMessageDao = new DeleteMessage();
        updateMessageDao = new UpdateMessage();
        selectMessagesByAccountIdDao= new SelectMessagesByAccountId();

    }


    public MessageService (CreateMessage createMessageDao){
        this.createMessageDao=createMessageDao;
    }

  public List<Message> getAllMessages() {
        return selectAllMessageDao.getAllMessages(); // Calls DAO method to get all messages
}
    

public List<Message> SelectMessagesByAccountId(int accountId) {
    List<Message> messages = selectMessagesByAccountIdDao.SelectMessagesByAccountId(accountId);
    return messages != null ? messages : new ArrayList<>(); // Return empty list instead of null
}


   public Message getMessageById(int messageId) {
       return selectMessaheByIdDao.getMessageById(messageId);
}
    

public   Message updateMessage (int  messageId, Message message){

  Message updateMessage =selectMessaheByIdDao.getMessageById(messageId);
  
       return updateMessage;
   }

   //public Flight updateFlight(int flight_id, Flight flight){
   // Flight existingFlight = flightDAO.getFlightById(flight_id);






  


    public Message deleteMessageById(int messageId) {
       return deleteMessageDao.deleteMessageById(messageId);
   }
    
    public Message createMessage(Message message){

        return createMessageDao.createMessage(message);
    }
  
}
