package testSQL;

public class MainSQL {

    public static void main(String[] args) {
        SQL.connect();
        System.out.println(SQL.getIdByNameAndPhone("Alex", "89091236621"));
        SQL.disconnect();
    }
}
