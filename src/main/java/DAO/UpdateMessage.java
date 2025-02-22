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
public class UpdateMessage {
    
    public Message UpdateMessage(int messageId, Message message) {

        // Validation for the message object and message_text
        if (message == null || message.message_text == null || message.message_text.trim().isEmpty() 
            || message.message_text.length() > 255 || message.posted_by <= 0) {
            return null; // Validation failed, return null
        }else{
            PreparedStatement ps;
            Connection con = ConnectionUtil.getConnection();
            Message updatedMessage = null;
        
            try {
                // Prepare the SQL query for updating the message
                String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
                ps = con.prepareStatement(sql);
        
                // Set the values for the prepared statement
                ps.setInt(1, message.posted_by);
                ps.setString(2, message.message_text);
                ps.setLong(3, message.time_posted_epoch);
                ps.setInt(4, messageId);
        
                // Execute the update
                int rowsAffected = ps.executeUpdate();
        
                // If the update was successful, retrieve the updated message
                if (rowsAffected > 0) {
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        
            return updatedMessage;  // Return the updated message if successful, or null if failed
        }
        
        }
    
       
}
