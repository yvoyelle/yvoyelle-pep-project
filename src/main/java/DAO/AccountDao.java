
package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static Util.ConnectionUtil.getConnection;

public class AccountDao {

    /*
     * ## 1: Our API should be able to process new User registrations.
     * //////////////////////////
     * 
     * As a user, I should be able to create a new Account on the endpoint POST
     * localhost:8080/register. The body will contain a representation of a JSON
     * Account, but will not contain an account_id.
     * 
     * - The registration will be successful if and only if the username is not
     * blank, the password is at least 4 characters long, and an Account with that
     * username does not already exist. If all these conditions are met, the
     * response body should contain a JSON of the Account, including its account_id.
     * The response status should be 200 OK, which is the default. The new account
     * should be persisted to the database.
     * - If the registration is not successful, the response status should be 400.
     * (Client error)
     */

    public Account createAccount(Account account) {
        // ***************************************************************************************
        // Let's Validate username and password
        // ***************************************************************************************

        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }

        Connection con = ConnectionUtil.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            // ***************************************************************************************
            // Les't Check if the user already exists before created
            // ***************************************************************************************

            String checkIfUserExist = "SELECT EXISTS (SELECT 1 FROM account WHERE username = ?)";
            ps = con.prepareStatement(checkIfUserExist);

            ps.setString(1, account.getUsername());
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // return null that mean the user already exist
                return null;
            }
            // ***************************************************************************************
            // Les't Insert new user and return generated keys
            // ***************************************************************************************

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int rowsffect = ps.executeUpdate();

            if (rowsffect > 0) {
                rs = ps.getGeneratedKeys(); // Retrieve generated key

                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    // return a new user account
                    return new Account(generatedId, account.getUsername(), account.getPassword());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    /*
     * ## 2: Our API should be able to process User
     * logins.////////////////////////////////////
     * 
     * As a user, I should be able to verify my login on the endpoint POST
     * localhost:8080/login. The request body will contain a JSON representation of
     * an Account, not containing an account_id. In the future, this action may
     * generate a Session token to allow the user to securely use the site. We will
     * not worry about this for now.
     * 
     * - The login will be successful if and only if the username and password
     * provided in the request body JSON match a real account existing on the
     * database. If successful, the response body should contain a JSON of the
     * account in the response body, including its account_id. The response status
     * should be 200 OK, which is the default.
     * - If the login is not successful, the response status should be 401.
     * (Unauthorized)
     */

    public Account getUser(Account account) {
        // ***************************************************************************************
        // Let's check user credentials to allow login
        // ***************************************************************************************

        String sql = "select * from account WHERE username = ? AND password = ?";

        try (Connection con = ConnectionUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}