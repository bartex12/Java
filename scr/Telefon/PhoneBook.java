package Telefon;

import java.util.*;

public class TelephoneDirectory {

    Map<String, HashSet<String>> phoneBook;
    HashSet<String> number;
    HashSet<String> def;


    public TelephoneDirectory() {
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
            System.out.print(name + ": ");
            number = phoneBook.getOrDefault(name, def);
            System.out.println(number);
    }
}
