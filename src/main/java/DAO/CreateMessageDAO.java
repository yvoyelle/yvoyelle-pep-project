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

public class CreateMessageDAO {
    Account account;

    public Message createMessage(Message message) {

        /*
         * ## 3: Our API should be able to process the creation of new messages.
         * 
         * As a user, I should be able to submit a new post on the endpoint POST
         * localhost:8080/messages.
         * The request body will contain a JSON representation of a message, which
         * should be persisted to
         * the database, but will not contain a message_id.
         * 
         * - The creation of the message will be successful if and only if the
         * message_text is not blank, is not over 255 characters, and posted_by refers
         * to a real, existing user. If successful, the response body should contain a
         * JSON of the message, including its message_id. The response status should be
         * 200, which is the default. The new message should be persisted to the
         * database.
         * - If the creation of the message is not successful, the response status
         * should be 400. (Client error)
         */

        // Validate message fields to not allow invalid message
        if (message == null || message.message_text == null || message.message_text.trim().isEmpty()
                || message.message_text.length() > 255 || message.posted_by <= 0) {
            return null;
        }

        Connection con = ConnectionUtil.getConnection();
        try {

            // ***************************************************************************************
            // if message is valid Insert new meesage
            // ************************************************************************************
             String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);
            ps.setLong(3, message.time_posted_epoch); 

            ps.executeUpdate();

            // let try to retrieve generated message ID
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generated_message_id = rs.getInt(1);
                return new Message(generated_message_id, message.posted_by, message.message_text,message.time_posted_epoch);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }
}
