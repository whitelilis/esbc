import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> times=new ArrayList();
        for(int j=0;j<20;j++){
            times.add((int)(Math.random()*86400));
        }
        for(Integer val:times){
            System.out.println((int)val);
        }

         System.out.println("——————————————————————————————");
        Collections.sort(times);
        for(Integer val:times){
            System.out.println(val);
        }
    }
}
