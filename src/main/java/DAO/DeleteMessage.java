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
public class DeleteMessage {
    public Message deleteMessageById(int messageId) {
        Connection con = getConnection();
        Message messageToDelete = null;
    
        try {
            // Retrieve the message before deletion
            String selectQuery = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement selectPs = con.prepareStatement(selectQuery);
            selectPs.setInt(1, messageId);
            ResultSet rs = selectPs.executeQuery();
    
            if (rs.next()) {
                messageToDelete = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            } else {
                return null; // Message does not exist, return null
            }
    
            // Delete the message
            String deleteQuery = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement deletePs = con.prepareStatement(deleteQuery);
            deletePs.setInt(1, messageId);
            int rowsAffected = deletePs.executeUpdate();
    
            if (rowsAffected == 0) {
                return null; // Deletion failed
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return messageToDelete;
    }
    
}
