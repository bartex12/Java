package Lesson_2_Enum;

public class Enum_Demo {
    public static void main(String[] args) {
        
        for (DayOfWeek d: DayOfWeek.values()){
            System.out.println(getWorkingHours(d));
        }
    }

   enum  DayOfWeek{
       MONDAY("понедельник", 40),
       TUESDAY("вторник", 32), WEDNESDAY("среда", 24),
       THURSDAY("четверг", 16), FRIDAY(" пятница", 8),
       SATURDAY("суббота", 0),SANDAY("воскресенье", -1);

       private String rus;
       private int remain;

       DayOfWeek(String rus, int remain) {
           this.rus = rus;
           this.remain = remain;
       }

       public String getRus() {
           return rus;
       }

       public int getRemain() {
           return remain;
       }
   }

   public static String getWorkingHours(Enum en){

       for (DayOfWeek d: DayOfWeek.values()){
           if (d==en){
               switch (d.ordinal()){
                   case 0:
                   case 3:
                   case 4:
                       return ("Сегодня " + d.getRus() +
                               " и до конца недели осталось " + d.getRemain() + " рабочих часов");
                   case 1:
                   case 2:
                       return ("Сегодня " + d.getRus() +
                               " и до конца недели осталось " + d.getRemain() + " рабочих часа");
                   case 5:
                       return ("Сегодня " + d.getRus() +
                               ", но я люблю свою работу, я приду сюда в субботу!");
                   case 6:
                       return ("Сегодня " + d.getRus() + "."+
                               " Ура, выходной!!!");
               }
           }
       }
       return "";
   }

}
