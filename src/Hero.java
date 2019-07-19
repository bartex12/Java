import java.util.Random;

abstract class Hero {

    protected int health;
    protected String name;
    protected int damage;
    protected int addHeal;

    public Hero(int health, String name, int damage, int addHeal) {
        this.health = health;
        this.name = name;
        this.damage = damage;
        this.addHeal = addHeal;
    }

    abstract void hit(Hero hero);

    abstract void healing(Hero hero);

    void causeDamage(int damage) {
        if(health < 0) {
            System.out.println("Герой уже мертвый!");
        } else {
            health -= damage;
        }

    }

    public int getHealth() {
        return health;
    }

    void addHealth(int health) {
        this.health += health;
    }

    void info() {

        System.out.println(name + " " + (health < 0 ? "Герой мертвый" : health) + " " + damage);
    }
}

class Warrior extends Hero {

    public Warrior(int health, String type, int damage, int addHeal) {
        super(health, type, damage, addHeal);
    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if(this.health < 0) {
                System.out.println("Герой погиб и бить не может!");
            }else if (hero.health<0){
                System.out.println("Герой погиб и его нельзя бить!");
            }else {
                hero.causeDamage(damage);
                System.out.println(this.name + " нанес урон " + hero.name +
                        " оставив  здоровья " + hero.getHealth());
            }

        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Войны не умеют лечить!");
    }
}

class Assasin extends Hero {

    int cricitalHit;
    Random random = new Random();

    public Assasin(int heal, String name, int damage, int addHeal) {
        super(heal, name, damage, addHeal);
        this.cricitalHit = random.nextInt(20);
    }

    @Override
    void hit(Hero hero) {
        if (hero != this) {
            if(health < 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                hero.causeDamage(damage + cricitalHit);
            }
            System.out.println(this.name + " нанес урон " + hero.name +
                    " оставив здоровья " + hero.getHealth() );
        }
    }

    @Override
    void healing(Hero hero) {
        System.out.println("Убийцы не умеют лечить!");
    }
}

class Doctor extends Hero {

    public Doctor(int heal, String name, int damage, int addHeal) {
        super(heal, name, damage, addHeal);
    }

    @Override
    void hit(Hero hero) {
        System.out.println("Доктор не может бить!");
    }

    @Override
    void healing(Hero hero) {
        hero.addHealth(addHeal);
    }
}


class Game {
    public static void main(String[] args) {

        Random randomStep = new Random();
        Random randomHealing = new Random();
        Random randomHiting = new Random();
        int round = 100;

        Hero[] team1 = new Hero[]{new Warrior(250, "Тигрил", 50, 0)
                , new Assasin(150, "Акали", 70, 0)
                , new Doctor(120, "Жанна", 0, 60)};

        Hero[] team2 = new Hero[]{new Warrior(290, "Минотавр", 60, 0)
                , new Assasin(160, "Джинкс", 90, 0)
                , new Doctor(110, "Зои", 0, 80)};

        //задаём раунды схватки
        for (int j = 0; j < round; j++) {
            System.out.println("************************");
            System.out.println(" Раунд " + (j+1));
            // перебираем игроков
            for (int i = 0; i < team1.length; i++) {
                //System.out.println(" Играет игрок " + (i+1));
                //случайным образом выбираем атакующую команду -
                // если выпадает 0 - в атаке team1, если 1 = в атаке team2
                if(randomStep.nextInt(2) == 0) {
                    //System.out.println(" Сейчас в атаке team1");
                    //Сейчас в атаке team1 и мы  проверяем, текущий игрок - это доктор?
                    if(team1[i] instanceof Doctor) {
                        System.out.println("  В игре доктор по имени " + team1[i].name+
                                " из команды team1");
                       //проверяем, а жив ли доктор
                        if (team1[i].getHealth()<0){
                            //если доктор не жив, выводим текст
                            System.out.println(" Доктор " + team1[i].name+ " уже погиб ");
                        }else {
                            //если доктор жив, то он может дать здоровья какому то игроку своей команды
                            //сначала выбираем такого игрока
                            int numToDoctorTeam1 = randomHealing.nextInt(2);
                            //затем проверяем, а жив ли этот игрок
                            if (team1[numToDoctorTeam1].getHealth()<0){
                                //если он уже погиб, выводим текст
                                System.out.println(" Игрок " + team1[numToDoctorTeam1].name +
                                        " погиб и врач ему не нужен!");
                                // а если игрок team1 жив
                            }else {
                                // то доктор лечит  этого игрока
                                team1[i].healing(team1[numToDoctorTeam1]);
                                System.out.println(team1[numToDoctorTeam1].name +
                                        " получил здоровья " + team1[i].addHeal);
                            }
                        }
                        //если игрок team1 - это НЕ доктор, значит он игрок
                    } else {
                        System.out.println("  В игре игрок " + (i+1) + " по имени " + team1[i].name +
                                " из команды team1");
                        //и тогда проверяем: уровень его здоровья < 0 ?
                        if (team1[i].getHealth()<0){
                            //если здоровья < 0, то выводим текст, что он вне игры
                            System.out.println(team1[i].name + " погиб и бить не может!");
                            //если же здоровья у игрока team1 > 0
                        }else {
                            //тогда сначала выбираем игрока team2
                            int numTeam2 = randomHiting.nextInt(3);
                            // а затем проверяем уровень его здоровья
                            if (team2[numTeam2].getHealth()<0){
                                //если здоровья < 0, тогда выводим текст, что он погиб
                                System.out.println(team1[numTeam2].name + " погиб и его нельзя бить!");
                                //если же здоровья > 0, тогда игроки вступают в схватку
                            }else {
                                //а именно, игрок team1 наносит урон игроку team2
                                team1[i].hit(team2[numTeam2]);
                            }
                        }
                    }
                    //случайным образом выбрали атакующую команду -
                    // если выпадает 0 - в атаке team1, если 1 = в атаке team2
                } else {
                   // System.out.println(" Сейчас в атаке team2");
                    //если игрок team2 - это доктор, то он может дать здоровья какому то игроку своей команды
                    if(team2[i] instanceof Doctor) {
                        System.out.println("  В игре доктор по имени " + team2[i].name +
                                " из команды team2");
                        //проверяем, а жив ли доктор
                        if (team2[i].getHealth()<0){
                            //если доктор не жив, выводим текст
                            System.out.println(" Доктор " + team2[i].name+ " уже погиб ");
                        }else {
                            //если доктор жив, то он может дать здоровья какому то игроку своей команды
                            //сначала выбираем такого игрока
                            int numToDoctorTeam2 = randomHealing.nextInt(2);
                            //затем проверяем, а жив ли этот игрок
                            if (team2[numToDoctorTeam2].getHealth()<0){
                                //если он уже погиб, выводим текст
                                System.out.println(" Игрок " + team2[numToDoctorTeam2].name +
                                        " погиб и врач ему не нужен!");
                                // а если игрок team2 жив
                            }else {
                                // то доктор лечит  этого игрока
                                team2[i].healing(team2[numToDoctorTeam2]);
                                System.out.println(team2[numToDoctorTeam2].name +
                                        " получил здоровья " + team2[i].addHeal);
                            }
                        }
                        //если игрок team2 - это НЕ доктор, значит он игрок
                    } else {
                        System.out.println("  В игре игрок " + (i+1) + " по имени " + team2[i].name +
                                " из команды team2");
                        //и тогда проверяем: уровень его здоровья < 0 ?
                        if (team2[i].getHealth()<0){
                            //если здоровья < 0, то выводим текст, что он вне игры
                            System.out.println(team2[i].name + " погиб и бить не может!");
                            //если же здоровья у игрока team2 > 0
                        }else {
                            //тогда сначала выбираем игрока team1
                            int numTeam1 = randomHiting.nextInt(3);
                            // а затем проверяем уровень его здоровья
                            if (team1[numTeam1].getHealth()<0){
                                //если здоровья < 0, тогда выводим текст, что он погиб
                                System.out.println(team1[numTeam1].name + " погиб и его нельзя бить!");
                                //если же здоровья > 0, тогда игроки вступают в схватку
                            }else {
                                //а именно, игрок team2 наносит урон игроку team1
                                team2[i].hit(team1[numTeam1]);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("---------------");

        for (Hero t1: team1) {
            t1.info();
        }

        for (Hero t2: team2) {
            t2.info();
        }

    }
    
}