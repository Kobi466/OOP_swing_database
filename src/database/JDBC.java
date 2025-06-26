package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    public static Connection getConnection() {
        Connection con = null;
        try {
            //Đăng kí mysql driver với drivermanager
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Các thông số
            var url = "jdbc:mysql://localhost:3306/Shop";
            var user = "root";
            var password = "";
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
