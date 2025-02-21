package DAO;
import java.util.*;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Util.ConnectionUtil.getConnection;

public class MessageDao {
    public Message createMessage(Message message) {
        if (message.message_text.isEmpty() || message.message_text.length() > 255 || message.posted_by == 0) {
            return null; // Validation failed
        }
    
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);
            ps.setLong(3, message.time_posted_epoch);
            int affectedRows = ps.executeUpdate();
    
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    return new Message(generatedId, message.posted_by, message.message_text, message.time_posted_epoch);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    


    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message";
    
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages; // Returns an empty list if no messages exist
    }
    
    

    public Message getMessageById(int messageId) {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        
        try (Connection con = ConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
    
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
    
            if (rs.next()) { // Fetch message
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no message found
    }
    
    public boolean getAllMessageByUserAccountId(Message message){
        PreparedStatement ps ;
        Connection con=ConnectionUtil.getConnection() ;
        String sql = "select * from message where account.user_id= ?";

        try{
            ps=con.prepareStatement(sql);
            Account account = new Account();
            ps.setInt(1,message.posted_by);
            ps.setString(2,message.message_text);
            ps.setString(3,message.getMessage_text());
            ps.setInt(4,account.account_id);

            ps.executeQuery();
            return  true;

        }catch ( Exception e){
            e.getMessage();

        }

        return false;
    }
    public Message updateMessage(int messageId, int postedBy, String messageText, long timePostedEpoch) {
        PreparedStatement ps;
        Connection con = ConnectionUtil.getConnection();
        
        try {
            String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
            ps = con.prepareStatement(sql);
    
            // Set the values for the prepared statement
            ps.setInt(1, postedBy);
            ps.setString(2, messageText);
            ps.setLong(3, timePostedEpoch);
            ps.setInt(4, messageId);  // Set the messageId to identify the row to update
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // If the update was successful, fetch and return the updated message
                return new Message(messageId, postedBy, messageText, timePostedEpoch);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception (e.g., logging)
        }
        
        return null; // If no rows were affected (i.e., message not found or not updated)
    }
    
   public Message deleteMessage(int messageId) {
    String sql = "DELETE FROM message WHERE message_id = ?";
    
    try (Connection con = ConnectionUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, messageId);
        int rowsAffected = ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Return false if deletion fails
}

}
