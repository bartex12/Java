package inbox.barcats.cource;

import inbox.barcats.competitor.Competitor;
import inbox.barcats.competitor.Team;

public class Cource {

    final int numberSteps = 4;

    public Obstacle[] course;

    public Cource(Obstacle... obstacles){
        course = new Obstacle[obstacles.length];
        for (int i=0;i<obstacles.length; i++){
            course[i]=obstacles[i];
        }
    }

    public Cource (){
        course = new Obstacle[numberSteps];
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
