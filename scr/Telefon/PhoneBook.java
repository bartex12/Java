package Telefon;

import java.util.*;

public class PhoneBook {

    Map<String, HashSet<String>> phoneBook;
    HashSet<String> number;
    HashSet<String> def;


    public PhoneBook() {
        phoneBook = new HashMap();
        number = new HashSet<>();
        def = new HashSet<>();
        def.add(" Данные отсутствуют");
    }

    public void addPerson (String name, String... phoneNum){
        number = new HashSet<>();
            for (String s: phoneNum){
                number.add(s);
            }
        phoneBook.put(name, number);
    }

    public void get(String name){
        if (name.length()>0){
            System.out.print(name + ": ");
        }else {
            System.out.print("Без имени" + ": ");
        }

        number = phoneBook.getOrDefault(name, def);

        if (number.contains("")){
            System.out.println("[ Номер не указан ]");
        }else {
            System.out.println(number);
        }
    }
}
