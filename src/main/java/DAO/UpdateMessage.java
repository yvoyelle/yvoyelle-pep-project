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
    
    //The update of a message should be successful if and only if the message id already exists and 
    //the new message_text is not blank and is not over 255 characters.
   
    public  Message UpdateMessage( int messageId){
        PreparedStatement ps;
        Connection con=ConnectionUtil.getConnection() ;

        try{
            String sql= "update  message set posted_by =?, message_text =?, time_posted_epoch =? where message_id=?";
            ps = con.prepareStatement(sql);

            ps.setInt(1,messageId);
            ps.execute();

        }catch (SQLException e){
            e.getMessage();
        }
        return  null;
    }
}
