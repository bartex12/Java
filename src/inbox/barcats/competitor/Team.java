package inbox.barcats.competitor;

public class Team {
    private String nameTeam;
    private final int number = 4;

    public Competitor[] group;

    public Team(String nameTeam){
        this.nameTeam = nameTeam;
        group = new Competitor[number];
        group[0] = new Human("Семён Семёныч");
        group[1] = new Human("Петя");
        group[2] = new Cat("Васька");
        group[3] = new Dog("Трамп");
        this.showTeamNames();
    }

    public Team(String nameTeam, Competitor... competitors ){
        this.nameTeam = nameTeam;
        group = new Competitor[competitors.length];
        for (int i = 0; i<competitors.length; i++ ){
            group[i] = competitors[i];
        }
        this.showTeamNames();
    }

    public void showTeamNames(){
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("*** В команду " + nameTeam + " входят *** ");
        for (Competitor c: group){
            System.out.println( c.showNames());
        }
    }

public void showResults(){
    System.out.println("");
    System.out.println("### Оценка прохождения полосы препятствий ###");
        for (Competitor g: group){
               g.info();
        }
    }

}