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
    public  Boolean UpdateMessage( Message message){
        PreparedStatement ps;
        Connection con=ConnectionUtil.getConnection() ;

        try{
            String sql= "update  message set posted_by =?, message_text =?,time_posted_epoch =? where message_id=?";
            ps = con.prepareStatement(sql);

            ps.setInt(1,message.posted_by);
            ps.setString(2,message.getMessage_text() );
            ps.setLong(3,message.time_posted_epoch);
            ps.setInt(4,message.message_id);
            ps.execute();

            return  true;
        }catch (Exception e){
            e.getMessage();
        }
        return  false;
    }
   public  boolean deleteMessage( Message message){
        PreparedStatement ps;
        Connection con=getConnection();

        try{
            String sql= "delete  from message where message_id =?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,message.message_id);
            ps.execute();
            return  true;
        }catch (Exception e){
            e.getMessage();
        }
        return  false;
    }
}
