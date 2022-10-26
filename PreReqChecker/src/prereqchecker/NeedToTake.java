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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public TreeSet<String> findPreRq(String course, HashMap<String, ArrayList<String>> map, TreeSet<String> tree){
        ArrayList<String> list = map.get(course);
        for(int i = 0; i < list.size(); i++){
            tree.add(list.get(i));
        }
        for(int i = 0 ; i < list.size(); i++)
            findPreRq(list.get(i), map, tree);

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
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String targetcourse = StdIn.readString();
        int nlines = StdIn.readInt();
        NeedToTake take = new NeedToTake();
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> targetcoursepre = take.findPreRq(targetcourse, map, new TreeSet<String>());
        ArrayList<String> taken  = new ArrayList<>();
        
        for(int i = 0; i < nlines; i++){
            String x = StdIn.readString();
            taken.add(x);
            set.add(x);
        }
        for(int x= 0; x < taken.size(); x++){
            TreeSet<String> y = take.findPreRq(taken.get(x), map, new TreeSet<String>());
                for(String val: y){
                    set.add(val);
            }
        
        }
        targetcoursepre.removeAll(set);
        for(String val: targetcoursepre){
            StdOut.println(val);
        }
    }

}
