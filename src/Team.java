public class Team {
    String nameTeam;
    final int number = 4;

    Competitor[] group= new Competitor[number];

    public Team(String nameTeam){
        this.nameTeam = nameTeam;
        group[0] = new Human("Семён Семёныч");
        group[1] = new Human("Петя");
        group[2] = new Cat("Васька");
        group[3] = new Dog("Трамп");
    }

public void info(){

        for (Competitor g: group){
               g.info();
        }
}

}
