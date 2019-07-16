package inbox.barcats.marathon;

import inbox.barcats.competitor.Cat;
import inbox.barcats.competitor.Dog;
import inbox.barcats.competitor.Human;
import inbox.barcats.cource.Cource;
import inbox.barcats.competitor.Team;
import inbox.barcats.cource.Cross;
import inbox.barcats.cource.Wall;

public class Main {

    public static void main(String[] args) {

        Cource cource = new Cource(); //создаём полосу препятствий (4 этапа) с параметрами по умолчанию
        Team team = new Team("AdiDas");  //создаём команду с параметрами по умолчанию (4 участника)
        cource.doIt(team);  //запускаем прохождение полосы
        team.showResults(); //выводим результаты

        //создаём полосу препятствий с произвольным числом этапов
        Cource cource2 = new Cource(new Cross(300), new Wall(25));
        //создаём команду с произвольным числом участников
        Team team2 = new Team("Стражи Вселенной", new Human("Человек-Паук"),
                new Cat("Матроскин"), new Dog("Артамон") );
        cource2.doIt(team2); //запускаем прохождение полосы
        team2.showResults(); //выводим результаты

    }
}