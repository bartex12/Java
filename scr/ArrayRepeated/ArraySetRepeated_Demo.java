package ArrayRepeated;

import java.util.*;

public class ArraySetRepeated_Demo {


    public static void main(String[] args) {

        ArrayInit arrayInit = new ArrayInit(20, 4);
        arrayInit.info();

        List<String> list = Arrays.asList(arrayInit.getArrayRepeated());
        Iterator<String> iterator = list.iterator();

        Map<String, Integer> map = new HashMap<>();

        while (iterator.hasNext()) {
            String s = iterator.next();
            //если по такому ключу ещё ничего нет
            if (map.get(s)==null) {
                map.put(s, 1);  //кладём единицу
                //а если по такому ключу что-то есть
            }else {
                map.put(s, (map.get(s))+1);  //добавляем 1 к тому, что есть
            }
        }
        System.out.println("Уникальные элементы массива");
        System.out.println(map.keySet());
        System.out.println("Количество повторений уникальных элементов");
        System.out.println(map);
    }


        public static class ArrayInit {
            int size;
            private String[] arrayRepeated;

            public ArrayInit(int size, int unicum) {
                this.size = size;
                Random random = new Random();
                arrayRepeated = new String[size];
                for (int i = 0; i < size; i++) {
                    this.arrayRepeated[i] = "ABC" + (random.nextInt(unicum));
                }
            }

            public String[] getArrayRepeated() {
                return arrayRepeated;
            }

            public void info() {
                System.out.println("Исходный массив строк");
                for (String s : arrayRepeated) {
                    System.out.print(s + " | ");
                }
                System.out.println("");
            }
        }
    }
