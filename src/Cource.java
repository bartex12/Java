public class Cource {

    final int numberSteps = 4;

    Obstacle[] course= new Obstacle[numberSteps];

    public Cource (){
        course[0] =new Cross(80);
        course[1] =new Wall(2);
        course[2] = new Water(10);
        course[3] =new Cross(120);
    }

    public void doIt (Team team){
        for (Competitor c : team.group) {
            System.out.println("");
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;            }
        }
    }

}
