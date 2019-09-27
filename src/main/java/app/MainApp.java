package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {
    private final static Storage storage = new Storage();


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Введите одну из команд - (list phone | list profit | add phone | buy phone | profit all | profit date | profit phone | delete | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");

            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String name = null;
            if (params.length == 2) {
                name = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll(name);
                    break;
                case "add":
                    storage.newProduct(name);
                    break;
                case "buy":
                    storage.purchase(name);
                    break;
                case "profit":
                    storage.saleReport(name);
                    break;
                case "delete":
                    storage.delete(name);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll(String name) {
        System.out.println("----------------------------");
        if (name.equals("phone")) {
            for (int i = 0; i < storage.arrayList.size(); i++) {
                System.out.print("Модель: " + storage.arrayList.get(i).getName() + "\t");
                System.out.print("Цена: " + storage.arrayList.get(i).getPrice() + " рублей" + "\t");
                System.out.print("Цвет: " + storage.arrayList.get(i).getColor() + "\t");
                System.out.print("Количество: " + storage.arrayList.get(i).getAmount() + "\t");
                System.out.println();
            }
        } else if (name.equals("profit")) {
            for (int i = 0; i < storage.profitList.size(); i++) {
                System.out.print("Модель: " + storage.profitList.get(i).getName() + "\t");
                System.out.print("Цена: " + storage.profitList.get(i).getPrice() + " рублей" + "\t");
                System.out.print("Цвет: " + storage.profitList.get(i).getColor() + "\t");
                System.out.print("Количество: " + storage.profitList.get(i).getAmount() + "\t");
                System.out.println();
            }
        }
        System.out.println("----------------------------");
    }
}