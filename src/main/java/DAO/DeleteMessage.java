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
