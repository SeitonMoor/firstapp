package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Storage {
    ArrayList<Phone> arrayList = new ArrayList<Phone>();
    ArrayList<Phone> profitList = new ArrayList<Phone>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    void newProduct(String name) throws IOException {
        SQL.connect();

        try {
            ResultSet rs = stmt().executeQuery(
                    "SELECT name FROM phoneList WHERE name = '" + name + "'"
            );
            if (rs.next()) {
                System.out.println("Введите цвет этой модели: ");
                String color = reader.readLine();

                ResultSet rsNew = stmt().executeQuery(
                        "SELECT name FROM phoneList WHERE name = '" + name + "' AND color = '" + color + "'"
                );

                if (rs.next()) {
                    System.out.println("Введите сколько пришло новых телефонов: ");
                    String s = reader.readLine();
                    int amount = Integer.parseInt(s);

                    stmt().executeUpdate(
                            "UPDATE phoneList SET amount = amount + " + amount + " WHERE name = '" + name + "'"
                    );
                } else {
                    System.out.println("Введите цену нового товара: ");
                    String s = reader.readLine();
                    int price = Integer.parseInt(s);

                    System.out.println("Введите количество телефонов: ");
                    String s1 = reader.readLine();
                    int amount = Integer.parseInt(s1);

                    Phone newPhone = new Phone(price, name, color);
                    newPhone.addAmount(amount);

                    insertPhoneSQL(newPhone, "phoneList");
                }
            } else {
                System.out.println("Введите цену нового товара: ");
                String s = reader.readLine();
                int price = Integer.parseInt(s);

                System.out.println("Введите цвет данного телефона: ");
                String color = reader.readLine();

                System.out.println("Введите количество телефонов: ");
                String s1 = reader.readLine();
                int amount = Integer.parseInt(s1);

                Phone newPhone = new Phone(price, name, color);
                newPhone.addAmount(amount);

                insertPhoneSQL(newPhone, "phoneList");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQL.disconnect();
    }

    void purchase(String name) throws IOException {
        SQL.connect();

        try {
            System.out.println("Какого цвета телефон вы хотете приобрести: ");
            String color = reader.readLine();

            ResultSet rs = stmt().executeQuery(
                    "SELECT * FROM phoneList WHERE name = '" + name + "' AND color = '" + color + "'"
            );
            if (rs.next()) {
                System.out.println("К покупке доступно " + rs.getString("amount") + " телефонов данной модели");
                System.out.println("Введите количество моделей, которое вы планируете приобрести: ");
                String s = reader.readLine();
                int amount = Integer.parseInt(s);

                Phone profitPhone = new Phone(rs.getInt("price"), rs.getString("name"),
                        rs.getString("color"));
                profitPhone.setAmount(amount);

                stmt().executeUpdate(
                        "UPDATE phoneList SET amount = amount - " + amount + " WHERE name = '" + name + "'"
                );

                insertPhoneSQL(profitPhone, "profitList");

                DateFormat formatD = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
                String date = formatD.format(new Date());
                stmt().executeUpdate(
                        "UPDATE profitList SET date =  '" + date +"' WHERE name = '" + name + "' AND date IS NULL"
                );
                stmt().executeUpdate(
                        "UPDATE profitList SET total =  " + profitPhone.getAmount() * profitPhone.getPrice() + " WHERE total IS NULL"
                );
            } else {
                System.out.println("Товар не найден!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SQL.disconnect();
    }

    void saleReport(String name) throws IOException {
        SQL.connect();
        int sumProfit = 0;

        if (name.equals("All") || name.equals("all")) {
            try {
                ResultSet rsProfit = stmt().executeQuery(
                        "SELECT SUM(total) FROM profitList"
                );
                System.out.println("Общая прибыль магазина: " + rsProfit.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (name.equals("Date") || name.equals("date")) {
            System.out.println("Введите дату от: (YYYY:mm:dd) ");
            String dFrom = reader.readLine();
            System.out.println("Введите дату до: (YYYY:mm:dd) ");
            String dTo = reader.readLine();
            try {
                ResultSet rsProfit = stmt().executeQuery(
                        "SELECT SUM(total) FROM profitList WHERE date BETWEEN '" + dFrom + "_00:00:00' and '" + dTo + "_23:59:59'"
                );
                System.out.println("Прибыль магазина в период " + dFrom + " - " + dTo + " составляет: " + rsProfit.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ResultSet rsProfit = stmt().executeQuery(
                        "SELECT SUM(total) FROM profitList WHERE name = '" + name +"'"
                );
                System.out.println("Прибыль магазина при продаже " + name + " составляет: " + rsProfit.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        SQL.disconnect();
    }

    void delete(String name) {
        SQL.connect();
        try (PreparedStatement statement = this.connection().prepareStatement(
                "DELETE FROM phoneList WHERE name = ?")) {
            statement.setObject(1, name);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.disconnect();
    }

    private Statement stmt() {
        return SQL.stmt;
    }

    private Connection connection() {
        return SQL.connection;
    }

    private void insertPhoneSQL(Phone newPhone, String list) {
        try (PreparedStatement statement = this.connection().prepareStatement(
                "INSERT INTO " + list + " (`name`, `amount`, `price`, `color`) " +
                        "VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, newPhone.getName());
            statement.setObject(2, newPhone.getAmount());
            statement.setObject(3, newPhone.getPrice());
            statement.setObject(4, newPhone.getColor());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
