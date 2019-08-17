package TreeSetTest2;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TestClass
{
    public static void main(String[] args)
    {
        HouseComparator h = new HouseComparator();
        Set<House> setHouse = new TreeSet <House>(h);
        House house1 = new House(50,50000);
        House house2 = new House(70,70000);
        House house3 = new House(80,80000);
        House house4 = new House(40,40000);
        House house5 = new House(30,30000);
        setHouse.add(house2);
        setHouse.add(house4);
        setHouse.add(house3);
        setHouse.add(house5);
        setHouse.add(house1);
        for (House house:setHouse)
        {
            System.out.println(house);
        }
    }
}

class House
{ int area;
    int price;


    public House(int area, int price)
    {
        this.area = area;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "House{" +
                "area=" + area +
                ", price=" + price +
                '}';
    }
}

class HouseComparator implements Comparator<House>{
    @Override
    public int compare(House o1, House o2) {
        if (o1.area<o2.area) return -1;
        if (o1.area==o2.area)return 0;
        else return 1;
    }
}