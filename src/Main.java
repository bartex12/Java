public class Main {

    public static void main(String[] args) {

        Cource cource = new Cource();
        Team team = new Team("AdiDas");
        cource.doIt(team);
        team.info();

    }
}