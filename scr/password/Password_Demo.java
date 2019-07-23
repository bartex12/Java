package password;

public class Password_Demo {
    public static void main(String[] args) {

        String[] test = {"123QQwwyy/)", "a3htDFV 1",
                "корова", "корова222",  ")))))))RTNnj","10_НЕГРИТЯТ",
                "4434436556464dddddDDDDD&&&&&&&&"};

       for (String t:test ){
           System.out.println("");
           System.out.println(" **********************");
           System.out.println("Строка: " + t);
           if (checkString(t)){
               System.out.println("Подходит для пароля ");
           }else {
               System.out.println("Не годится для пароля");
           }
       }
    }


    public static boolean checkString(String s){

        boolean digit = false;
        boolean lowCase = false;
        boolean upperCase = false;
        boolean special = false;
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        int size = s.length();
            if (size<8){
                System.out.println("Длина строки = " +size + " \nСлишком короткая строка");
                return false;
            }else if (size>28){
                System.out.println("Длина строки = " +size + " \nСлишком длиная строка");
                return false;
            }

            for (int i = 0; i<size; i++) {
                if (Character.isDigit(s.charAt(i))) {
                    //System.out.println(" Цифра есть -> " + s.charAt(i));
                    digit = true;
                }else if (Character.isLowerCase(s.charAt(i))) {
                    lowCase = true;
                    //System.out.println(" Символ в нижнем регистре есть -> " + s.charAt(i1));
                }else if (Character.isUpperCase(s.charAt(i))) {
                    upperCase = true;
                    //System.out.println(" Символ в верхнем регистре есть -> " + s.charAt(i2));
                }else{
                    Character ss = s.charAt(i);
                    String s1 = ss.toString();
                    if (specialChars.contains(s1)) {
                        special = true;
                        //System.out.println("Специальный символ есть -> " + s1);
                    }
                }
            }

            if (!digit){
                System.out.println("Нет цифр");
                return false;
            }else  if (!lowCase){
            System.out.println("Нет символов в нижнем регистре");
            return false;
        }else if (!upperCase){
            System.out.println("Нет символов в верхнем регистре");
            return false;
        }else if (!special){
            System.out.println("Специальных символов нет");
            return false;
        }else {
            return true;
        }
    }
}
