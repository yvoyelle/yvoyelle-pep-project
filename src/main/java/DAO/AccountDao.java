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

    public Account createAccount(Account account) {
        // Validate username and password
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            System.out.println("Invalid input: Username cannot be empty and password must be at least 4 characters.");
            return null;
        }

        Connection con = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Check if the user already exists
            String checkUserSql = "SELECT COUNT(*) FROM account WHERE username = ?";
            ps = con.prepareStatement(checkUserSql);
            ps.setString(1, account.getUsername());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("User already registered.");
                return null;
            }
            rs.close();
            ps.close(); // Close previous statement

            // Insert new user and return generated keys
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                rs = ps.getGeneratedKeys(); // Retrieve generated key
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Account successfully created with ID: " + generatedId);
                    return new Account(generatedId, account.getUsername(), account.getPassword());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}