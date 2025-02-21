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
public class selectAllMessageByUserAccountId {
    public boolean getAllMessageByUserAccountId(Message message){
        PreparedStatement ps ;
        Connection con=ConnectionUtil.getConnection() ;
        String sql = "select * from message where account.user_id= ?";

        try{
            ps=con.prepareStatement(sql);
            Account account = new Account();
            ps.setInt(1,message.posted_by);
            ps.setString(2,message.message_text);
            ps.setString(3,message.getMessage_text());
            ps.setInt(4,account.account_id);

            ps.executeQuery();
            return  true;

        }catch ( Exception e){
            e.getMessage();

        }

        return false;
    }
   
}
