
    package DAO;
    import Model.Message;
    import Util.ConnectionUtil;
    import java.sql.*;
    
    public class UpdateMessageDAO {
    
        public Message updateMessage(int messageId, String newMessageText) {
            // Validate new message text
            if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
                return null; // Invalid message text
            }
        
            Connection con = null;
            PreparedStatement selectPs = null;
            PreparedStatement updatePs = null;
            ResultSet rs = null;
            Message updatedMessage = null;
        
            try {
                con = ConnectionUtil.getConnection();
        
                // Check if the message exists
                String checkSQL = "SELECT * FROM message WHERE message_id = ?";
                selectPs = con.prepareStatement(checkSQL);
                selectPs.setInt(1, messageId);
                rs = selectPs.executeQuery();
        
                if (!rs.next()) {
                    return null; // Message ID does not exist
                }
        
                // Update message text
                String updateSQL = "UPDATE message SET message_text = ? WHERE message_id = ?";
                updatePs = con.prepareStatement(updateSQL);
                updatePs.setString(1, newMessageText);
                updatePs.setInt(2, messageId);
        
                int rowsAffected = updatePs.executeUpdate();
        
                if (rowsAffected > 0) {
                    updatedMessage = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        newMessageText,  // Update only message_text
                        rs.getLong("time_posted_epoch")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Clean up resources
                try {
                    if (rs != null) rs.close();
                    if (updatePs != null) updatePs.close();
                    if (selectPs != null) selectPs.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
            return updatedMessage; // Return the updated message if successful, or null if message not found
        }
        
            
    }
    

