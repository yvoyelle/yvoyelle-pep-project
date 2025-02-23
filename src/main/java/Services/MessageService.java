package Services;

import DAO.CreateMessageDAO;
import DAO.DeleteMessageDAO;
import DAO.SelectAllMessage;
import DAO.SelectMessagesByAccountId;
import DAO.UpdateMessageDAO;
import DAO.SelectmessageByMesssageIdDAO;
import Model.Message;

import java.util.*;

public class MessageService {

    // ***************************************************************************************
    // Each line is declaring a DAO (Data Access Object) as an instance variable.
    // DAOs are used to interact with the database, typically performing CRUD
    // (Create, Read, Update, Delete) operations
    // ***************************************************************************************

    public CreateMessageDAO createMessageDao;
    public SelectAllMessage selectAllMessageDao;
    SelectmessageByMesssageIdDAO selectMessaheByIdDao;
    DeleteMessageDAO deleteMessageDao;
    UpdateMessageDAO updateMessageDao;
    SelectMessagesByAccountId selectMessagesByAccountIdDao;

  // ***************************************************************************************
  //This is a default constructor that gets called when an object of MessageService is created.
  // ***************************************************************************************

    public MessageService() {
        createMessageDao = new CreateMessageDAO();
        selectAllMessageDao = new SelectAllMessage();
        selectMessaheByIdDao = new SelectmessageByMesssageIdDAO();
        deleteMessageDao = new DeleteMessageDAO();
        updateMessageDao = new UpdateMessageDAO();
        selectMessagesByAccountIdDao = new SelectMessagesByAccountId();

    }

    public MessageService(CreateMessageDAO createMessageDao) {
        this.createMessageDao = createMessageDao;
    }

    public List<Message> getAllMessages() {
        return selectAllMessageDao.getAllMessages(); 
    }

    public List<Message> SelectMessagesByAccountId(int accountId) {
        List<Message> messages = selectMessagesByAccountIdDao.SelectMessagesByAccountId(accountId);
        return messages != null ? messages : new ArrayList<>(); 
    }

    public Message getMessageById(int messageId) {
        return selectMessaheByIdDao.getMessageById(messageId);
    }

    public Message updateMessage(int messageId, String newMessageText) {
        return updateMessageDao.updateMessage(messageId, newMessageText);
    }

    public Message deleteMessageById(int messageId) {
        return deleteMessageDao.deleteMessageById(messageId);
    }

    public Message createMessage(Message message) {
        return createMessageDao.createMessage(message);
    }

}
