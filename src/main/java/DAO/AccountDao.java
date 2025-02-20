package DAO;
//You will need to design and create your own DAO classes from scratch.
//You should refer to prior mini-project lab examples and course material for guidance.

//Please refrain from using a 'try-with-resources' block when connecting to your database.
//The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static Util.ConnectionUtil.getConnection;

public class AccountDao {

    public  Account createAccount( Account account){
        PreparedStatement ps;
        Connection con= ConnectionUtil.getConnection() ;
        String username= account.getUsername();
        String password =account.getPassword();
        
        if (username.equals("") && username.length()>=4){
            try{
                String sql= "insert into account (username, password) values(?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1,account.username);
                ps.setString(2,account.password);
                ps.executeUpdate();
            }catch (Exception e){
                e.getMessage();
            }
        }else {
            System.out.println("user already register");            
        }


        return  null;
    }


    

public Account getUser(Account account){
        PreparedStatement ps ;
        Connection con=ConnectionUtil.getConnection() ;
        String sql = "select * from account where username= ? and password=?";

        try{
            ps=con.prepareStatement(sql);
           ps.setString(1,account.username);
           ps.setString(2,account.password);
           ps.executeQuery();

        }catch ( Exception e){
e.getMessage();

        }


        return null;
}
}
