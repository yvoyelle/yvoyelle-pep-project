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

public class SelectmessageByMesssageIdDAO {
    /*
     * ## 5: Our API should be able to retrieve a message by its
     * ID./////////////////////////////////////////////
     * 
     * As a user, I should be able to submit a GET request on the endpoint GET
     * localhost:8080/messages/{message_id}.
     * 
     * - The response body should contain a JSON representation of the message
     * identified by the message_id. It is expected for the response body to simply
     * be empty if there is no such message. The response status should always be
     * 200, which is the default.
     */
    public Message getMessageById(int messageId) {
        List<Message> messages = new ArrayList<>();

        // ***************************************************************************************
        // Retrieve all message by message Id
        // ************************************************************************************

        String sql = "SELECT * FROM message WHERE message_id = ?";

        try (Connection con = ConnectionUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { 
                return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
