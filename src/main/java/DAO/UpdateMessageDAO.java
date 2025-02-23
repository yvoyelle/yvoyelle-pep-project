package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;

public class UpdateMessageDAO {
    public Message updateMessage(int messageId, String newMessageText) {
        // ***************************************************************************************
        // Validate the new message text before processing
        // ***************************************************************************************

        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            return null;
        }

        Message updatedMessage = null;
        // ***************************************************************************************
        // lest check if the message is exist by provide message id
        // ***************************************************************************************
        String sql_select = "SELECT * FROM message WHERE message_id = ?";
        

        String sql_update = "UPDATE message SET message_text = ? WHERE message_id = ?";


        try (Connection con = ConnectionUtil.getConnection();

                PreparedStatement ps_select = con.prepareStatement(sql_select);

                PreparedStatement ps_update = con.prepareStatement(sql_update)) {

            // ***************************************************************************************
            // Check if the message exists
            // ***************************************************************************************
            ps_select.setInt(1, messageId);
            ResultSet rs = ps_select.executeQuery();

            if (!rs.next()) {
                return null; // Message ID does not exist
            }

    

            // ***************************************************************************************
            // If message exists, update message text
            // ***************************************************************************************
            
            ps_update.setString(1, newMessageText);
            ps_update.setInt(2, messageId);

            int rowsAffected = ps_update.executeUpdate();

            if (rowsAffected > 0) {

                int postedBy = rs.getInt("posted_by");
                long timePostedEpoch = rs.getLong("time_posted_epoch");

                // assig the update message 
                updatedMessage = new Message(messageId, postedBy, newMessageText, timePostedEpoch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedMessage; 
    }
}
