package database;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("JDBC Connection Working!");
        } else {
            System.out.println("Connection Not Working!");
        }
    }
}
