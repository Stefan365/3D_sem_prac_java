
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SetExample {

    public static void main(String[] args) {

        // Set example with implement TreeSet
        List<String> s = new ArrayList<>();
        List<ObjectO> objekty1 = new ArrayList<>();
        List<ObjectO> objekty2 = new ArrayList<>();

        ObjectO o1 = new ObjectO("a");
        ObjectO o2 = new ObjectO("b");
        ObjectO o3 = new ObjectO("c");
        ObjectO o4 = new ObjectO("d");
        ObjectO o5 = new ObjectO("e");
        ObjectO o6 = new ObjectO("f");
        ObjectO o7 = new ObjectO("g");
        ObjectO o8 = new ObjectO("g");

        objekty1.add(o1);
        objekty1.add(o2);
        objekty1.add(o3);
        objekty1.add(o4);
        objekty1.add(o5);
        objekty1.add(o6);
        objekty1.add(o7);

        objekty2.add(o1);
        objekty2.add(o3);
        objekty2.add(o5);
        objekty2.add(o7);
        
        
        
        Set<String> s1 = new TreeSet<String>();

        s.add("b");
        s.add("a");
        s.add("d");
        s.add("c");

        s1.add("b");
        s1.add("a");
        s1.add("d");
        s1.add("e");

        //s.addAll(s1);
        Iterator it = s.iterator();

        long a = 1234;
        /*
         while(it.hasNext())
         {
         String value=(String)it.next();
          

         System.out.println("Value :"+value);
         }*/
         if (objekty1.contains(o8)) {
             System.out.println("Value :" + o8.getO());
         } else {
             System.out.println("TENTO PRVOK NEOBSAHUJE :" + Objects.equals(1234L, a));
         }
        
        /*
        for (ObjectO obj : objekty2) {
            if (objekty1.contains(obj)) {
                System.out.println("Value :" + obj.getO());
            }
        }/*
        for (String sa : s) {
            System.out.println("Value :" + sa);
        }*/
    }
}
