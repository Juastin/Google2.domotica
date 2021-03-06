package src.system;
import src.core.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Queries {
    static Connection verbinding = Database.maakVerbinding();

    public static boolean isPasswordCorrect(String username, String password) {
        try {
            PreparedStatement myStmt = verbinding.prepareStatement("SELECT COUNT(Username) FROM Person WHERE Username = ? AND PasswordHash = ?");
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            ArrayList<ArrayList<String>> results = Database.query(myStmt);
            return Integer.parseInt(results.get(0).get(0))>0;
        } catch (Exception ex) {
            System.out.println(ex);
            Logging.logThis("Failed login attempt for user " + username);
            return false;
        }
    }

    public static ArrayList<ArrayList<String>> getProfiles() {
        try {
            PreparedStatement myStmt = verbinding.prepareStatement("SELECT username FROM Person");
            ArrayList<ArrayList<String>> results = Database.query(myStmt);
            return results;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static ArrayList<ArrayList<String>> getPersonalSettings(String username) {
        try {
            PreparedStatement myStmt = verbinding.prepareStatement("SELECT ps.Light, ps.Temperature, ps.PlaylistID FROM PersonalSettings ps JOIN Person p ON ps.ProfileID = p.PersonID WHERE p.Username = ?");
            myStmt.setString(1, username);
            ArrayList<ArrayList<String>> results = Database.query(myStmt);
            Logging.logThis("Retrieving personal settings for user " + username);
            return results;
        } catch (Exception ex) {
            System.out.println(ex);
            Logging.logThis("Failed to retrieve personal settings for user " + username);
            return null;
        }
    }

    public static boolean makeNewProfile(String username, String firstname, String lastname, char[] password) {
        String hashed_password = Authentication.encryptPassword(password);

        try {
            PreparedStatement myStmt_0 = verbinding.prepareStatement("INSERT INTO Person (Username, FirstName, LastName, PasswordHash) VALUES (?,?,?,?)");
            myStmt_0.setString(1, username);
            myStmt_0.setString(2, firstname);
            myStmt_0.setString(3, lastname);
            myStmt_0.setString(4, hashed_password);
            Database.query(myStmt_0);
        } catch (Exception ex) {
            System.out.println(ex);
            Logging.logThis("Failed to make a new profile: (" + username + ", " + firstname + ", " + lastname + ")");
            return false;
        }

        Logging.logThis("New profile created for user " + username);
        return true;
    }

    public static boolean setStandardProfileSettings(String username) {
        int light = 30;
        int heating = 15;

        try {
            PreparedStatement myStmt = verbinding.prepareStatement(" UPDATE PersonalSettings set Light = ?, Temperature = ? WHERE ProfileID = (SELECT PersonID FROM Person WHERE Username = ?)");
            myStmt.setInt(1, light);
            myStmt.setInt(2, heating);
            myStmt.setString(3, username);
            Database.query(myStmt);
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public static boolean updatePersonalSettings(int light, int heating, String username) {
        try {
            PreparedStatement myStmt = verbinding.prepareStatement(" UPDATE PersonalSettings set Light = ?, Temperature = ? WHERE ProfileID = (SELECT PersonID FROM Person WHERE Username = ?)");
            myStmt.setInt(1, light);
            myStmt.setInt(2, heating);
            myStmt.setString(3, username);
            Database.query(myStmt);
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

}