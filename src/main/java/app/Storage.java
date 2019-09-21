package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Storage {
    ArrayList<Phone> arrayList = new ArrayList<Phone>();
    ArrayList<Phone> profitList = new ArrayList<Phone>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    void newProduct(String name) throws IOException {
        boolean checkAvailability = true;

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(name)) {
                System.out.println("Введите сколько пришло новых телефонов: ");
                String s = reader.readLine();
                int amount = Integer.parseInt(s);

                arrayList.get(i).addAmount(amount);
                checkAvailability = false;
            }
        }

        if (checkAvailability) {
            System.out.println("Введите цену нового товара: ");
            String s = reader.readLine();
            int price = Integer.parseInt(s);

            System.out.println("Введите цвет данного телефона: ");
            String color = reader.readLine();
            Phone newPhone = new Phone(price, name, color);

            System.out.println("Введите количество телефонов: ");
            String s1 = reader.readLine();
            int amount = Integer.parseInt(s1);
            newPhone.addAmount(amount);

            arrayList.add(newPhone);
        }
    }

    void purchase(String name) throws IOException {
        boolean checkAvailability = false;
        int index = 0;
        int indexProfit = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(name)) {
                index = i;
                checkAvailability = true;
            }
        }

        if (checkAvailability) {
            System.out.println("К покупке доступно " + arrayList.get(index).getAmount() + " телефонов данной модели");
            System.out.println("Введите количество моделей, которое вы планируете приобрести: ");
            String s = reader.readLine();
            int amount = Integer.parseInt(s);

            arrayList.get(index).delAmount(amount);

            Phone profitPhone = new Phone(arrayList.get(index).getPrice(), arrayList.get(index).getName(),
                    arrayList.get(index).getColor());
            profitList.add(profitPhone);
            for (int i = 0; i < profitList.size(); i++) {
                if (profitList.get(i).getName().equals(name)) {
                    indexProfit = i;
                }
            }

            profitList.get(indexProfit).setAmount(amount);
        } else {
            System.out.println("Товар не найден!");
        }
    }

    void saleReport(String name) {
        int sumProfit = 0;

        if (name.equals("All") || name.equals("all")) {
            for (int i = 0; i < profitList.size(); i++) {
                int p = profitList.get(i).getAmount();
                sumProfit = (p * profitList.get(i).getPrice()) + sumProfit;
            }
            System.out.println("Общая прибыль магазина: " + sumProfit);
        } else {
            for (int i = 0; i < profitList.size(); i++) {
                if (profitList.get(i).getName().equals(name)) {
                    int p = profitList.get(i).getAmount();
                    sumProfit = (p * profitList.get(i).getPrice()) + sumProfit;
                }
            }
            System.out.println("Прибыль магазина при продаже " + name + " составляет: " + sumProfit);
        }
    }
}
