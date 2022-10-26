package transit;
import java.util.Queue;
import java.util.LinkedList;
public class MyClass {

    
    public static void main(String args[]) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(6);
        q.add(7);
        q.add(5);
        q.add(3);
        System.out.print(q.poll());
    }
}