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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        int lines = StdIn.readInt();

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for(int i = 0; i < lines; i++){
            map.put(StdIn.readString(), new ArrayList<String>());
        }        
        int b = StdIn.readInt();
        for(int i = 0; i < b; i++){
            map.get(StdIn.readString()).add(StdIn.readString());
        }
        for (Map.Entry<String,ArrayList<String>> e : map.entrySet()){        
            StdOut.print(e.getKey()+" ");
            ArrayList<String> list = map.get(e.getKey());
            for(int i = 0; i < list.size(); i++){
                StdOut.print(list.get(i)+" ");
            }
            StdOut.println();
        }
    }
}
