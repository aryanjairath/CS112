package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public TreeSet<String> findPreRq(String course, String course2, HashMap<String, ArrayList<String>> map, TreeSet<String> tree){
        ArrayList<String> list = map.get(course2);
        for(int i = 0; i < list.size(); i++){
            tree.add(list.get(i));
        }
        for(int i = 0 ; i < list.size(); i++)
            findPreRq(course, list.get(i), map, tree);

        return tree;

    }
    
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        int lines = StdIn.readInt();

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for(int i = 0; i < lines; i++){
            map.put(StdIn.readString(), new ArrayList<String>());
        }        
        int b = StdIn.readInt();
        for(int i = 0; i < b; i++){
            map.get(StdIn.readString()).add(StdIn.readString());
        }
        TreeSet<String> tree = new TreeSet<>();
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String course1 = StdIn.readString();    
        String course2 = StdIn.readString();
        ValidPrereq r = new ValidPrereq();
        boolean y = false;
        TreeSet<String> x = r.findPreRq(course1, course2, map, tree);
        for(String val: x){
            if(val.equals(course1))
                y=true;
        }
        if(!y)
            StdOut.print("YES");
        else
            StdOut.print("NO");


    }


}
