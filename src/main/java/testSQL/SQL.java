package testSQL;

import java.sql.*;

class SQL {
    private static Connection connection;
    private static Statement stmt;

    static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:user.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getIdByNameAndPhone(String name, String phone) {
        try {
            ResultSet rs = stmt.executeQuery(
                    "SELECT id FROM User WHERE Name = '" + name +"' AND Phone = '" + phone + "'"
            );
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
