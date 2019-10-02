package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

class Login {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String access;

    void login() throws IOException {
        SQL.connect();

        System.out.println("Введите одну из команд - (sign in | register): ");
        String params = reader.readLine();

        switch (params) {
            case "sign in":
                signIn();
                break;
            case "register":
                register();
                signIn();
                break;
            default:
                System.out.println("Неверная команда.");
                break;
        }
        SQL.disconnect();
    }

    private void signIn() throws IOException {
        System.out.println("Введите свой username: ");
        String username = reader.readLine();

        System.out.println("Введите свой password: ");
        String password = reader.readLine();

        try {
            ResultSet rs = stmt().executeQuery(
                    "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'"
            );
            if (rs.next()) {
                access = rs.getString("access");
            } else {
                System.out.println("Неправильно указан логин и/или пароль");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register() throws IOException {
        System.out.println("Введите username: ");
        String username = reader.readLine();

        System.out.println("Введите password: ");
        String password1 = reader.readLine();

        System.out.println("Введите password еще раз: ");
        String password2 = reader.readLine();

        while (!password1.equals(password2)) {
            System.out.println("Пароли не совпадают");

            System.out.println("Введите password: ");
            password1 = reader.readLine();

            System.out.println("Введите password еще раз: ");
            password2 = reader.readLine();
        }

        try (PreparedStatement statement = this.connection().prepareStatement(
                "INSERT INTO users (`username`, `password`, `access`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, username);
            statement.setObject(2, password1);
            statement.setObject(3, "user");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement stmt() {
        return SQL.stmt;
    }

    private Connection connection() {
        return SQL.connection;
    }

    String getAccess() {
        return access;
    }
}
