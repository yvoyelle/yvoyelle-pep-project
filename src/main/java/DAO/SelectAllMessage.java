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

public class SelectAllMessage {
    /*
     * ## 4: Our API should be able to retrieve all
     * messages./////////////////////////////////////////////////////
     * 
     * As a user, I should be able to submit a GET request on the endpoint GET
     * localhost:8080/messages.
     * 
     * - The response body should contain a JSON representation of a list containing
     * all messages retrieved from the database. It is expected for the list to
     * simply be empty if there are no messages. The response status should always
     * be 200, which is the default.
     */
    public List<Message> getAllMessages() {

        List<Message> messages = new ArrayList<>();
        
        // ***************************************************************************************
        //  Retrieve all message
        // ************************************************************************************

        String sql = "SELECT * FROM message";

        try (Connection con = ConnectionUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages; 
}}
