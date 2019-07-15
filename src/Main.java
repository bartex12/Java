public class Main {

    public static void main(String[] args) {

        Cource cource = new Cource(); //создаём полосу препятствий с параметрами по умолчанию
        Team team = new Team("AdiDas");  //создаём команду с параметрами по умолчанию
        cource.doIt(team);  //запускаем прохождение полосы
        team.showResults(); //выводим результаты

        //создаём команду с произвольным числом участников
        Team team2 = new Team("Стражи Вселенной", new Human("Человек-Паук"),
                new Cat("Матроскин"), new Dog("Артамон") );
        cource.doIt(team2); //запускаем прохождение полосы
        team2.showResults(); //выводим результаты

    }
}