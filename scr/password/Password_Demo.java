package password;

public class Password_Demo {
    public static void main(String[] args) {

        String[] test = {"123QQwwyy/)", "a3htDFV 1", "qwert", ")))))))RTNnj","10_Негритят"};

       for (String t:test ){
           System.out.println("");
           System.out.println(" **********************");
           System.out.println(" Строка: " + t);
           if (checkString(t)){
               System.out.println(" Подходит для пароля ");
           }else {
               System.out.println(" Не годится для пароля");
           }
       }
    }
    

    public static boolean checkString(String s){

        int size = s.length();

        boolean sizeOf = false;
        if ((size>7)&&(size<28)){
            System.out.println(" Длина строки = " + s.length());
            sizeOf = true;
        }
        if (!sizeOf){
            System.out.println(" Длина строки = " + s.length());
            return false;
        }


        boolean digit = false;
            for (int i = 0; i<size; i++) {
                if (Character.isDigit(s.charAt(i))) {
                    System.out.println(" Цифра есть -> " + s.charAt(i));
                    digit = true;
                    break;
                }
            }
            if (!digit){
                System.out.println(" Нет цифр");
                return false;
            }


            boolean lowCase = false;
                   for (int i1 = 0; i1 < size; i1++) {
                       if (Character.isLowerCase(s.charAt(i1))) {
                           lowCase = true;
                           System.out.println(" Символ в нижнем регистре есть -> " + s.charAt(i1));
                           break;
                       }
                   }
        if (!lowCase){
            System.out.println(" Нет символов в нижнем регистре");
            return false;
        }


            boolean upperCase = false;
                   for (int i2 = 0; i2 < size; i2++) {
                       if (Character.isUpperCase(s.charAt(i2))) {
                           upperCase = true;
                           System.out.println(" Символ в верхнем регистре есть -> " + s.charAt(i2));
                           break;
                       }
                   }
        if (!upperCase){
            System.out.println(" Нет символов в верхнем регистре");
            return false;
        }

               boolean special = false;
            String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
                   for (int i3 = 0; i3<size; i3++){
                      Character ss = s.charAt(i3);
                      String s1 = ss.toString();
                       if (specialChars.contains(s1)) {
                           special = true;
                           System.out.println("Специальный символ есть -> " + s1);
                           return true;
                       }
                   }
        System.out.println("Специальных символов нет");
        return false;
    }
}
