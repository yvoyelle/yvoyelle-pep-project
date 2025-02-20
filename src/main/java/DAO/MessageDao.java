package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Util.ConnectionUtil.getConnection;

public class MessageDao {
    public  Message createMessage( Message message){
        PreparedStatement ps;
        Connection con=ConnectionUtil.getConnection() ;
        String sql= "insert into message (posted_by, message_text,time_posted_epoch) values(?,?,?)";

        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,message.posted_by);
            ps.setString(2,message.message_text);
            ps.setLong(3,message.time_posted_epoch);
            ps.executeUpdate();
            
        }catch (Exception e){
            e.getMessage();
        }
        return  null;
    }


    public boolean getAllMessage(Message message){
        PreparedStatement ps ;
        Connection con=ConnectionUtil.getConnection() ;
        String sql = "select * from message";

        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1,message.posted_by);
            ps.setString(2,message.message_text);
            ps.setString(3,message.getMessage_text());
            ps.executeQuery();
            return  true;

        }catch ( Exception e){
            e.getMessage();

        }


        return false;
    }

    public Message getMessageById(int message_id){
        PreparedStatement ps ;
        Connection con=ConnectionUtil.getConnection() ;

        try{
            String sql = "select * from message where message_id=?";
            ps=con.prepareStatement(sql);

            ps.setInt(1,message_id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
       Message message= new Message();
        rs .getInt(("posted_by"));
        rs.getString("message_text");
        rs.getDouble("time_posted_epoch");

                return  message;

            }

        }catch ( Exception e){
            e.getMessage();

        }


        return null;
    }
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
    public  Boolean UpdateMessage( Message message){
        PreparedStatement ps;
        Connection con=ConnectionUtil.getConnection() ;

        try{
            String sql= "update  message set posted_by =?, message_text =?,time_posted_epoch =? where message_id=?";
            ps = con.prepareStatement(sql);

            ps.setInt(1,message.posted_by);
            ps.setString(2,message.getMessage_text() );
            ps.setLong(3,message.time_posted_epoch);
            ps.setInt(4,message.message_id);
            ps.execute();

            return  true;
        }catch (Exception e){
            e.getMessage();
        }
        return  false;
    }
   public  boolean deleteMessage( Message message){
        PreparedStatement ps;
        Connection con=getConnection();

        try{
            String sql= "delete  from message where message_id =?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,message.message_id);
            ps.execute();
            return  true;
        }catch (Exception e){
            e.getMessage();
        }
        return  false;
    }
}
