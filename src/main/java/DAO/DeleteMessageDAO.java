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

public class DeleteMessageDAO {
    /*
     * ## 6: Our API should be able to delete a message identified by a message
     * ID.
     * 
     * As a User, I should be able to submit a DELETE request on the endpoint DELETE
     * localhost:8080/messages/{message_id}.
     * 
     * - The deletion of an existing message should remove an existing message from
     * the database. If the message existed, the response body should contain the
     * now-deleted message. The response status should be 200, which is the default.
     * - If the message did not exist, the response status should be 200, but the
     * response body should be empty. This is because the DELETE verb is intended to
     * be idempotent, ie, multiple calls to the DELETE endpoint should respond with
     * the same type of response.
     */
    public Message deleteMessageById(int messageId) {

        Connection con = ConnectionUtil.getConnection();
        Message messageToDelete = null;

        try {
            // ***************************************************************************************
            // lest Retrieve the message by id before deletion
            // ************************************************************************************

            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement selectMessage = con.prepareStatement(sql);

            selectMessage.setInt(1, messageId);
            ResultSet rs = selectMessage.executeQuery();

            if (rs.next()) {
                messageToDelete = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            } else {
                return null;
            }
            // ***************************************************************************************
            // lest process the Deletion of message
            // ************************************************************************************

            String sqlDelete = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement ps = con.prepareStatement(sqlDelete);

            ps.setInt(1, messageId);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return messageToDelete;
    }

}
