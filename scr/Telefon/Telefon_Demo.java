package Telefon;

public class Telefon_Demo {

    public static void main(String[] args) {

        PhoneBook td = new PhoneBook();

        // добавляем имя и телефоны в телефонную книгу
        td.addPerson("Иванов", "+79211234567");
        td.addPerson("Петров", "+79214445577", "+79214445588");
        td.addPerson("Сидоров", "02");
        td.addPerson("Сидоров", "555-56-78", "8-960-33-45");
        td.addPerson("Николаев", "");
        td.addPerson("", "555");
        td.addPerson("ABC", "dgsdfbfbf");

        td.get("Сидоров");
        td.get("Петров");
        td.get("Иванов");
        td.get("Баранов");
        td.get("Николаев");
        td.get("");
        td.get("ABC");  // надо делать проверку или ограничение на ввод
    }
}